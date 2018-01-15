package net.kingsilk.qh.agency.core;

/**
 * 商品属性属于哪种系统预定义的类型
 */
public enum ItemPropSysTypeEnum {
    SILK_WEIGHT("SILK_WEIGHT","蚕丝净重"),
    SILK_GRADE("SILK_GRADE","蚕丝等级"),
    QUILT_SIZE("QUILT_SIZE","被子尺寸"),
    QUILT_SEASON("QUILT_SEASON","被子使用季节"),
    QUILT_COVER_FABRIC("QUILT_COVER_FABRIC","胎套面料类别"),
    QUILT_COVER_THICKNESS("QUILT_COVER_THICKNESS","胎套面料支数");

    ItemPropSysTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    ItemPropSysTypeEnum(String code) {
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

    private final String code;
    private final String desp;
}
