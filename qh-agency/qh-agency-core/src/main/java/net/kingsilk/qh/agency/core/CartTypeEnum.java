package net.kingsilk.qh.agency.core;

/**
 * Created by lit on 17/8/15.
 */
public enum CartTypeEnum {
    REFUND("REFUND","退货购物车"),
    PURCHASE("PURCHASE","进货购物车");

    CartTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    CartTypeEnum(String code) {
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
