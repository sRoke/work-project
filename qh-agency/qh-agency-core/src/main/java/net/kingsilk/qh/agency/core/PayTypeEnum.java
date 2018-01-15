package net.kingsilk.qh.agency.core;

/**
 * Created by lit on 17/9/1.
 */
public enum PayTypeEnum {


    ALIPAY("ALIPAY", "支付宝"),
    WEIXIN("WEIXIN", "微信支付");

    PayTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    PayTypeEnum(String code) {
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
