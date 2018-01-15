package net.kingsilk.qh.shop.core;


/**
 * 优惠卷类型
 */
public enum CouponTypeEnum {
//    FREE_USE("普通优惠"),
//    ITEM_REDUCE("商品优惠"),
//    FULL_REDUCE("满减");
//    清洗订单优惠券只能是折扣优惠券
      ORDER("普通优惠券"),
//    SILK_RENOVATE("翻新优惠券"),
        DISCOUNT("折扣优惠券");

    CouponTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    CouponTypeEnum(String code) {
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
