package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.PayTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 支付
 */
@Document
public class QhPay extends Base {

    private String brandAppId;

    /**
     * 支付编号
     */
    private String seq;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 支付类型
     */
    private PayTypeEnum payType;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 需要支付的总金额
     */
    private Integer totalAmount;

    /**
     * 余额支付的金额
     */
    private Integer balanceAmount;

    /**
     * 第三方支付金额
     */
    private Integer thirdPayAmount;

    /**
     * 已退款的金额
     */
    private Integer refundAmount;

    /**
     * 支付的用户id
     */
    private String userId;

    /**
     * 备注
     */
    private String memo;

    /**
     * 门店ID
     */
    private String shopId;


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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public PayTypeEnum getPayType() {
        return payType;
    }

    public void setPayType(PayTypeEnum payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Integer balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Integer getThirdPayAmount() {
        return thirdPayAmount;
    }

    public void setThirdPayAmount(Integer thirdPayAmount) {
        this.thirdPayAmount = thirdPayAmount;
    }

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
