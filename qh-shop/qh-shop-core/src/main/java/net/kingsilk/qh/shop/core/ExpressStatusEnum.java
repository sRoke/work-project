package net.kingsilk.qh.shop.core;

/**
 * Created by lit on 17/8/22.
 */
public enum ExpressStatusEnum {

    SIGNED("SIGNED","已签收"),
    REFUSED("REFUSED","已拒签"),
    DELIVERY_OF("DELIVERY_OF","快递未达"),
    COURIER_LOST("COURIER_LOST","快递丢失");

    ExpressStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    ExpressStatusEnum(String code) {
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
