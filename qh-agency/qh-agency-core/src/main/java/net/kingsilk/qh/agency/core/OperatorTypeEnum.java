package net.kingsilk.qh.agency.core;

/**
 * Created by lit on 17/8/24.
 */
public enum OperatorTypeEnum {

    CREATE("CREATE","创建订单"),
    CANCEL("CANCEL","取消订单"),
    REJECTE("REJECTE", "平台拒绝接单"),
    PAYMENT("PAYMENT","支付订单"),
    PAYED("PAYED","已付款"),
    MODIFIED("MODIFIED","修改订单价格"),
    CONFIRMED("CONFIRMED","用户确认收货"),
    CANCELING("CANCELING","申请取消中"),
    DELETE("DELETE","删除订单"),
    CONFIRM_PAYMENT("CONFIRM_PAYMENT","确认接单"),
    REFUNDED("REFUNDED","退款成功"),
    SHIPPED("SHIPPED","平台发货"),
    COMMENTED("COMMENTED","用户评论"),
    CLOSED("CLOSED","已关闭"),
    SUCCESS("SUCCESS","已完成"),
    ALLOCATION("ALLOCATION","调拨中"),

    /*退货退款*/
    MONEY_ONLY("MONEY_ONLY","买家申请仅退款"),
    GOOGS_AND_MONEY("GOOGS_AND_MONEY","买家申请退货并退款"),
    USER_REFUND("USER_REFUND","买家退货"),
    CANCEL_MONEY("CANCEL_MONEY","买家取消申请退款"),
    REJECTED("REJECTED","卖家拒绝申请"),
    ADJUSTED("ADJUSTED","卖家修改了退款金额"),
    AGREE_BUYER_SENDING("AGREE_BUYER_SENDING","同意买家退货"),
    COURIER_LOST_REFUND("COURIER_LOST_REFUND","快递丢失，同意退款"),
    REFUSED_SELLER_RECEIVED("REFUSED_SELLER_RECEIVED","快递拒签，待确认收货"),
    DELIVERY_OF_BUYER_ADJUST_SIGN("DELIVERY_OF_BUYER_ADJUST_SIGN","快递未达，待买家签收/拒收"),
    CONFIRM_MONEY_ONLY("CONFIRM_MONEY_ONLY","确认仅退款"),
    CONFIRM_GOODS_MONEY("CONFIRM_GOODS_MONEY","确认退货并退款"),
    NEGOTIATE_REFUND("NEGOTIATE_REFUND","协商退款"),
    SELLER_CLOSE("SELLER_CLOSE","卖家关闭"),
    SELLER_FORCEREFUND("SELLER_FORCEREFUND","卖家强制关闭");

    OperatorTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    OperatorTypeEnum(String code) {
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
