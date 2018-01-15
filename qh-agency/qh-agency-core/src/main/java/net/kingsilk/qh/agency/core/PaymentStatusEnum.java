package net.kingsilk.qh.agency.core;

/**
 * Created by lit on 17/9/1.
 */
public enum PaymentStatusEnum {
    UNPAYED("UNPAYED", "待支付"),
    CANCELD("CANCELD", "已取消"),
    PAYED("PAYED", "已支付");

    PaymentStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    PaymentStatusEnum(String code) {
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
