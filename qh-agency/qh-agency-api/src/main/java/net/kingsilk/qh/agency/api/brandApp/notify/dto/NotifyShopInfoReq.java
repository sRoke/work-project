package net.kingsilk.qh.agency.api.brandApp.notify.dto;

import javax.ws.rs.QueryParam;
import java.util.Map;

public class NotifyShopInfoReq {


    @QueryParam("skuIds")
    private Map<String, Integer> skuIds;

    @QueryParam("price")
    private Integer price;

    @QueryParam("shopInfo")
    private Map<String, String> shopInfo;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Map<String, Integer> getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(Map<String, Integer> skuIds) {
        this.skuIds = skuIds;
    }

    public Map<String, String> getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(Map<String, String> shopInfo) {
        this.shopInfo = shopInfo;
    }
}
