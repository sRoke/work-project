package net.kingsilk.qh.oauth.api.s.attr;

import java.io.*;
import java.util.*;

/**
 * 保存在 session 中 手机号认证信息。
 */
public class PhoneAuthInfo implements Serializable {

    public static final long serialVersionUID = 1L;

    public static final String SESSION_KEY = PhoneAuthInfo.class.getName();

    public enum AuthType {

        AUTH,

        /** 通过手机短信验证码登录 */
        PHONE_LOGIN,

        /** 通过手机短信验证码注册 */
        PHONE_REG,

        /** 通过手机号短信验证码找回密码 */
        RETRIEVE_PWD,

        /** 绑定手机号 */
        BIND_PHONE
    }

    private String phone;
    private String code;
    private Date time;
    private AuthType authType;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }
}
