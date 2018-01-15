package net.kingsilk.qh.agency.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 渠道商账户表
 */
@Document
public class AccountBankCard extends Base {

    /**
     * 所属品牌商
     */
    private String brandAppId;

    /**
     * 渠道商id
     */
    @DBRef
    private Account account;

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
}
