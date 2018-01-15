package net.kingsilk.qh.shop.core;


/**
 * 优惠卷定义 的状态
 */
public enum CouponDefStatusEnum {
    EDITING("EDITING","编辑中"),
    NORMAL("NORMAL","正常"),
    DISABLED("DISABLED","禁用");

    CouponDefStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    CouponDefStatusEnum(String code) {
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
