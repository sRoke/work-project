package net.kingsilk.qh.platform.api.app.dto;

import net.kingsilk.qh.platform.core.SubSysTypeEnum;

public class AppResp {

    /***
     * appId
     */
    private String id;

    /**
     * 子系统类型
     */
    private SubSysTypeEnum systemTypeEnum;

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


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public SubSysTypeEnum getSystemTypeEnum() {
        return systemTypeEnum;
    }

    public void setSystemTypeEnum(SubSysTypeEnum systemTypeEnum) {
        this.systemTypeEnum = systemTypeEnum;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
}
