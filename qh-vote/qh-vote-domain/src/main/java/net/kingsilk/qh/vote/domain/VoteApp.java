package net.kingsilk.qh.vote.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class VoteApp extends Base {

    private String brandAppId;

    private String shopId;

    private String userId;

    /**
     * 购买记录
     */
    private List<String> payLog = new ArrayList<>();


    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getPayLog() {
        return payLog;
    }

    public void setPayLog(List<String> payLog) {
        this.payLog = payLog;
    }
}
