package net.kingsilk.qh.agency.core;

/**
 * Created by zcw on 3/13/17.
 * 短信类型
 */
public enum SmsPlatformEnum {
    /**
     * 云片网
     */
    YP("YP", "云片网"),

    /**
     * 阿里大于
     */
    ALI("ALI", "阿里大于");

    SmsPlatformEnum(String code) {
        this(code, null);
    }

    SmsPlatformEnum(String code, String desp) {
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
