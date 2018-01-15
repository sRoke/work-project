package net.kingsilk.qh.agency.core;

/**
 * Created by zcw on 3/13/17.
 * 订单操作
 */
public enum OrderOperateEnum {
    /////正常流程
    SUBMIT("SUBMIT", "用户提交订单"),
    PAY("PAY", "用户支付"),
    REJECTE("REJECTE", "平台拒绝接单"),
    CONFIRM("CONFIRM", "平台确认接单"),
    SELLER_DELIVER("SELLER_DELIVER", "平台发货"),
    USER_RECEIVE("USER_RECEIVE", "用户确认收货"),
    COMMENT("COMMENT", "用户评论"),

    //////退货流程
    REFUND_ITEM("REFUND_ITEM", "用户申请退货"),
    CONFIRM_REFUND("CONFIRM_REFUND", "同意售后"),
    USER_DELIVER("USER_DELIVER", "用户发货"),
    //SELLER_RECEIVE("SELLER_RECEIVE", "平台确认收货并退款"),
    REFUND_FINISHED("REFUND_FINISHED", "退款完成"),
    REFUND_CLOSED("REFUND_CLOSED","售后关闭");

    OrderOperateEnum(String code) {
        this(code, null);
    }

    OrderOperateEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
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
