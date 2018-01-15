package net.kingsilk.qh.shop.api.brandApp.shop.shopAccount.dto;

import net.kingsilk.qh.shop.core.AccountChangeTypeEnum;

import java.util.Date;

/**
 *
 */
public class ShopAccountPageResp {

    private Date createDate;

    private Integer amount;

    private AccountChangeTypeEnum type;

    private String payType;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public AccountChangeTypeEnum getType() {
        return type;
    }

    public void setType(AccountChangeTypeEnum type) {
        this.type = type;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
