package net.kingsilk.qh.oauth.api.s.user.dto;

import io.swagger.annotations.*;

import java.util.*;

/**
 *
 */
@ApiModel
public class InfoResp {


    // --------------------------------------- common fields

    @ApiModelProperty("User ID")
    private String id;

    @ApiModelProperty("创建时间")
    private Date dateCreated;

    @ApiModelProperty("创建者的ID")
    private String createdBy;

    @ApiModelProperty("最后修改日期")
    private Date lastModifiedDate;

    @ApiModelProperty("最后更新者的ID")
    private String lastModifiedBy;

    // --------------------------------------- biz fields


    @ApiModelProperty("是否有效")
    private boolean enabled = true;

    @ApiModelProperty("帐号是否锁定")
    private boolean accountLocked = false;

    @ApiModelProperty("帐号是否过期")
    private boolean accountExpired = false;

    /**
     * 邀请人
     */
    @ApiModelProperty("邀请人的 User Id")
    private String inviterUserId;

    // ---------------------------------- 绑定用户信息:  用户名、密码、支付密码

    @ApiModelProperty("登录用的用户名")
    private String username;


    // ---------------------------------- 绑定用户信息:  手机号
    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("手机号验证的时间。是有当该字段非空时，才表示手机号完成绑定")
    private Date phoneVerifiedAt;

    // ---------------------------------- 绑定用户信息:  电子邮箱

    @ApiModelProperty("电子邮箱")
    private String email;

    @ApiModelProperty("邮箱验证的时间。是有当该字段非空时，才表示邮箱完成绑定。")
    private Date emailVerifiedAt;

    // ---------------------------------- 绑定用户信息:  微信用户

    @ApiModelProperty("绑定的微信账号信息")
    private List<WxUser> wxUsers;


    @ApiModel
    public static class WxUser {

        @ApiModelProperty("微信公众号的 APP ID")
        private String appId;

        @ApiModelProperty("微信用户的 Open ID")
        private String openId;

        @ApiModelProperty("微信用户的 union ID")
        private String unionId;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getUnionId() {
            return unionId;
        }

        public void setUnionId(String unionId) {
            this.unionId = unionId;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public String getInviterUserId() {
        return inviterUserId;
    }

    public void setInviterUserId(String inviterUserId) {
        this.inviterUserId = inviterUserId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getPhoneVerifiedAt() {
        return phoneVerifiedAt;
    }

    public void setPhoneVerifiedAt(Date phoneVerifiedAt) {
        this.phoneVerifiedAt = phoneVerifiedAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Date emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public List<WxUser> getWxUsers() {
        return wxUsers;
    }

    public void setWxUsers(List<WxUser> wxUsers) {
        this.wxUsers = wxUsers;
    }
}
