package net.kingsilk.qh.platform.api.app.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "编辑应用的修改信息")
public class AppReq {

    /**
     * 子系统类型
     */
    private String systemTypeEnum;

    /**
     * 子系统logo
     */
    private String logo;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用介绍
     */
    private String desp;

    /**
     * 试用周期
     */
    private String trialDuration;

    /**
     * 服务价格（分）
     */
    private String price;

    /**
     * 访问地址
     */
    private String appUrl;

    /***
     * 生效周期
     */
    private String effectiveCycle;


    public String getSystemTypeEnum() {
        return systemTypeEnum;
    }

    public void setSystemTypeEnum(String systemTypeEnum) {
        this.systemTypeEnum = systemTypeEnum;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getTrialDuration() {
        return trialDuration;
    }

    public void setTrialDuration(String trialDuration) {
        this.trialDuration = trialDuration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEffectiveCycle() {
        return effectiveCycle;
    }

    public void setEffectiveCycle(String effectiveCycle) {
        this.effectiveCycle = effectiveCycle;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
