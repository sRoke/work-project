package net.kingsilk.qh.agency.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 支付相关
 */
@Document
public class QhPay extends Base {


    /**
     * 所属品牌。
     */
    private String brandAppId;

    /**
     * 支付的订单
     */
    @DBRef
    private Order order;


    /**
     * 支付类型,ALI / WEIXIN  FIXME 应当使用枚举
     */
    private String payType;

    /**
     * 支付时间
     */
    private Date payTime;


    /**
     * 订单总金额 = （余额支付的金额+第三方支付金额）
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
    private Integer refundAmount = 0;

    // --------------------------------------- getter && setter


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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
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
}
