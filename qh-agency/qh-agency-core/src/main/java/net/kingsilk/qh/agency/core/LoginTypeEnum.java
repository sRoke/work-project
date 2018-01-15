package net.kingsilk.qh.agency.core;

/**
 * 登录方式。
 */
public enum LoginTypeEnum {
    UserId("UserId", "用户ID"),
    UserName("UserName", "用户名"),
    Mobile("Mobile", "手机号"),
    Email("Email", "电子邮箱"),
    WeiXin("WeiXin", "微信"),
    QQ("QQ", "QQ"),
    ALIPAY("ALIPAY", "支付宝");

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

    private final String code;
    private final String desp;
}
