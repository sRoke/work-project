package net.kingsilk.qh.agency.core;

/**
 * Created by lit on 17/7/18.
 */
public enum SourceTypeEnum {

    PARTNER_DELIVER("PARTNER_DELIVER", "渠道商发货"),
    BRAND_DELIVER("BRAND_DELIVER", "品牌商发货"),
    ALLOCATION_DELIVER("ALLOCATION_DELIVER", "调拨发货");

    SourceTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    SourceTypeEnum(String code) {
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
