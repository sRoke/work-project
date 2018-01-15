package net.kingsilk.qh.platform.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document
//@CompoundIndexes({
//        @CompoundIndex(def = "brandComId:1,appId:1,id:1}")
//})
public class BrandApp extends Base {

    /**
     * 拥有者（特指一个组织、公司）。
     * <p>
     *  组织Id
     */
    private String ownerOrgId;

    /**
     * 拥有该应用的品牌商。
     */
    private String brandComId;

    /**
     * 开通的应用
     */
    private String appId;

    /***
     *开通应用的使用期限
     */
    private String expireDate;

    /**
     * 店铺名称
     */
    private String shopName;

    //付费记录，记录在付费记录表中，多对一
//    /**
//     * 付费记录
//     */
//    private Set<String> payLogs;


    /**
     * 第三方平台appId
     */
    private String wxComAppId;

    /**
     * 关联的微信公众号
     */
    private String wxMpId;


    public String getOwnerOrgId() {
        return ownerOrgId;
    }

    public void setOwnerOrgId(String ownerOrgId) {
        this.ownerOrgId = ownerOrgId;
    }

    public String getBrandComId() {
        return brandComId;
    }

    public void setBrandComId(String brandComId) {
        this.brandComId = brandComId;
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

//    public Set<String> getPayLogs() {
//        return payLogs;
//    }
//
//    public void setPayLogs(Set<String> payLogs) {
//        this.payLogs = payLogs;
//    }

    public String getWxComAppId() {
        return wxComAppId;
    }

    public void setWxComAppId(String wxComAppId) {
        this.wxComAppId = wxComAppId;
    }

    public String getWxMpId() {
        return wxMpId;
    }

    public void setWxMpId(String wxMpId) {
        this.wxMpId = wxMpId;
    }
}
