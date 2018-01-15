package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * shop staff
 */
@Document
public class Staff extends Base {

    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 是否禁用
     */
    private Boolean enable;

    /**
     * 备注
     */
    private String memo;


    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
