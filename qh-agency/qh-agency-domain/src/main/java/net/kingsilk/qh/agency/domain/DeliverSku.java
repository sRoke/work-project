package net.kingsilk.qh.agency.domain;

/**
 * 发货商品清单
 */
public class DeliverSku extends Base {
    /**
     * 发货单ID
     */
    private DeliverInvoice deliverInvoice;

    /**
     * SKU
     */
    private Sku sku;

    /**
     * SKU商品个数
     */
    private Integer num;

    /**
     * 销售价格
     */
    private Integer price;

    /**
     * 实际支付价格
     */
    private Integer paymentPrice;

    public DeliverInvoice getDeliverInvoice() {
        return deliverInvoice;
    }

    public void setDeliverInvoice(DeliverInvoice deliverInvoice) {
        this.deliverInvoice = deliverInvoice;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(Integer paymentPrice) {
        this.paymentPrice = paymentPrice;
    }
}
