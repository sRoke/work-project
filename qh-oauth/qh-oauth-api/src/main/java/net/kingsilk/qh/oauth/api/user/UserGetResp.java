package net.kingsilk.qh.oauth.api.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 *
 */
@ApiModel
public class UserGetResp {

    // --------------------------------------- common fields

    @ApiModelProperty("用户ID")
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
    private Boolean enabled;

    @ApiModelProperty("帐号是否锁定")
    private Boolean accountLocked;

    @ApiModelProperty("帐号是否过期")
    private Boolean accountExpired;

    @ApiModelProperty("邀请人ID")
    private String inviter;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("手机号验证的时间")
    private Date phoneVerifiedAt;

    @ApiModelProperty("邮箱")
    private Date email;

    @ApiModelProperty("邮箱验证的时间")
    private Date emailVerifiedAt;

    @ApiModelProperty("真实姓名")
    private String realName;
    // --------------------------------------- UserInfo

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("当前授权微信mpAppId")
    private String curWxMpId;
    // --------------------------------------- WxUser

    @ApiModelProperty("绑定的微信账号")
    List<WxUser> wxUsers;


    @ApiModel
    public static class WxUser {

        @ApiModelProperty("微信 APP ID")
        private String appId;

        @ApiModelProperty("OpenId")
        private String openId;

        @ApiModelProperty("UnionId")
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


    // ------------------------ 自动生成的 getter、 setter

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Boolean getAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(Boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
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

    public Date getEmail() {
        return email;
    }

    public void setEmail(Date email) {
        this.email = email;
    }

    public Date getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(Date emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<WxUser> getWxUsers() {
        return wxUsers;
    }

    public void setWxUsers(List<WxUser> wxUsers) {
        this.wxUsers = wxUsers;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCurWxMpId() {
        return curWxMpId;
    }

    public void setCurWxMpId(String curWxMpId) {
        this.curWxMpId = curWxMpId;
    }
}
