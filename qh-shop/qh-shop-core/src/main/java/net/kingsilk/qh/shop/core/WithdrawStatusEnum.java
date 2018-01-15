package net.kingsilk.qh.shop.core;


/**
 * 佣金提现状态
 */
public enum WithdrawStatusEnum {

    CASHING("CASHING","提现中"),
    FINISH("FINISH","提现成功"),
    FAIL("FAIL","提现失败");

    WithdrawStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    WithdrawStatusEnum(String code) {
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
