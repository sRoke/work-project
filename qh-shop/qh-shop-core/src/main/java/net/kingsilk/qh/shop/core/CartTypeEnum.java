package net.kingsilk.qh.shop.core;

/**
 *
 */
public enum CartTypeEnum {
    CASHIER("CASHIER", "收银购物车"),
    MALL("MALL", "线上会员购物车");

    CartTypeEnum(String code) {
        this(code, null);
    }

    CartTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
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
