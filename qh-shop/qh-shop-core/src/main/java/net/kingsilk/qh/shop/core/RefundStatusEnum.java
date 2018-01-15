package net.kingsilk.qh.shop.core;

/**
 * 退款状态
 */
public enum RefundStatusEnum {

    UNCHECKED("UNCHECKED", "待确认"),
    REJECTED("REJECTED", "已拒绝"),
    WAIT_BUYER_SENDING("WAIT_BUYER_SENDING", "待寄件"),
    WAIT_SELLER_RECEIVED("WAIT_SELLER_RECEIVED", "待收货"),
    //   -- WAIT_BUYER_ADJUST_SIGN("WAIT_BUYER_ADJUST_SIGN","等待买家拒收或签收"),
    REFUNDING("REFUNDING", "待退款"),
    REJECTING("REJECTING", "拒绝中"),
    REFUNDFILED("REFUNDFILED", "退款失败"),
    FINISHED("FINISHED", "已完成");
//    CANCELING("CANCELING", "申请取消中");
//--    CLOSED("CLOSED","已关闭");

    private final String code;

    private final String desp;

    RefundStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }

}
