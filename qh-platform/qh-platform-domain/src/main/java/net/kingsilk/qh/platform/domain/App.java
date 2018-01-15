package net.kingsilk.qh.platform.domain;

import net.kingsilk.qh.platform.core.SubSysTypeEnum;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 子系统权限表
 */
@Document
public class App extends Base {

//    /**
//     * 品牌
//     */
//    private BrandCom brand;

    /**
     * 子系统类型
     */
    private SubSysTypeEnum systemTypeEnum;

    /**
     * 子系统logo
     */
    private String logo;

    /**
     * 访问地址
     */
    private String appUrl;

    /**
     * 应用名称
     */
    @Indexed(unique = true)
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

    /***
     * 生效周期
     */
    private String effectiveCycle;

//    /**
//     * 是否有该系统权限
//     */
//    private Boolean hasAuth = false;
//
//    /**
//     *过期时间
//     */
//    private Date expireDate;
//
//    /**
//     * 付费记录
//     */
//    private String payRecordId;
//
//    /**
//     * 创建人
//     */
//    private String userId;


    // --------------------------------------- getter && setter


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
