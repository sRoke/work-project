package net.kingsilk.qh.agency.core;

/**
 * 钱皇所有的项目应用。
 */
public enum QhAppEnum {

    WWW("WWW", "电脑网站"),
    WAP("WAP", "手机网站"),
    ADMIN("ADMIN", "管理后台"),
    ANDROID("ANDROID", "安卓APP"),
    IOS("IOS", "苹果APP");

    QhAppEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    QhAppEnum(String code) {
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