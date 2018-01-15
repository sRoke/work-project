package net.kingsilk.qh.shop.core;

/**
 * Created by lit on 17/8/24.
 */
public enum AccountChangeTypeEnum {

    //    RECHARGE("充值"),
    PAY("PAY", "主动支付"),
    PAY_CASH_DEPOSIT("PAY_CASH_DEPOSIT", "缴纳保证金"),    //之后渠道商加入可能会有保证金等费用
    WITHDRAW_WX("WITHDRAW_WX", "微信提现"),
    WITHDRAW_ALIPAY("WITHDRAW_ALIPAY", "支付宝提现"),
    SELL("SELL", "销售"),
    BARGAINSELL("BARGAINSELL", "砍价销售"),
    SELL_REFUND("SELL_REFUND", "销售退货");
    //TODO 采购退款


    AccountChangeTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    AccountChangeTypeEnum(String code) {
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
