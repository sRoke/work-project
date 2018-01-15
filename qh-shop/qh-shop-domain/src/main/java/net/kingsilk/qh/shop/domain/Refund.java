package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.ExpressStatusEnum;
import net.kingsilk.qh.shop.core.RefundStatusEnum;
import net.kingsilk.qh.shop.core.RefundTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 退款
 */
@Document
public class Refund extends Base {

    private String brandAppId;

    /**
     * 编号
     */
    private String seq;

    /**
     * 退款状态
     */
    private RefundStatusEnum refundStatus;

    /**
     * 客户id
     */
    private String memberId;

    /**
     * 类型
     */
    private RefundTypeEnum refundType;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 申请退货的skus
     */
    private Set<Sku> skus = new LinkedHashSet<>();

    /**
     * 申请退货的金额
     */
    private Integer refundMoney;

    /**
     * 客服修改的金额
     */
    private Integer adjustMoney;

    /**
     * 需退款到支付宝的最终金额(单价：分)
     */
    private Integer aliAmount = 0;

    /**
     * 需退款到微信的最终金额(单价：分)
     */
    private Integer wxAmount = 0;

    /**
     * 退货原因
     */
    private String reason;

    /**
     * 备注
     */
    private String memo;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 退货款快递状态
     */
    private ExpressStatusEnum expressStatus;

    /**
     * 买家退货时间
     */
    private Date buyerDeliveredTime;

    /**
     * 卖家确认收货时间
     */
    private Date receivedTime;

    /**
     * 物流信息
     */
    private String logisticsId;

    /**
     * 门店
     */
    private String shopId;

    /**
     * 退货地址
     */
    private RefundAddress refundAddress;

    public static class RefundAddress {
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

    public RefundAddress getRefundAddress() {
        return refundAddress;
    }

    public void setRefundAddress(RefundAddress refundAddress) {
        this.refundAddress = refundAddress;
    }

    public Integer getAliAmount() {
        return aliAmount;
    }

    public void setAliAmount(Integer aliAmount) {
        this.aliAmount = aliAmount;
    }

    public Integer getWxAmount() {
        return wxAmount;
    }

    public void setWxAmount(Integer wxAmount) {
        this.wxAmount = wxAmount;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public RefundStatusEnum getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(RefundStatusEnum refundStatus) {
        this.refundStatus = refundStatus;
    }

    public RefundTypeEnum getRefundType() {
        return refundType;
    }

    public void setRefundType(RefundTypeEnum refundType) {
        this.refundType = refundType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Set<Sku> getSkus() {
        return skus;
    }

    public void setSkus(Set<Sku> skus) {
        this.skus = skus;
    }

    public Integer getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Integer refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Integer getAdjustMoney() {
        return adjustMoney;
    }

    public void setAdjustMoney(Integer adjustMoney) {
        this.adjustMoney = adjustMoney;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public ExpressStatusEnum getExpressStatus() {
        return expressStatus;
    }

    public void setExpressStatus(ExpressStatusEnum expressStatus) {
        this.expressStatus = expressStatus;
    }

    public Date getBuyerDeliveredTime() {
        return buyerDeliveredTime;
    }

    public void setBuyerDeliveredTime(Date buyerDeliveredTime) {
        this.buyerDeliveredTime = buyerDeliveredTime;
    }

    public Date getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Date receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
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

    //内部类
    public static class Sku {

        //申请退货的数量
        private Integer applyedNum;

        //申请退货的订单总金额
        private Integer orderPrice;

        //申请退货的sku实际支付金额
        private Integer skuPrice;

        //skuId
        private String skuId;

        public Integer getApplyedNum() {
            return applyedNum;
        }

        public void setApplyedNum(Integer applyedNum) {
            this.applyedNum = applyedNum;
        }

        public Integer getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(Integer orderPrice) {
            this.orderPrice = orderPrice;
        }

        public Integer getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(Integer skuPrice) {
            this.skuPrice = skuPrice;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }
    }

}
