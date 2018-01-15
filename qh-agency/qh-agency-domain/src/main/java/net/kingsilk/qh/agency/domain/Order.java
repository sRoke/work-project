package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.OrderStatusEnum;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 进货订单，每一步非操作人见OrderLog
 */
@Document
//@CompoundIndexes({
//        @CompoundIndex(def = "brandAppId:1,sellerPartnerId:1,buyerPartnerId:1}")
//})
public class Order extends Base {


    /**
     * 所属品牌商ID。
     */
    private String brandAppId;

    /**
     * 向哪个渠道商进货。如果为空，则表示直接向品牌商进货。
     */
    private String sellerPartnerId;

    /**
     * 向哪个渠道商进货。如果为空，则表示直接向品牌商进货。
     */
    private String buyerPartnerId;

    /**
     * 订单号
     */
    private String seq;


    /**
     * 下单渠道商员工
     */
    @DBRef
    private PartnerStaff partnerStaff;

    /**
     * 商品合计,原价，不包含改价
     * SUM(sku.parterTagPrice * sku.num)
     */
    private Integer orderPrice;

    /**
     * 客服调整的金额。
     * <p>
     * - 正值 : 让购买者多支付
     * - 负值 : 让购买者少支付
     */
    private Integer adjustPrice = 0;

    /**
     * 实际支付价
     * <p>
     * = orderPrice - 优惠 + 改价(adjustPrice) + 邮费 + ...
     */
    private Integer paymentPrice;

    /**
     * 余额抵扣金额
     */
    private Integer balancePrice;

    /**
     * 货款抵扣金额
     */
    private Integer noCashBalancePrice;


    /**
     * 订单当前状态
     */
    private OrderStatusEnum status;

//    /**
//     * 支付记录
//     */
//    @DBRef
//    private QhPay qhPay;

    /**
     * 订单前一次状态
     */
    private OrderStatusEnum lastStatus;

    /**
     * 渠道商收货地址
     */
    private OrderAddress orgShippingAddr;


    /**
     * 卖家备注
     */
    private String sellerMemo;

    /**
     * 买家备注
     */
    private String buyerMemo;

    /**
     * 下单的商品
     */
    private List<OrderItem> orderItems = new ArrayList<>();

    public static class OrderItem {
        /**
         * 关联的品牌商Id
         */
        private String brandAppId;

        /**
         * 关联的订单
         */
        @DBRef
        private Order order;

        /**
         * 下单的商品
         */
        @DBRef
        private Sku sku;

        /**
         * 发生的退款
         * //FIXME 该字段是否有意义，是否可去掉
         */
        @DBRef
        private Refund refund;

        /**
         * 下单数量
         */
        private Integer num = 0;

        /**
         * 提交订单时刻,sku的单价
         * 增加该字段原因：若取sku的价格，sku可能会修改，不准确
         */
        private Integer skuPrice = 0;


   /* XXX DO NOT DELETE ME

    单品活动价格，初始值为 skuPrice.
    如果启用，则 realTotalPrice = activityPrice * num - 订单类优惠百分比 + 订单总改价的百分比
    private Integer activityPrice = 0;
    */



    /* XXX DO NOT DELETE ME

    提交订单后，单个SKU改价。
    如果启用，则 realTotalPrice = usedPrice * num - 订单类优惠百分比 + (订单总改价的百分比 或 adjustSkuPrice *  num)
    private Integer adjustSkuPrice = 0;
    */

        /**
         * 订单中该商品实际的支付金额
         * skuPrice * num - 优惠百分比 + 订单总改价的百分比
         */
        private Integer realTotalPrice = 0;

        public String getBrandAppId() {
            return brandAppId;
        }

        public void setBrandAppId(String brandAppId) {
            this.brandAppId = brandAppId;
        }

        public Order getOrder() {
            return order;
        }

        public void setOrder(Order order) {
            this.order = order;
        }

        public Sku getSku() {
            return sku;
        }

        public void setSku(Sku sku) {
            this.sku = sku;
        }

        public Refund getRefund() {
            return refund;
        }

        public void setRefund(Refund refund) {
            this.refund = refund;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public Integer getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(Integer skuPrice) {
            this.skuPrice = skuPrice;
        }

        public Integer getRealTotalPrice() {
            return realTotalPrice;
        }

        public void setRealTotalPrice(Integer realTotalPrice) {
            this.realTotalPrice = realTotalPrice;
        }
    }

    public static class OrderAddress {
        private String adc;
        private String street;
        private String receiver;
        private String phone;

        public String getAdc() {
            return adc;
        }

        public void setAdc(String adc) {
            this.adc = adc;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }


    // --------------------------------------- getter && setter

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getSellerPartnerId() {
        return sellerPartnerId;
    }

    public void setSellerPartnerId(String sellerPartnerId) {
        this.sellerPartnerId = sellerPartnerId;
    }

    public String getBuyerPartnerId() {
        return buyerPartnerId;
    }

    public void setBuyerPartnerId(String buyerPartnerId) {
        this.buyerPartnerId = buyerPartnerId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public PartnerStaff getPartnerStaff() {
        return partnerStaff;
    }

    public void setPartnerStaff(PartnerStaff partnerStaff) {
        this.partnerStaff = partnerStaff;
    }

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(Integer adjustPrice) {
        this.adjustPrice = adjustPrice;
    }

    public Integer getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(Integer paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

//    public QhPay getQhPay() {
//        return qhPay;
//    }
//
//    public void setQhPay(QhPay qhPay) {
//        this.qhPay = qhPay;
//    }

    public OrderStatusEnum getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(OrderStatusEnum lastStatus) {
        this.lastStatus = lastStatus;
    }

    public OrderAddress getOrgShippingAddr() {
        return orgShippingAddr;
    }

    public void setOrgShippingAddr(OrderAddress orgShippingAddr) {
        this.orgShippingAddr = orgShippingAddr;
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getBalancePrice() {
        return balancePrice;
    }

    public void setBalancePrice(Integer balancePrice) {
        this.balancePrice = balancePrice;
    }

    public Integer getNoCashBalancePrice() {
        return noCashBalancePrice;
    }

    public void setNoCashBalancePrice(Integer noCashBalancePrice) {
        this.noCashBalancePrice = noCashBalancePrice;
    }
}
