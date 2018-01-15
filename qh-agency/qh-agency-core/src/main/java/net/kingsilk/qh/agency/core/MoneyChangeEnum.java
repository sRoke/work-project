package net.kingsilk.qh.agency.core;

public enum MoneyChangeEnum {

    ALIPAY("ALIPAY", "支付宝"),
    WX("WX", "微信"),
    BALANCE("BALANCE","账户"),
    NOCASHBALANCE("NOCASHBALANCE","货款"),
    OWEDBALANCE("OWEDBALANCE","欠款");

    MoneyChangeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    MoneyChangeEnum(String code) {
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
