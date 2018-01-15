package net.kingsilk.qh.agency.core;

/**
 * Created by lit on 17/8/24.
 */
public enum AccountChangeTypeEnum {

//    RECHARGE("充值"),
    ORDER("ORDER","采购"),
    RETAIL_ORDER("RETAIL_ORDER","收银"),        //零售订单
    PAY("PAY","主动支付"),
    REFUND("REFUND","采购退货"),
    PAY_CASH_DEPOSIT("PAY_CASH_DEPOSIT","缴纳保证金"),    //之后渠道商加入可能会有保证金等费用
    WITHDRAW_CASH("WITHDRAW_CASH","提现"),
    ORDER_CANCLE("ORDER_CANCLE","采购退款"),
    RETAIL_REFUND("RETAIL_REFUND","收银退款"),        //零售退款
    SELL("SELL","销售"),
    SELL_REFUND("SELL_REFUND","销售退货");
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
