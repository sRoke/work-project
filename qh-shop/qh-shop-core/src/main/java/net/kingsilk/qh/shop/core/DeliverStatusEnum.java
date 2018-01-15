package net.kingsilk.qh.shop.core;

/**
 * Created by lit on 17/7/18.
 */
public enum DeliverStatusEnum {

    UNSHIPPED("UNSHIPPED", "待发货"),
    UNRECEIVED("UNRECEIVED", "待收货"),
    RECEIVED("RECEIVED", "已完成");

    DeliverStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    DeliverStatusEnum(String code) {
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
