package net.kingsilk.qh.platform.api.brandApp.dto;

import javax.ws.rs.QueryParam;

public class BrandAppReq {

    @QueryParam(value = "appId")
    private String appId;

    @QueryParam(value = "date")
    private String date;

    @QueryParam(value = "shopName")
    private String shopName;

    @QueryParam(value = "商家id")
    private String brandComId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getBrandComId() {
        return brandComId;
    }

    public void setBrandComId(String brandComId) {
        this.brandComId = brandComId;
    }
}
