package net.kingsilk.qh.agency.core;

public enum DeductibleTypeEnum {

    BALANCE("BALANCE", "余额支付"),
    NO_CASH_BALANCE("NO_CASH_BALANCE", "货款支付"),
    BOTH("BOTH", "两者都有");

    DeductibleTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    DeductibleTypeEnum(String code) {
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
