package net.kingsilk.qh.oauth.core;

/**
 * 登录类型
 */
public enum LoginTypeEnum {

    PHONE("PHONE", "手机密码登录"),
    PASSWORD("PASSWORD", "用户名密码登录"),
    WX_SCAN("WX_SCAN", "微信扫码登录"),
    WX("WX", "微信JSSDK登录"),
    WX_QYH_SCAN("WX_QYH_SCAN", "企业号扫码登录"),
    WX_QYH("WX_QYH_SCAN", "企业号JSSDK登录");

    private final String code;
    private final String desp;

    LoginTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    LoginTypeEnum(String code) {
        this(code, null);
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }
}
