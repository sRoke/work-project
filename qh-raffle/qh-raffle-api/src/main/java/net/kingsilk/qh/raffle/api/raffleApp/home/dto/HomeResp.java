package net.kingsilk.qh.raffle.api.raffleApp.home.dto;

import io.swagger.annotations.ApiModel;

import java.util.Set;

@ApiModel
public class HomeResp {


    /***
     * 用户名
     */
    private String userId;


    /***
     * 用户开通的应用id
     */
    private String appId;

    /**
     * 商家id
     */
    private String brandComId;

    /**
     * 主营类目
     */
    private Set<String> categoryId;

    /***
     * 访问路径
     */
    private String appUrl;

    /**
     * 关联id,用于区分相同的应用
     */
    private String raffleAppId;

    /***
     * 店铺名称
     */
    private String shopName;

    /**
     * 店铺类型
     */
    private String shopType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getBrandComId() {
        return brandComId;
    }

    public void setBrandComId(String brandComId) {
        this.brandComId = brandComId;
    }

    public Set<String> getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Set<String> categoryId) {
        this.categoryId = categoryId;
    }

    public String getraffleAppId() {
        return raffleAppId;
    }

    public void setraffleAppId(String raffleAppId) {
        this.raffleAppId = raffleAppId;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }
}
