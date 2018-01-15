package net.kingsilk.qh.agency.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 渠道商账户表
 */
@Deprecated
@Document
public class Account extends Base {

    /**
     * 所属品牌商
     */
    private String brandAppId;

    /**
     * 渠道商id
     */
    @DBRef
    private Partner partner;

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

//    /**
//     * 渠道商银行账号
//     */
//    private String bankCard;
//
//    /**
//     * 渠道商银行账号开户行
//     */
//    private String bank;
//
//    /**
//     * 渠道商银行账号开户名
//     */
//    private String registerName;
//
//    /**
//     * 渠道商微信账号
//     */
//    private String wxOpenId;
//
//    /**
//     * 渠道商支付宝账号
//     */
//    private String aliPayId;

    // --------------------------------------- getter && setter

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
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
}
