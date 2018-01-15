package net.kingsilk.qh.agency.core;

/**
 *
 */
public enum WithdrawCashStatusEnum {
    CASHING("PAY","提现处理中"),
    SUCCESS("SUCCESS","提现成功"),
    REJECT("REJECT","提现被拒绝"),
    ERROR("ERROR","提现失败");
    WithdrawCashStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    WithdrawCashStatusEnum(String code) {
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
