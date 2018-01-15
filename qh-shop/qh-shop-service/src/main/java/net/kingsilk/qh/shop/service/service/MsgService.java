package net.kingsilk.qh.shop.service.service;


import net.kingsilk.wx4j.broker.api.wxCom.mp.at.WxComMpAtApi;
import net.kingsilk.wx4j.client.mp.api.kfMsg.KfMsgApi;
import net.kingsilk.wx4j.client.mp.api.kfMsg.TextMsgReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MsgService {

    @Autowired
    private WxComMpAtApi wxComMpAtApi;

    @Autowired
    private KfMsgApi kfMsgApi;

    public void sendMsg(String wxComAppId, String wxMpAppId, String openId, String content) {
        net.kingsilk.wx4j.broker.api.UniResp<net.kingsilk.wx4j.broker.api.wxCom.mp.at.GetResp> respUniResp =
                wxComMpAtApi.get(wxComAppId, wxMpAppId);
        TextMsgReq req = new TextMsgReq();
        req.setMsgType("text");
        req.setToUser(openId);
        TextMsgReq.Text text = new TextMsgReq.Text();
        text.setContent(content);
        req.setText(text);
        kfMsgApi.send(respUniResp.getData().getAccessToken(), req);
    }

    public void sendMsgUser(String wxComAppId, String wxMpAppId, String openId, String shareUrl, String money) {

        net.kingsilk.wx4j.broker.api.UniResp<net.kingsilk.wx4j.broker.api.wxCom.mp.at.GetResp> respUniResp =
                wxComMpAtApi.get(wxComAppId, wxMpAppId);
        TextMsgReq req = new TextMsgReq();
        req.setMsgType("text");
        req.setToUser(openId);
        TextMsgReq.Text text = new TextMsgReq.Text();
        String price = BigDecimal.valueOf(Integer.valueOf(money)).divide(new BigDecimal(100)).toString();
        text.setContent("恭喜您成功砍掉" + price + "元\n" +
                "<a href='" + shareUrl + "'>找人砍价</a>");
        req.setText(text);
        kfMsgApi.send(respUniResp.getData().getAccessToken(), req);

    }

}
