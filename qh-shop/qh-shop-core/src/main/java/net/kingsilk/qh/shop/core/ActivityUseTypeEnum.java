package net.kingsilk.qh.shop.core;


/**
 * 优惠卷类型
 */
public enum ActivityUseTypeEnum {
    EDITING("EDITING","编辑中"),
    NORMAL("NORMAL","正常"),
    DISABLED("DISABLED","禁用");

    ActivityUseTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    ActivityUseTypeEnum(String code) {
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
