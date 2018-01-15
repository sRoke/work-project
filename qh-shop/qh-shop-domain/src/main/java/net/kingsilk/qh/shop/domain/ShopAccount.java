package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 会员账户表
 */
@Document
public class ShopAccount extends Base {

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
     * 账户冻结余额
     */
    private Integer freezeBalance;

    /**
     * 账户不可提现余额
     */
    private Integer noCashBalance;

    /**
     * 支付宝账户
     */
    private AliAccount aliAccount;

    /**
     * 统计字段，累计收入
     */
    private Integer totalBalance;

    /**
     * 统计字段，累计提现
     */
    private Integer totalWithdraw;

    public static class AliAccount {

        /**
         * 账户真实姓名
         */
        private String realName;

        /**
         * 账户账号
         */
        private String account;

        /**
         * 是否默认(绑定支付宝就设置为默认)
         */
        private Boolean isDefault = false;

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public Boolean getDefault() {
            return isDefault;
        }

        public void setDefault(Boolean aDefault) {
            isDefault = aDefault;
        }
    }

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

    public AliAccount getAliAccount() {
        return aliAccount;
    }

    public void setAliAccount(AliAccount aliAccount) {
        this.aliAccount = aliAccount;
    }

    public Integer getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Integer totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Integer getTotalWithdraw() {
        return totalWithdraw;
    }

    public void setTotalWithdraw(Integer totalWithdraw) {
        this.totalWithdraw = totalWithdraw;
    }
}
