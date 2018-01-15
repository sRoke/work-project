package net.kingsilk.qh.agency.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * 退款sku清单表，若为订单类退款，不再该表做记录
 */
public class RefundSku extends Base {

    /**
     * 关联的退款订单
     */
    @DBRef
    private Refund refund;

    /**
     * 对应的sku
     */
    @DBRef
    private Sku sku;

    /**
     * 该SKU所退的数量
     */
    private int num;

    /**
     * 当前Sku退款时应退的价格
     */
    private int price;

    /**
     * 实际退款金额
     */
    private int refundAmount;

    public Refund getRefund() {
        return refund;
    }

    public void setRefund(Refund refund) {
        this.refund = refund;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(int refundAmount) {
        this.refundAmount = refundAmount;
    }
}
