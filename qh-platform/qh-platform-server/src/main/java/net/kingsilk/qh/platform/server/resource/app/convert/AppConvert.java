package net.kingsilk.qh.platform.server.resource.app.convert;


import net.kingsilk.qh.platform.api.app.dto.AppReq;
import net.kingsilk.qh.platform.api.app.dto.AppResp;
import net.kingsilk.qh.platform.core.SubSysTypeEnum;
import net.kingsilk.qh.platform.domain.App;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class AppConvert {

    public App AppReqConvert(App app, AppReq appReq) {
        if (!StringUtils.isEmpty(appReq.getLogo())) {
            app.setLogo(appReq.getLogo());
        }
        if (!StringUtils.isEmpty(appReq.getAppName())) {
            app.setAppName(appReq.getAppName());
        }
        if (!StringUtils.isEmpty(appReq.getDesp())) {
            app.setDesp(appReq.getDesp());
        }
        if (!StringUtils.isEmpty(appReq.getPrice())) {
            app.setPrice(appReq.getPrice());
        }
        if (!StringUtils.isEmpty(appReq.getTrialDuration())) {
            app.setTrialDuration(appReq.getTrialDuration());
        }
        if (!StringUtils.isEmpty(appReq.getEffectiveCycle())) {
            app.setEffectiveCycle(appReq.getEffectiveCycle());
        }
        if (!StringUtils.isEmpty(appReq.getSystemTypeEnum())) {
            app.setSystemTypeEnum(SubSysTypeEnum.valueOf(appReq.getSystemTypeEnum()));
        }
        if (!StringUtils.isEmpty(appReq.getAppUrl())) {
            app.setAppUrl(appReq.getAppUrl());
        }
        return app;
    }

    public AppResp AppConvert(App app) {
        AppResp appResp = new AppResp();
        appResp.setPrice(app.getPrice());
        appResp.setAppName(app.getAppName());
        appResp.setDesp(app.getDesp());
        appResp.setAppUrl(app.getAppUrl());
        appResp.setLogo(app.getLogo());
        appResp.setTrialDuration(app.getTrialDuration());
        appResp.setEffectiveCycle(app.getEffectiveCycle());
        appResp.setId(app.getId());
        appResp.setSystemTypeEnum(app.getSystemTypeEnum());
        return appResp;
    }
}
