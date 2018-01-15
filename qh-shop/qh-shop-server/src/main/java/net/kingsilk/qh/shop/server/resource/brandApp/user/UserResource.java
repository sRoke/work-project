//package net.kingsilk.qh.shop.server.resource.brandApp.user;
//
//import net.kingsilk.qh.shop.api.UniResp;
//import net.kingsilk.qh.shop.service.service.SecService;
//import net.kingsilk.qh.shop.service.service.UserService;
//import net.kingsilk.wx4j.broker.api.wxCom.mp.scence.ScenceInfoReq;
//import net.kingsilk.wx4j.broker.api.wxCom.mp.scence.WxScenceApi;
//import net.kingsilk.wx4j.client.mp.api.qrCode.CreateTicketResp;
//import net.kingsilk.wx4j.client.mp.api.qrCode.QrCodeApi;
//import net.kingsilk.wx4j.client.mp.api.qrCode.TmpReq;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * 用户级别的公共API 不区分是门店员工或是商城客户
// */
//public class UserResource {

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private SecService secService;
//
//    @Autowired
//    private QrCodeApi qrCodeApi;
//
//    @Autowired
//    private WxScenceApi wxScenceApi;
//
//    public void getUserQrCode(){
////        String userId=secService.curUserId();
////
////        TmpReq tmpReq = new TmpReq();
////        TmpReq.ActionInfo.Scene scene = new TmpReq.ActionInfo.Scene();
////
////
////        ScenceInfoReq scenceInfoReq = new ScenceInfoReq();
////        scenceInfoReq.setExpiredAt("300");
////        Map<String, String> senceData = new LinkedHashMap<>();
////
////        senceData.put("brandAppId", brandAppId);
////        senceData.put("workId", workId);
////        senceData.put("activityId", activityId);
////        senceData.put("shareUrl", shareUrl);
////        senceData.put("curUserId", curUserId);
////        scenceInfoReq.setSenceData(senceData);
////        UniResp<Integer> uniResp = wxScenceApi.save(wxComAppId, wxMpAppId, scenceInfoReq);
////        scene.setScene_id(uniResp.getData());
////
////        //调取微信的接口，创建一条场景id的记录 data里面放置workid,activityId
////        TmpReq.ActionInfo actionInfo = new TmpReq.ActionInfo();
////        actionInfo.setScene(scene);
////        tmpReq.setAction_info(actionInfo);
////        tmpReq.setExpire_seconds(300);
////        CreateTicketResp createTicketResp = qrCodeApi.createTicket(accessToken, tmpReq);
////        return createTicketResp.getTicket();
////
//
////        userService.getOauthUserInfo();
//
//    }
//}
