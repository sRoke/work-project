package net.kingsilk.qh.activity.service.service;


import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.activity.api.ErrStatus;
import net.kingsilk.qh.activity.api.ErrStatusException;
import net.kingsilk.qh.activity.core.vote.VoteStatusEnum;
import net.kingsilk.qh.activity.core.vote.VoteWorksStatusEnum;
import net.kingsilk.qh.activity.domain.*;
import net.kingsilk.qh.activity.repo.VoteActivityRepo;
import net.kingsilk.qh.activity.repo.VoteRecordRepo;
import net.kingsilk.qh.activity.repo.VoteWorksRepo;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import net.kingsilk.wx4j.broker.api.UniResp;
import net.kingsilk.wx4j.broker.api.wxCom.mp.at.WxComMpAtApi;
import net.kingsilk.wx4j.broker.api.wxCom.mp.scence.ScenceInfoReq;
import net.kingsilk.wx4j.broker.api.wxCom.mp.scence.WxScenceApi;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.GetResp;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.WxComMpUserApi;
import net.kingsilk.wx4j.client.mp.api.qrCode.CreateTicketResp;
import net.kingsilk.wx4j.client.mp.api.qrCode.QrCodeApi;
import net.kingsilk.wx4j.client.mp.api.qrCode.TmpReq;
import net.kingsilk.wx4j.client.mp.api.user.InfoResp;
import net.kingsilk.wx4j.client.mp.api.user.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoteRecordService {

    @Autowired
    private SecService secService;

    @Autowired
    private BrandAppApi brandAppApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private net.kingsilk.qh.oauth.api.user.UserApi oauthUserApi;

    @Autowired
    private WxComMpAtApi wxComMpAtApi;

    @Autowired
    private WxScenceApi wxScenceApi;

    @Autowired
    private QrCodeApi qrCodeApi;

    @Autowired
    private VoteWorksRepo voteWorksRepo;

    @Autowired
    private VoteActivityRepo voteActivityRepo;

    @Autowired
    private VoteRecordRepo voteRecordRepo;


    @Autowired
    private WxComMpUserApi wxComMpUserApi;

    public Map<String, String> forceFollow(String brandAppId, String activityId, String workId, String shareUrl) {

        String userId = secService.curUserId();

        Map<String, String> map = new LinkedHashMap<>();

        //获取微信相关id
        net.kingsilk.qh.platform.api.UniResp<BrandAppResp> respUniResp = brandAppApi.info(brandAppId);

        String wxComAppId = respUniResp.getData().getWxComAppId();
        String wxMpAppId = respUniResp.getData().getWxMpId();

        //获取本地accesstoken
        net.kingsilk.wx4j.broker.api.UniResp<net.kingsilk.wx4j.broker.api.wxCom.mp.at.GetResp> uniResp =
                wxComMpAtApi.get(wxComAppId, wxMpAppId);

        //获取微信头像
        net.kingsilk.qh.oauth.api.UniResp<UserGetResp> resp = oauthUserApi.get(userId);
        List<UserGetResp.WxUser> openIdList = resp.getData().getWxUsers().stream().filter(wxUser ->
                wxMpAppId.equals(wxUser.getAppId())
        ).collect(Collectors.toList());

        if (openIdList.isEmpty()) {
            map.put("int", String.valueOf(ErrStatus.WXUSER_404));
            return map;
        }

        //查看用户是否关注微信公众号
        InfoResp infoResp = userApi.info(uniResp.getData().getAccessToken(), openIdList.get(0).getOpenId(), "zh_CN");

        map.put("int", String.valueOf(infoResp.getSubscribe()));
        if (infoResp.getSubscribe() == 0) {
            String url = qrCodeUrl(uniResp.getData().getAccessToken(), wxComAppId,
                    wxMpAppId, brandAppId, activityId, workId, shareUrl, userId);
            map.put("url", url);
        }
        return map;
    }

    public String qrCodeUrl(
            String accessToken, String wxComAppId, String wxMpAppId, String brandAppId,
            String activityId, String workId, String shareUrl, String curUserId) {

        TmpReq tmpReq = new TmpReq();
        TmpReq.ActionInfo.Scene scene = new TmpReq.ActionInfo.Scene();


        ScenceInfoReq scenceInfoReq = new ScenceInfoReq();
        scenceInfoReq.setExpiredAt("300");
        Map<String, String> senceData = new LinkedHashMap<>();

        senceData.put("brandAppId", brandAppId);
        senceData.put("workId", workId);
        senceData.put("activityId", activityId);
        senceData.put("shareUrl", shareUrl);
        senceData.put("curUserId", curUserId);
        scenceInfoReq.setSenceData(senceData);
        UniResp<Integer> uniResp = wxScenceApi.save(wxComAppId, wxMpAppId, scenceInfoReq);
        scene.setScene_id(uniResp.getData());

        //调取微信的接口，创建一条场景id的记录 data里面放置workid,activityId
        TmpReq.ActionInfo actionInfo = new TmpReq.ActionInfo();
        actionInfo.setScene(scene);
        tmpReq.setAction_info(actionInfo);
        tmpReq.setExpire_seconds(300);
        CreateTicketResp createTicketResp = qrCodeApi.createTicket(accessToken, tmpReq);
        return createTicketResp.getTicket();
    }


    /**
     * 投票相关转到这里了
     *
     * @return
     */
    public String voteService(
            String brandAppId,
            String activityId,
            String id,
            String wxComAppId,
            String wxMpAppId,
            String curUserId
    ) {
        VoteActivity voteActivity = voteActivityRepo.findOne(
                Expressions.allOf(
                        QVoteActivity.voteActivity.brandAppId.eq(brandAppId),
                        QVoteActivity.voteActivity.id.eq(activityId),
                        QVoteActivity.voteActivity.voteStatusEnum.eq(VoteStatusEnum.NORMAL)
                ));

        if (voteActivity == null) {
            throw new ErrStatusException(ErrStatus.ACTIVITYERROR, "活动还未开始");
        }

        if (voteActivity.getVoteStartTime().after(new Date())) {
            throw new ErrStatusException(ErrStatus.ACTIVITYERROR, "投票还未开始");
        }
        if (voteActivity.getVoteEndTime().before(new Date())) {
            throw new ErrStatusException(ErrStatus.ACTIVITYERROR, "投票已经结束");
        }
        if (StringUtils.isEmpty(curUserId)) {
            curUserId = secService.curUserId();
        }
        Assert.notNull(curUserId, "请先注册");

        Pageable pageable = new PageRequest(0, 999, null);


        //todo 先查看今天当前人投了几次票
        Calendar todayStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        todayStartCalendar.set(todayStartCalendar.get(Calendar.YEAR), todayStartCalendar.get(Calendar.MONTH), todayStartCalendar.get(Calendar.DATE) - 1, 24, 0, 0);
        Date todayStartTime = todayStartCalendar.getTime();
        Calendar todayEndCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        todayStartCalendar.set(todayStartCalendar.get(Calendar.YEAR), todayEndCalendar.get(Calendar.MONTH), todayStartCalendar.get(Calendar.DATE), 23, 59, 59);
        Date todayEndTime = todayEndCalendar.getTime();
        Page<VoteRecord> allNum = voteRecordRepo.findAll(
                Expressions.allOf(
                        QVoteRecord.voteRecord.voterUserId.eq(curUserId),
                        QVoteRecord.voteRecord.voteActivityId.eq(activityId)
                ), pageable);
        Page<VoteRecord> list = voteRecordRepo.findAll(
                Expressions.allOf(
                        QVoteRecord.voteRecord.voterUserId.eq(curUserId),
                        QVoteRecord.voteRecord.voteActivityId.eq(activityId),
                        QVoteRecord.voteRecord.voteWorksId.eq(id),
                        QVoteRecord.voteRecord.dateCreated.lt(todayEndTime),
                        QVoteRecord.voteRecord.dateCreated.gt(todayStartTime)
                ), pageable);
        Page<VoteRecord> todayVoteNum = voteRecordRepo.findAll(
                Expressions.allOf(
                        QVoteRecord.voteRecord.voterUserId.eq(curUserId),
                        QVoteRecord.voteRecord.voteActivityId.eq(activityId),
                        QVoteRecord.voteRecord.dateCreated.lt(todayEndTime),
                        QVoteRecord.voteRecord.dateCreated.gt(todayStartTime)
                ), pageable);
        if (voteActivity.getTotalVoteCount() <= allNum.getTotalElements()) {
            throw new ErrStatusException(ErrStatus.ACTIVITYERROR, "亲，你的票数已用完");
        }
        if (voteActivity.getMaxVotePerDay() <= todayVoteNum.getTotalElements()) {
            throw new ErrStatusException(ErrStatus.ACTIVITYERROR, "亲，你今天投TA的票数已达上限");
        }
        if (voteActivity.getMaxTicketPerDay() <= list.getTotalElements()) {
            throw new ErrStatusException(ErrStatus.ACTIVITYERROR, "你今天的票数已用完");
        }
        Map<String, List<VoteRecord>> map = todayVoteNum.getContent().stream().collect(
                Collectors.groupingBy(VoteRecord::getVoteWorksId, Collectors.toList())
        );
        if (!map.containsKey(id)) {
            if (voteActivity.getVotePeoplePerDay() != -1 && voteActivity.getVotePeoplePerDay() <= map.size()) {
                throw new ErrStatusException(ErrStatus.ACTIVITYERROR, "亲，不能再给其他人投票了");
            }
        }

        net.kingsilk.qh.oauth.api.UniResp<UserGetResp> userInfo = oauthUserApi.get(curUserId);
        List<UserGetResp.WxUser> openIdList = userInfo.getData().getWxUsers().stream().filter(wxUser ->
                wxMpAppId.equals(wxUser.getAppId())
        ).collect(Collectors.toList());
        if (openIdList.isEmpty()) {
            throw new ErrStatusException(ErrStatus.WXUSER_404, "请重新登录");
        }
        String openId = openIdList.get(0).getOpenId();
        net.kingsilk.wx4j.broker.api.UniResp<GetResp> wxResp
                = wxComMpUserApi.get(wxComAppId, wxMpAppId, openId);
        if (wxResp.getStatus() == 10001) {
            throw new ErrStatusException(ErrStatus.WXUSER_404, "请重新登录");
        }

        //todo 投票生成投票记录
        VoteRecord voteRecord = new VoteRecord();
        voteRecord.setVoterUserId(curUserId);
        voteRecord.setVoterPhone(userInfo.getData().getPhone());
        voteRecord.setVoteWorksId(id);
        voteRecord.setVoteActivityId(activityId);
        voteRecord.setVoterNickName(wxResp.getData().getNickName());
        voteRecord.setVoterHeaderImgUrl(wxResp.getData().getHeadImgUrl());
        voteRecord = voteRecordRepo.save(voteRecord);
        //todo 给相应的作品加上1票
        VoteWorks voteWorks = voteWorksRepo.findOne(
                Expressions.allOf(
                        QVoteWorks.voteWorks.id.eq(id),
                        QVoteWorks.voteWorks.status.eq(VoteWorksStatusEnum.NORMAL)
                ));
        //todo 加上 排名   对重票的情况 再加一票的时候进行 重新排名，其他情况排名不变。
        Iterable<VoteWorks> voteWorksAll = voteWorksRepo.findAll(
                Expressions.allOf(
                        QVoteWorks.voteWorks.voteActivityId.eq(activityId),
                        QVoteWorks.voteWorks.status.eq(VoteWorksStatusEnum.NORMAL),
                        QVoteWorks.voteWorks.totalVotes.eq(voteWorks.getTotalVotes()),
                        QVoteWorks.voteWorks.deleted.ne(true)
                )
        );
        int lenth = 0;   // 记录是否重票
        int min = Integer.MAX_VALUE;    //记录最小排名数
        int curRank = 0; //当前未加票前 的排名
        curRank = voteWorks.getRank();
        for (VoteWorks voteWorks1 : voteWorksAll) {
            lenth++;
        }
        if (lenth > 1) {
            for (VoteWorks voteWorks2 : voteWorksAll) {
                int rank = voteWorks2.getRank();
                min = rank < min ? rank : min;
            }

            for (VoteWorks curVoteWorks : voteWorksAll) {
                if (curVoteWorks.getRank() < curRank) {
                    curVoteWorks.setRank(curVoteWorks.getRank() + 1);
                }
                voteWorksRepo.save(curVoteWorks);
            }
            //对当前加票的作品 重新设置排名
            voteWorks.setRank(min);
            voteWorksRepo.save(voteWorks);
        }

        voteWorks.setTotalVotes(voteWorks.getTotalVotes() + 1);
        voteWorks.setLastVoteTime(new Date());
        voteWorksRepo.save(voteWorks);

        //活动上的统计
        VoteActivity voteAfterTotal = voteActivityRepo.findOne(activityId);
        voteAfterTotal.setTotalVisit(voteAfterTotal.getTotalVisit() + 1);
        voteAfterTotal.setTotalVote(voteAfterTotal.getTotalVote() + 1);
        voteActivityRepo.save(voteAfterTotal);
        return voteRecord.getId();
    }


}
