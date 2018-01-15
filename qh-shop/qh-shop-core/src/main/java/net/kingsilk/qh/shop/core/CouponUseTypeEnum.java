package net.kingsilk.qh.shop.core;


/**
 * 优惠卷使用类型
 */
public enum CouponUseTypeEnum {

    CURRENCY("CURRENCY","全场通用"),
    CLEAN_ORDER("CLEAN_ORDER","清洗服务专用"),
    GIVEN_SKU("GIVEN_SKU","指定商品使用");

    CouponUseTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    CouponUseTypeEnum(String code) {
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
