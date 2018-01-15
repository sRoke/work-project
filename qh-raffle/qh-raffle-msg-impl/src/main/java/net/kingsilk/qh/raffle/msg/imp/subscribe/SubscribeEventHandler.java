package net.kingsilk.qh.raffle.msg.imp.subscribe;

import net.kingsilk.qh.raffle.QhRaffleProperties;
import net.kingsilk.qh.raffle.msg.imp.AbstractJobImpl;
import net.kingsilk.qh.raffle.service.RaffleRecordService;
import net.kingsilk.wx4j.broker.Q;
import net.kingsilk.wx4j.broker.api.UniResp;
import net.kingsilk.wx4j.broker.api.wxCom.mp.at.WxComMpAtApi;
import net.kingsilk.wx4j.broker.api.wxCom.mp.scence.ScenceInfoResp;
import net.kingsilk.wx4j.broker.api.wxCom.mp.scence.WxScenceApi;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.GetResp;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.WxComMpUserApi;
import net.kingsilk.wx4j.broker.msg.api.wx4j.client.com.mp.push.msg.event.subscribe.SubscribeEventEx;
import net.kingsilk.wx4j.broker.msg.api.wx4j.client.com.mp.push.msg.event.subscribe.SubscribeEventExApi;
import net.kingsilk.wx4j.client.mp.api.kfMsg.TextMsgReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component("ActivitySubscribeEventExApi")
public class SubscribeEventHandler extends AbstractJobImpl implements SubscribeEventExApi {

    @Autowired
    private WxComMpAtApi wxComMpAtApi;

    @Autowired
    private RaffleRecordService raffleRecordService;

    @Autowired
    private WxComMpUserApi wxComMpUserApi;

    @Autowired
    private QhRaffleProperties qhRaffleProperties;

    @Autowired
    private WxScenceApi wxScenceApi;

    public String getLockKey(SubscribeEventEx event) {
        StringBuffer buf = new StringBuffer();
        buf
                .append(Q.MQ_EXCHANGE_PREFIX)
                .append("/").append(SubscribeEventEx.class.getName())
                .append("/").append(event.getWxMpAppId()).append(event.getMsg().getEventKey());
        return buf.toString();
    }


    @Override
    public void handle(SubscribeEventEx msgEx) {

        String lockKey = getLockKey(msgEx);
        long waitLockTime = qhRaffleProperties.getMq().getDefaultConf().getLockWaitTime();
        lockAndExec(lockKey, waitLockTime, () -> {

            // TODO 要避免重复发送
            UniResp<net.kingsilk.wx4j.broker.api.wxCom.mp.at.GetResp> respUniResp =
                    wxComMpAtApi.get(msgEx.getWxComAppId(), msgEx.getWxMpAppId());

            TextMsgReq req = new TextMsgReq();
            req.setMsgType("text");
            req.setToUser(msgEx.getMsg().getFromUserName());

            UniResp<GetResp> wxResp
                    = wxComMpUserApi.get(respUniResp.getData().getWxComAppId(), respUniResp.getData().getWxMpAppId(), msgEx.getMsg().getFromUserName());

            //调取微信4j的场景得到值接口，应该需要作品url，直接调用投票接口
            if (!StringUtils.isEmpty(msgEx.getMsg().getEventKey())) {
                String scenceId = msgEx.getMsg().getEventKey().substring(8);
                UniResp<ScenceInfoResp> uniResp =
                        wxScenceApi.get(msgEx.getWxComAppId(), msgEx.getWxMpAppId(), Integer.valueOf(scenceId));

                if (uniResp.getData().getSenceData().get("raffleId") != "" && uniResp.getData().getSenceData().get("raffleId") != null) {
//                    TextMsgReq.Text text = new TextMsgReq.Text();
//                    text.setContent(wxResp.getData().getNickName() + "感谢你宝贵的一票，可以拉上好友一起为我投票，么么哒" + "<p>" +
//                            "<a href='" + shareUrl + "'>分享</a></p>");
//                    req.setText(text);
//                    kfMsgApi.send(respUniResp.getData().getAccessToken(), req);
                    String raffleAppId = uniResp.getData().getSenceData().get("raffleAppId").toString();
                    String raffleId = uniResp.getData().getSenceData().get("raffleId").toString();
                    String curUserId = uniResp.getData().getSenceData().get("curUserId").toString();
                    String shareUrl = uniResp.getData().getSenceData().get("shareUrl").toString();
                    String openId = uniResp.getData().getSenceData().get("openId").toString();
                    String recordId = uniResp.getData().getSenceData().get("recordId").toString();
                    raffleRecordService.sendMsgUser(respUniResp.getData().getWxComAppId(), respUniResp.getData().getWxMpAppId(),raffleAppId,raffleId,recordId, openId, shareUrl);
                }
            }
        });
    }
}
