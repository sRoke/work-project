package net.kingsilk.qh.shop.core;

/**
 * 优惠卷来源类型
 */
public enum CouponOriginEnum {

    NEW_USER("NEW_USER","新用户优惠券"),
    USED("USED","领用"),
    EXCHANGE("EXCHANGE","积分商城兑换"),
    RAFFLE("RAFFLE","抽奖活动");

    CouponOriginEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    CouponOriginEnum(String code) {
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
