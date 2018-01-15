package net.kingsilk.qh.oauth.domain;

import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 演示用 Domain
 * <p>
 * 字段允许的类型：java基础数据类型以及其封装类型。
 */
@Document
@CompoundIndexes({
})
public class User extends Base {

    /**
     * 是否有效
     */
    private boolean enabled = true;

    /**
     * 帐号是否锁定
     */
    // FIXME: 账户被锁定的同时密码也不存在，则视为刚输入手机号码注册。但没有输入密码
    private boolean accountLocked = false;

    /**
     * 帐号是否过期
     */
    private boolean accountExpired = false;

    /**
     * 邀请人
     */
    private String inviterUserId;

    // ---------------------------------- 绑定用户信息:  用户名、密码、支付密码

    /**
     * 登录用的用户名
     */
    @Indexed(unique = true, sparse = true)
    @TextIndexed
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 支付密码
     *
     * @deprecated 暂未使用
     */
    @Deprecated
    private String payPassword;


    // ---------------------------------- 绑定用户信息:  手机号
    /**
     * 手机号
     */
    @Indexed(unique = true, sparse = true)
    @TextIndexed
    private String phone;

    /**
     * 手机号验证的时间。是有当该字段非空时，才表示手机号完成绑定。
     */
    private Date phoneVerifiedAt;

//
//    // ---------------------------------- 绑定用户信息:  微信公众号的账户
//    // 注意：该表中微信用户信息必须是同一个微信开放平台下的应用
//
//
//    /**
//     * 微信授权登录对应的openId
//     */
//    @Indexed(unique = true, sparse = true)
//    private String openId;
//
//
//    /**
//     * 微信登录时的code
//     */
//    private String code;
//
//    // ---------------------------------- 绑定用户信息:  微信企业号的员工账户
//    // 注意：该表中微信用户信息必须是同一个微信开放平台下的应用
//
//
//    /**
//     * 微信授权登录对应的openId
//     */
//    @Indexed(unique = true, sparse = true)
//    private String wxQyhOpenId;
//
//
//    /**
//     * 微信登录时的code
//     */
//    @Indexed(unique = true, sparse = true)
//    private String wxQyhUserId;


    // ---------------------------------- 绑定用户信息:  电子邮箱

    /**
     * 邮箱
     */
    @Indexed(unique = true, sparse = true)
    @TextIndexed
    private String email;

    /**
     * 邮箱验证的时间。是有当该字段非空时，才表示邮箱完成绑定。
     */
    private Date emailVerifiedAt;


    // ------------------------ 自动生成的 getter、 setter


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
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
//
//    public String getOpenId() {
//        return openId;
//    }
//
//    public void setOpenId(String openId) {
//        this.openId = openId;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getWxQyhOpenId() {
//        return wxQyhOpenId;
//    }
//
//    public void setWxQyhOpenId(String wxQyhOpenId) {
//        this.wxQyhOpenId = wxQyhOpenId;
//    }
//
//    public String getWxQyhUserId() {
//        return wxQyhUserId;
//    }
//
//    public void setWxQyhUserId(String wxQyhUserId) {
//        this.wxQyhUserId = wxQyhUserId;
//    }

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
}
