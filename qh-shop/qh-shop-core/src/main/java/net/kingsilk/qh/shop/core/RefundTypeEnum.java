package net.kingsilk.qh.shop.core;

/**
 * Created by lit on 17/8/23.
 */
public enum RefundTypeEnum {

    ALIPAY("ALIPAY", "支付宝"),
    WX("WX", "微信支付"),
    ONLYMONEY("ONLYMONEY", "仅退款"),
    ITEM("ITEM", "退货并退款");

    RefundTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    RefundTypeEnum(String code) {
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
