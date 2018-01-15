package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 会员账户表
 */
@Document
public class MemberAccount extends Base {

    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;

    /**
     * 账户余额
     */
    private Integer balance;

    /**
     * 会员id
     */
    private String memberId;

    /**
     * 账户冻结余额
     */
    private Integer freezeBalance;

    /**
     * 账户不可提现余额
     */
    private Integer noCashBalance;

    /**
     * 账户积分
     */
    private Integer integral;

    /**
     * 账户当年累计积分
     */
    private Integer yearIntegral;

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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(Integer freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public Integer getNoCashBalance() {
        return noCashBalance;
    }

    public void setNoCashBalance(Integer noCashBalance) {
        this.noCashBalance = noCashBalance;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getYearIntegral() {
        return yearIntegral;
    }

    public void setYearIntegral(Integer yearIntegral) {
        this.yearIntegral = yearIntegral;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
