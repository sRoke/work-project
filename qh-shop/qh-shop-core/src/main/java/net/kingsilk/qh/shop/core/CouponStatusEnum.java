package net.kingsilk.qh.shop.core;


/**
 * 优惠卷状态
 */
public enum CouponStatusEnum {
    NORMAL("NORMAL","正常"), // 正常的优惠券包括已经过期的，
    EXPORED("EXPORED","已过期"),// 已经过期由于暂时无法判断，该状态值暂时不采用
    USED("USED","已使用");

    CouponStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    CouponStatusEnum(String code) {
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
