package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.OrderSourceTypeEnum;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@Document
public class Order extends Base {

    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 门店员工ID,只在门店收银时有值
     */
    private String shopStaffId;

    /**
     * 系统编码
     */
    private String seq;

    /**
     * 订单类型
     */
    private OrderSourceTypeEnum sourceType;

    /**
     * 订单中的sku
     */
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * 订单状态
     */
    private OrderStatusEnum status;

    /**
     * 是否开发票
     */
    private String needInvoice;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 订单原价总和
     */
    private Integer totalPrice;

    /**
     * 使用的余额
     */
    private Integer usedBalance;

    /**
     * 使用的优惠券
     */
    private String couponId;

    /**
     * 优惠券抵用掉的总价格
     */
    private Integer couponPrice;

    /**
     * 使用的积分
     */
    private Integer usedIntegral;

    /**
     * 积分抵用的总价格
     */
    private Integer integralPrice;

    /**
     * 客服改价的总金额
     */
    private Integer adjustedAmount;

    /**
     * 支付记录id
     */
    private String qhPayId;

    /**
     * 使用的快递模板id
     */
    private String deliveryTemplateId;

    /**
     * 实际支付金额
     */
    private Integer paymentAmount;

    /**
     * 拒绝接单原因
     */
    private String refusedReason;

    /**
     * 确认收货时间
     */
    private Date receivedTime;

    /**
     * 到点自提时间（OrderSourceTypeEnum订单类型为自取是有值）
     */
    private Date sinceTakeTime;

    /**
     * 该比订单赠送的积分
     */
    private Integer integral;

    /**
     * 买家备注
     */
    private String buyerMemo;

    /**
     * 卖家备注
     */
    private String sellerMemo;

    /**
     * 运费
     */
    private Integer freight;

    private ShippingAddress addr;

    public class OrderItem {
        /**
         * 编号，前端生成
         */
        private String no;

        /**
         * 下单的Sku
         */
        private String skuId;

        /**
         * 下单的数量
         */
        private String num;

        /**
         * 发生退款的退款单
         */
        private String refundId;

        /**
         * 提交订单时刻,sku的单价
         */
        private String skuPrice;

        /**
         * sku在该订单中的总价格
         */
        private String orderPrice;

        /**
         * 活动减去的价格
         */
        private String activityPrice;

        /**
         * 优惠券抵用的价格
         */
        private String totalCouponPrice;

        /**
         * 使用的积分
         */
        private String useIntegral;

        /**
         * 积分抵用的价格
         */
        private String integralPrice;

        /**
         * 该单个sku实际支付的价格(四舍五入)
         */
        private String realPayPrice;

        /**
         * 实际需要支付的总价
         */
        private String allRealPayPrice;

        /**
         * 客服改价在该sku中减少的价格
         */
        private String adjustedAmount;

        public String getAllRealPayPrice() {
            return allRealPayPrice;
        }

        public void setAllRealPayPrice(String allRealPayPrice) {
            this.allRealPayPrice = allRealPayPrice;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getRefundId() {
            return refundId;
        }

        public void setRefundId(String refundId) {
            this.refundId = refundId;
        }

        public String getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(String skuPrice) {
            this.skuPrice = skuPrice;
        }

        public String getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
        }

        public String getActivityPrice() {
            return activityPrice;
        }

        public void setActivityPrice(String activityPrice) {
            this.activityPrice = activityPrice;
        }

        public String getTotalCouponPrice() {
            return totalCouponPrice;
        }

        public void setTotalCouponPrice(String totalCouponPrice) {
            this.totalCouponPrice = totalCouponPrice;
        }

        public String getUseIntegral() {
            return useIntegral;
        }

        public void setUseIntegral(String useIntegral) {
            this.useIntegral = useIntegral;
        }

        public String getIntegralPrice() {
            return integralPrice;
        }

        public void setIntegralPrice(String integralPrice) {
            this.integralPrice = integralPrice;
        }

        public String getRealPayPrice() {
            return realPayPrice;
        }

        public void setRealPayPrice(String realPayPrice) {
            this.realPayPrice = realPayPrice;
        }

        public String getAdjustedAmount() {
            return adjustedAmount;
        }

        public void setAdjustedAmount(String adjustedAmount) {
            this.adjustedAmount = adjustedAmount;
        }
    }

    public class ShippingAddress {
        /**
         * 六位ADC地区码
         */
        private String adc;

        /**
         * 街道
         */
        private String street;

        /**
         * 收货人
         */
        private String receiver;

        /**
         * 收货人联系方式
         */
        private String phone;

        /**
         * 备注
         */
        private String memo;

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

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }
    }


    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getShopStaffId() {
        return shopStaffId;
    }

    public void setShopStaffId(String shopStaffId) {
        this.shopStaffId = shopStaffId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public OrderSourceTypeEnum getSourceType() {
        return sourceType;
    }

    public void setSourceType(OrderSourceTypeEnum sourceType) {
        this.sourceType = sourceType;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public String getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(String needInvoice) {
        this.needInvoice = needInvoice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getUsedBalance() {
        return usedBalance;
    }

    public void setUsedBalance(Integer usedBalance) {
        this.usedBalance = usedBalance;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public Integer getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Integer couponPrice) {
        this.couponPrice = couponPrice;
    }

    public Integer getUsedIntegral() {
        return usedIntegral;
    }

    public void setUsedIntegral(Integer usedIntegral) {
        this.usedIntegral = usedIntegral;
    }

    public Integer getIntegralPrice() {
        return integralPrice;
    }

    public void setIntegralPrice(Integer integralPrice) {
        this.integralPrice = integralPrice;
    }

    public Integer getAdjustedAmount() {
        return adjustedAmount;
    }

    public void setAdjustedAmount(Integer adjustedAmount) {
        this.adjustedAmount = adjustedAmount;
    }

    public String getQhPayId() {
        return qhPayId;
    }

    public void setQhPayId(String qhPayId) {
        this.qhPayId = qhPayId;
    }

    public String getDeliveryTemplateId() {
        return deliveryTemplateId;
    }

    public void setDeliveryTemplateId(String deliveryTemplateId) {
        this.deliveryTemplateId = deliveryTemplateId;
    }

    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getRefusedReason() {
        return refusedReason;
    }

    public void setRefusedReason(String refusedReason) {
        this.refusedReason = refusedReason;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo;
    }

    public String getSellerMemo() {
        return sellerMemo;
    }

    public void setSellerMemo(String sellerMemo) {
        this.sellerMemo = sellerMemo;
    }

    public ShippingAddress getAddr() {
        return addr;
    }

    public void setAddr(ShippingAddress addr) {
        this.addr = addr;
    }

    public Date getSinceTakeTime() {
        return sinceTakeTime;
    }

    public void setSinceTakeTime(Date sinceTakeTime) {
        this.sinceTakeTime = sinceTakeTime;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }
}
