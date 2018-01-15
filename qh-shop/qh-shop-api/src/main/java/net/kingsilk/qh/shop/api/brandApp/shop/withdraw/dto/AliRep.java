package net.kingsilk.qh.shop.api.brandApp.shop.withdraw.dto;

public class AliRep {

    /**
     * 账户真实姓名
     */
    private String realName;

    /**
     * 账户账号
     */
    private String account;

    /**
     * 是否设为默认
     */
    private Boolean isDefault;

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
