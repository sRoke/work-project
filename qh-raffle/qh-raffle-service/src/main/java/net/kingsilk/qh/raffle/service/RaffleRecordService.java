package net.kingsilk.qh.raffle.service;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import net.kingsilk.qh.raffle.api.common.ErrStatus;
import net.kingsilk.qh.raffle.domain.QRaffleRecord;
import net.kingsilk.qh.raffle.domain.RaffleAward;
import net.kingsilk.qh.raffle.domain.RaffleRecord;
import net.kingsilk.qh.raffle.repo.RaffleAwardRepo;
import net.kingsilk.qh.raffle.repo.RaffleRecordRepo;
import net.kingsilk.wx4j.broker.api.UniResp;
import net.kingsilk.wx4j.broker.api.wxCom.mp.at.WxComMpAtApi;
import net.kingsilk.wx4j.broker.api.wxCom.mp.scence.ScenceInfoReq;
import net.kingsilk.wx4j.broker.api.wxCom.mp.scence.WxScenceApi;
import net.kingsilk.wx4j.client.mp.api.kfMsg.KfMsgApi;
import net.kingsilk.wx4j.client.mp.api.kfMsg.TextMsgReq;
import net.kingsilk.wx4j.client.mp.api.qrCode.CreateTicketResp;
import net.kingsilk.wx4j.client.mp.api.qrCode.QrCodeApi;
import net.kingsilk.wx4j.client.mp.api.qrCode.TmpReq;
import net.kingsilk.wx4j.client.mp.api.user.InfoResp;
import net.kingsilk.wx4j.client.mp.api.user.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RaffleRecordService {

    @Autowired
    private SecService secService;

    @Autowired
    private BrandAppApi brandAppApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private net.kingsilk.qh.oauth.api.user.UserApi oauthUserApi;

    @Autowired
    private QrCodeApi qrCodeApi;

    @Autowired
    private WxComMpAtApi wxComMpAtApi;

    @Autowired
    private RaffleRecordRepo raffleRecordRepo;

    @Autowired
    private KfMsgApi kfMsgApi;

    @Autowired
    private RaffleAppService raffleAppService;

    @Autowired
    private RaffleAwardRepo raffleAwardRepo;

    @Autowired
    private WxScenceApi wxScenceApi;

    public Map<String, String> forceFollow(String raffleAppId, String raffleId, String recordId, String curUserId, String shareUrl) {

        Map<String, String> map = new LinkedHashMap<>();
        String brandAppId = raffleAppService.getBrandAppId(raffleAppId);
        //获取微信相关id
        net.kingsilk.qh.platform.api.UniResp<BrandAppResp> respUniResp = brandAppApi.info(brandAppId);

        String wxComAppId = respUniResp.getData().getWxComAppId();
        String wxMpAppId = respUniResp.getData().getWxMpId();

        //获取本地accesstoken
        UniResp<net.kingsilk.wx4j.broker.api.wxCom.mp.at.GetResp> uniResp =
                wxComMpAtApi.get(wxComAppId, wxMpAppId);

        //获取微信头像
        net.kingsilk.qh.oauth.api.UniResp<UserGetResp> resp = oauthUserApi.get(curUserId);
        List<UserGetResp.WxUser> openIdList = resp.getData().getWxUsers().stream().filter(wxUser ->
                wxMpAppId.equals(wxUser.getAppId())
        ).collect(Collectors.toList());

        if (openIdList.isEmpty()) {
            map.put("int", String.valueOf(ErrStatus.WXUSER_404));
            return map;
        }
        String openId = openIdList.get(0).getOpenId();

        //查看用户是否关注微信公众号
        InfoResp infoResp = userApi.info(uniResp.getData().getAccessToken(), openIdList.get(0).getOpenId(), "zh_CN");

        map.put("int", String.valueOf(infoResp.getSubscribe()));
        if (infoResp.getSubscribe() == 0) {
            String url = qrCodeUrl(uniResp.getData().getAccessToken(), wxComAppId,
                    wxMpAppId, raffleAppId, raffleId, recordId, curUserId, openId, shareUrl);
            map.put("url", url);
        }
        return map;
    }

    public String qrCodeUrl(
            String accessToken, String wxComAppId, String wxMpAppId, String raffleAppId,
            String raffleId, String recordId, String curUserId, String openId, String shareUrl) {

        TmpReq tmpReq = new TmpReq();
        TmpReq.ActionInfo.Scene scene = new TmpReq.ActionInfo.Scene();

        ScenceInfoReq scenceInfoReq = new ScenceInfoReq();
        scenceInfoReq.setExpiredAt("300");
        Map<String, String> senceData = new LinkedHashMap<>();

        senceData.put("raffleAppId", raffleAppId);
        senceData.put("raffleId", raffleId);
        senceData.put("recordId", recordId);
        senceData.put("curUserId", curUserId);
        senceData.put("shareUrl", shareUrl);
        senceData.put("openId", openId);
        scenceInfoReq.setSenceData(senceData);
        UniResp<Integer> uniResp = wxScenceApi.save(wxComAppId, wxMpAppId, scenceInfoReq);
        scene.setScene_id(uniResp.getData());

        //调取微信的接口，创建一条场景id的记录 data里面放置workid,raffleId
        TmpReq.ActionInfo actionInfo = new TmpReq.ActionInfo();
        actionInfo.setScene(scene);
        tmpReq.setAction_info(actionInfo);
        tmpReq.setExpire_seconds(300);
        CreateTicketResp createTicketResp = qrCodeApi.createTicket(accessToken, tmpReq);
        return createTicketResp.getTicket();
    }

    public void sendMsgUser(String wxComAppId, String wxMpAppId, String raffleAppId, String raffleId, String recordId, String openId, String shareUrl) {

        RaffleRecord record = raffleRecordRepo.findOne(
                Expressions.allOf(
                        QRaffleRecord.raffleRecord.deleted.ne(true),
                        QRaffleRecord.raffleRecord.id.eq(recordId),
                        QRaffleRecord.raffleRecord.raffleAppId.eq(raffleAppId),
                        QRaffleRecord.raffleRecord.raffleId.eq(raffleId)
                )
        );
        RaffleAward award = raffleAwardRepo.findOne(record.getAwardId());
        UniResp<net.kingsilk.wx4j.broker.api.wxCom.mp.at.GetResp> respUniResp =
                wxComMpAtApi.get(wxComAppId, wxMpAppId);
        TextMsgReq req = new TextMsgReq();
        req.setMsgType("text");
        req.setToUser(openId);
        TextMsgReq.Text text = new TextMsgReq.Text();
        text.setContent("恭喜您获得\"" + award.getName() + "\",快去领取吧\n" +
                "<a href='" + "https:" + shareUrl + "'>点击领取</a>");
        req.setText(text);
        kfMsgApi.send(respUniResp.getData().getAccessToken(), req);
    }
}
