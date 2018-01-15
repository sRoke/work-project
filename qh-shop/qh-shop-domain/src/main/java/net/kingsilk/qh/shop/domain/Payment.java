package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.PayTypeEnum;
import net.kingsilk.qh.shop.core.PaymentStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 主动打款等
 */
@Document
public class Payment extends Base {

    private String brandAppId;

    /**
     * 系统编号
     */
    private String seq;

    /**
     * 支付状态
     */
    private PaymentStatusEnum paymentStatus;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 支付类型
     */
    private PayTypeEnum payType;

    /**
     * 原付款支付宝交易号
     */
    private String tradeNo;

    /**
     * 订单总金额
     */
    private Integer totalFee;

    /**
     * 支付原因
     */
    private String reason;

    /**
     * 审核员工
     */
    private String operatorId;

    /**
     * 支付时间
     */
    private Date payTime;

    /**
     * 门店
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

    public PaymentStatusEnum getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public PayTypeEnum getPayType() {
        return payType;
    }

    public void setPayType(PayTypeEnum payType) {
        this.payType = payType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
