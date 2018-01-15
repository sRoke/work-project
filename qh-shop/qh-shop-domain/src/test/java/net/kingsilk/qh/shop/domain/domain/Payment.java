package net.kingsilk.qh.shop.domain.bak;

import net.kingsilk.qh.shop.core.PayTypeEnum;
import net.kingsilk.qh.shop.core.PaymentStatusEnum;
import net.kingsilk.qh.shop.domain.Base;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *
 */
@Document
public class Payment extends Base
{

    /**
     * 所属品牌商ID。
     */
    private String brandAppId;

    /**
     * 当前渠道商ID
     */
    private String partnerId;

    /**
     * 编号
     */
    private String seq;

    /** 支付状态 */
    private PaymentStatusEnum status;

    /** 申请时间  */
    private Date applyTime;

    /** 支付方式 */
    private PayTypeEnum payType;

    /** 支付金额（单位：分），需退回到除礼品卡外的支付渠道的金额 */
    private Integer refundFee = 0;

    /**
     * 财务人员手动调整的金额（单位：分），可以为负数。
     *
     * 但是如果为正数时，不可超过 refundMoney。
     */
    private Integer adjustAmount = 0;

    /** 原付款支付宝交易号 */
    private String tradeNo;

    /** 商户订单号 */
    private String outTradeNo;

    /** 订单总金额，用户对该交易实际支付的金额（包含礼品卡） */
    private Integer totalFee = 0;


    /** 支付原因 */
    private String reason;

    /** 审核员ID */
    private String operatorId;

    // ---------------------------------------------- 支付后填写

    /** 支付时间 (是否已经支付通过该字段是否为null判断) */
    private Date refundTime;

    /** 支付宝支付相关信息 */
    private String alipayLogId;

    /** 微信支付相关信息 */
    private String weiXinPayLogId;

    /** 待退回到第三方支付金额 */
    private Integer refundPay = 0;

    /** 待退回到可提现金额支付 */
    private Integer refundCanWithdraw = 0;

    /** 待退回到不可提现金额支付 */
    private Integer refundNotWithdraw = 0;

    // ---------------------------------------------- 关联信息

    /** 买家 */
    private String userId;

    /** 订单 */
    @DBRef
    private Order order;

    /** 备注 */
    private String memo;

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

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public PaymentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusEnum status) {
        this.status = status;
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

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public Integer getAdjustAmount() {
        return adjustAmount;
    }

    public void setAdjustAmount(Integer adjustAmount) {
        this.adjustAmount = adjustAmount;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
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

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public String getAlipayLogId() {
        return alipayLogId;
    }

    public void setAlipayLogId(String alipayLogId) {
        this.alipayLogId = alipayLogId;
    }

    public String getWeiXinPayLogId() {
        return weiXinPayLogId;
    }

    public void setWeiXinPayLogId(String weiXinPayLogId) {
        this.weiXinPayLogId = weiXinPayLogId;
    }

    public Integer getRefundPay() {
        return refundPay;
    }

    public void setRefundPay(Integer refundPay) {
        this.refundPay = refundPay;
    }

    public Integer getRefundCanWithdraw() {
        return refundCanWithdraw;
    }

    public void setRefundCanWithdraw(Integer refundCanWithdraw) {
        this.refundCanWithdraw = refundCanWithdraw;
    }

    public Integer getRefundNotWithdraw() {
        return refundNotWithdraw;
    }

    public void setRefundNotWithdraw(Integer refundNotWithdraw) {
        this.refundNotWithdraw = refundNotWithdraw;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
