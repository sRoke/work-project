package net.kingsilk.qh.agency.wap.api.common.dto;


import java.util.HashSet;
import java.util.Set;

/**
 * 品牌商员工
 */
public class Staff extends Base {

    /**
     * 所属品牌
     */
    private String  brandId;

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
     * 身份证号码
     */
    private String idNumber;

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
     * 权限列表。
     * 暂时不用,使用用户组的权限
     */
    private Set<String> authorities=new HashSet<>();

    /**
     * 备注
     */
    private String memo;

    // --------------------------------------- getter && setter

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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
