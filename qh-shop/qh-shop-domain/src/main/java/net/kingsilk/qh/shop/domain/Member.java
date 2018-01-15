package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashSet;

/**
 * 会员
 */
@Document
public class Member extends Base {
    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 是否禁用
     */
    private Boolean enable;

    /**
     * 会员等级Id
     */
    private String levelId;

    /**
     * 备注
     */
    private String memo;

    /**
     * 账户id
     */
    private String accountId;

    /**
     * 手机号
     */
    private String phone;


    /**
     * 会员标签
     */
    private LinkedHashSet<String> tags = new LinkedHashSet<>();

    /**
     * 用于存储名字首字的首字母的ascll,用来排序
     * @return
     */
    private Integer order;

    /**
     * shop会员的联系方式（oauth之外的自定义的）
     */
    private LinkedHashSet<String> contacts = new LinkedHashSet();

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

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public LinkedHashSet<String> getContacts() {
        return contacts;
    }

    public void setContacts(LinkedHashSet<String> contacts) {
        this.contacts = contacts;
    }
}
