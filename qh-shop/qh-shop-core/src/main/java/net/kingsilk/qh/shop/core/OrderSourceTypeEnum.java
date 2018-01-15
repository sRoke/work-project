package net.kingsilk.qh.shop.core;

/**
 * Created by lit on 17/11/6.
 */
public enum OrderSourceTypeEnum {

    CASHIER("CASHIER", "收银订单"),
    SINCE("SINCE", "自提订单"),
    MALL("MALL", "线上订单");

    OrderSourceTypeEnum(String code) {
        this(code, null);
    }

    OrderSourceTypeEnum(String code, String desp) {
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
