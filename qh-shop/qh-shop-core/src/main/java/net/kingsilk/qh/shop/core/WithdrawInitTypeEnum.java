package net.kingsilk.qh.shop.core;


/**
 * 提现发起类型类型
 */
public enum WithdrawInitTypeEnum {

    INIT_BY_USER("INIT_BY_USER","用户发起"),
    INIT_BY_SYS("INIT_BY_SYS","系统发放"),
    INIT_BY_STAFF("INIT_BY_STAFF","员工发放");

    WithdrawInitTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    WithdrawInitTypeEnum(String code) {
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
