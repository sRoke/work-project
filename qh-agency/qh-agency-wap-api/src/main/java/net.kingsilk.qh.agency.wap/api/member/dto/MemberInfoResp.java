package net.kingsilk.qh.agency.wap.api.member.dto;


/**
 * Created by lit on 17/7/24.
 */
public class MemberInfoResp {
    /**
     * 所属品牌
     */
    private String brandId;

    /**
     * 账户ID
     */
    private String userId;

    /**
     * 真实姓名。 第一次创建时从 UserDetails 中复制得来
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 花名
     */
    private String nickName;


    /**
     * 头像URL。
     */
    private String avatar;

    /**
     * 是否已经禁用。
     * <p>
     * true - 已经禁用。
     */
    private boolean disabled;

    /**
     * 收货地址
     */
    private AdcModel addr;

    /**
     * 店铺名称
     */
    private String shopName;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public AdcModel getAddr() {
        return addr;
    }

    public void setAddr(AdcModel addr) {
        this.addr = addr;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
