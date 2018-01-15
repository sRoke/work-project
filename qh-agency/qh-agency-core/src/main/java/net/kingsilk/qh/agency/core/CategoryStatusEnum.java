package net.kingsilk.qh.agency.core;

/**
 * 分类状态。
 */
public enum CategoryStatusEnum {
    NORMAL("NORMAL", "正常"),
    DISABLED("DISABLED", "禁用");


    private final String code;
    private final String desp;

    CategoryStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    CategoryStatusEnum(String code) {
        this(code, null);
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

}
