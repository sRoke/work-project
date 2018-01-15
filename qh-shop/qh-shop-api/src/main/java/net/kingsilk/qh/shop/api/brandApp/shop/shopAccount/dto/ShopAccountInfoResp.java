package net.kingsilk.qh.shop.api.brandApp.shop.shopAccount.dto;

/**
 *
 */
public class ShopAccountInfoResp {

    private Integer totalBalance;

    private Integer balance;

    private Integer totalWithdraw;

    public Integer getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Integer totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getTotalWithdraw() {
        return totalWithdraw;
    }

    public void setTotalWithdraw(Integer totalWithdraw) {
        this.totalWithdraw = totalWithdraw;
    }
}
