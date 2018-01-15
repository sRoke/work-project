package net.kingsilk.qh.agency.core;

/**
 * 员工状态。
 * @deprecated 只是启用、禁用状态的，使用 boolean 字段取代。
 */
@Deprecated
public enum StaffStatusEnum {

    NORMAL("NORMAL", "正常"),
    DISABLED("DISABLED", "禁用");


    private final String code;
    private final String desp;

    StaffStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    StaffStatusEnum(String code) {
        this(code, null);
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }
}