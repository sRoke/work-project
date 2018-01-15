package net.kingsilk.qh.agency.server.resource.brandApp.wx;

import net.kingsilk.qh.agency.QhAgencyProperties;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.wx.WeiXinApi;
import net.kingsilk.qh.agency.service.WeiXinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Map;

/**
 *
 */
public class WeiXinResource implements WeiXinApi {
    @Autowired
    QhAgencyProperties properties;
    @Autowired
    WeiXinService weiXinService;

    @Override
    public UniResp<Map> jsSdkConf(String url) {
        Assert.notNull(url, "请传递要调用微信 JS SDK 的网页的URL");
//        String appId = properties.wxPub.appId;
//        String appSecret = properties.wxPub.appSecret;

        String req = "https://kingsilk.net/qh/mall/local/11300/api/weiXin/jsSdkConf?url=${url}";
        //Map cfg = restTemplate.getForObject(req, Map).jsApiConf
        Map cfg = weiXinService.genJsApiConfig(url);
        System.out.println("-----------------cfg " + cfg);
        UniResp resp = new UniResp();
        resp.setStatus(200);
        resp.setData(cfg);
        return resp;
    }
}
