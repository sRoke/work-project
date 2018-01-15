package net.kingsilk.qh.shop.domain.bak;

import net.kingsilk.qh.shop.core.PayTypeEnum;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @Indexed
    private String brandAppId;

    /**
     * 收银对应的渠道商Id
     */
    @Indexed
    private String partnerId;

    /**
     * 支付的订单
     */
    private String orderId;


    /**
     * 支付类型,ALI / WEIXIN
     */
    private PayTypeEnum payType;

    /**
     * 支付时间
     */
    private Date payTime;


    /**
     * 支付的总金额
     */
    private Integer payAmount;


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

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
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

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }
}
