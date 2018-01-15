package net.kingsilk.qh.agency.core;

/**
 * Created by zcw on 3/13/17.
 * 短信类型
 */
public enum SmsTypeEnum {
    CAPTCHA("CAPTCHA", "验证码");

    SmsTypeEnum(String code) {
        this(code, null);
    }

    SmsTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }

    public final String getDescription() {
        return desp;
    }

    private final String code;
    private final String desp;
}
