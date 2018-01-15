package net.kingsilk.qh.agency.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 渠道商资金账户表
 */
@Document
public class PartnerAccount extends Base {

    /**
     * 渠道商id
     */
    @Indexed
    @DBRef
    private Partner partner;

    /**
     * 所属品牌
     */
    private String brandAppId;

    /**
     * 冻结中的余额
     * ps. 冻结中：提现中
     */
    private Integer freezeBalance;

    /**
     * 可提现余额
     * ps. 下级代理商、普通消费者购买的订单已经结算的金额
     */
    private Integer balance;

    /**
     * 可用货款
     * ps. 换货时的临时金额。——暗指不能退货
     * 当前渠道商采购退货后的累计可用退货款
     */
    private Integer noCashBalance;

    /**
     * 已欠货款
     */
    private Integer owedBalance;

    /**
     * 渠道商银行账号
     */
    private String bankCard;

    /**
     * 渠道商银行账号开户行
     */
    private String bank;

    /**
     * 渠道商银行账号开户名
     */
    private String registerName;

    /**
     * 渠道商微信账号
     */
    private String wxUnionId;
    /**
     * 渠道商支付宝账号
     */
    private String aliPayId;

    /** 支付密码，仅当触发余额支付或者充值的时候使用 */
    private String payPassword;

    /**
     * 支付密码最后更新时间
     */
    private Date payPwdLastUpdateTime;

    /** 密码是否过期 */
    private Boolean passwordExpired = false;

    // --------------------------------------- getter && setter


    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
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

    public Integer getNoCashBalance() {
        return noCashBalance;
    }

    public void setNoCashBalance(Integer noCashBalance) {
        this.noCashBalance = noCashBalance;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getWxUnionId() {
        return wxUnionId;
    }

    public void setWxUnionId(String wxUnionId) {
        this.wxUnionId = wxUnionId;
    }

    public String getAliPayId() {
        return aliPayId;
    }

    public void setAliPayId(String aliPayId) {
        this.aliPayId = aliPayId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public Boolean getPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(Boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public Date getPayPwdLastUpdateTime() {
        return payPwdLastUpdateTime;
    }

    public void setPayPwdLastUpdateTime(Date payPwdLastUpdateTime) {
        this.payPwdLastUpdateTime = payPwdLastUpdateTime;
    }

    public Integer getOwedBalance() {
        return owedBalance;
    }

    public void setOwedBalance(Integer owedBalance) {
        this.owedBalance = owedBalance;
    }
}
