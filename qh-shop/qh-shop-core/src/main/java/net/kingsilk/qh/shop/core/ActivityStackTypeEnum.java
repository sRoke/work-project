package net.kingsilk.qh.shop.core;


/**
 * 优惠卷 是否可以叠加
 */
public enum ActivityStackTypeEnum {

    NOT_STACK("NOT_STACK","不可叠加使用"),
    YES_STACK("YES_STACK","可以叠加使用");

    ActivityStackTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    ActivityStackTypeEnum(String code) {
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
