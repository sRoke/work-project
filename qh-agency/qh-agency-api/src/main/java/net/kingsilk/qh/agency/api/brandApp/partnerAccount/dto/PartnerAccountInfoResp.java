package net.kingsilk.qh.agency.api.brandApp.partnerAccount.dto;

/**
 * Created by lit on 17/8/7.
 */
public class PartnerAccountInfoResp {

    /**
     * 渠道商id
     */
    private String partnerId;

    /**
     * 品牌商Id
     */
    private String brandAppId;

    /**
     * 冻结中的余额
     * ps. 采购未结算的钱
     */
    private Integer freezeBalance;

    /**
     * 可提现余额
     * ps. 下级代理商、普通消费者购买的订单已经结算的金额
     */
    private Integer balance;

    /**
     * 不可提现余额
     * ps. 换货时的临时金额。——暗指不能退货
     */
    private Integer noCashBalance;

    /**
     * 已欠货款
     */
    private Integer owedBalance;

    /**
     * 总资产(三个资产总和)
     * @return
     */
    private Integer totalBalance;

    public Integer getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Integer totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public Integer getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(Integer freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getOwedBalance() {
        return owedBalance;
    }

    public void setOwedBalance(Integer owedBalance) {
        this.owedBalance = owedBalance;
    }

    public Integer getNoCashBalance() {
        return noCashBalance;
    }

    public void setNoCashBalance(Integer noCashBalance) {
        this.noCashBalance = noCashBalance;
    }
}
