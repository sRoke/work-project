package net.kingsilk.qh.oauth.core;

/**
 * 演示用枚举
 *
 * @deprecated 仅仅示例用
 */
@Deprecated
public enum XxxEnum {
    /**
     * 额外的说明文档
     */
    ONE("ONE", "壹"),

    /**
     * 额外的说明文档
     */
    TWO("TWO", "贰"),

    /**
     * 额外的说明文档
     */
    THREE("THREE", "叁");

    private final String code;
    private final String desp;

    XxxEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    XxxEnum(String code) {
        this(code, null);
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }
}
