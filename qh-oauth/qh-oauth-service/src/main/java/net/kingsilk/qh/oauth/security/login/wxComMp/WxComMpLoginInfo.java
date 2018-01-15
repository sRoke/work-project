package net.kingsilk.qh.oauth.security.login.wxComMp;

import java.io.*;
import java.util.*;

/**
 * @see WxComMpAuthInfo
 */
@Deprecated
public class WxComMpLoginInfo implements Serializable {

    public static final long serialVersionUID = 1L;

    private String wxComAppId;
    private String wxMpAppId;
    private String openId;
    private String code;
    private String state;

    /**
     * 微信授权时间
     */
    private Date wxAuthTime;

    public String getWxComAppId() {
        return wxComAppId;
    }

    public void setWxComAppId(String wxComAppId) {
        this.wxComAppId = wxComAppId;
    }

    public String getWxMpAppId() {
        return wxMpAppId;
    }

    public void setWxMpAppId(String wxMpAppId) {
        this.wxMpAppId = wxMpAppId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getWxAuthTime() {
        return wxAuthTime;
    }

    public void setWxAuthTime(Date wxAuthTime) {
        this.wxAuthTime = wxAuthTime;
    }
}
