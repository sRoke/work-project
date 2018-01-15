package net.kingsilk.qh.oauth.core;

/**
 * 演示用枚举
 *
 * @deprecated
 */
@Deprecated
public enum UserTypeEnum {
    /**
     * 用户（人）
     */
    USER("USER", "用户"),

    /**
     * 应用
     */
    APP("APP", "应用");

    private final String code;
    private final String desp;

    UserTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    UserTypeEnum(String code) {
        this(code, null);
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }
}
