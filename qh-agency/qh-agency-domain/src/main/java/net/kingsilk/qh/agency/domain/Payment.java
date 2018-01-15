package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.PayTypeEnum;
import net.kingsilk.qh.agency.core.PaymentStatusEnum;

import java.util.Date;

/**
 * Created by lit on 17/8/23.
 */
public class Payment extends Base{

    private String seq;

    /** 支付状态 */
    private PaymentStatusEnum status;

    /**
     * 提现的用户
     */
    private String userId;

    /** 申请时间  */
    private Date applyTime;

    /** 支付方式 */
    private PayTypeEnum payType;

    /** 支付金额（单位：分）*/
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

    /** 审核员 */
    private Staff operator;

    // ---------------------------------------------- 支付后填写

    /** 支付时间 (是否已经支付通过该字段是否为null判断) */
    private Date refundTime;

    /** 待退回到第三方支付金额 */
    private Integer refundPay = 0;

//    /** 待退回到可提现金额支付 */
//    private Integer refundCanWithdraw = 0;
//
//    /** 待退回到不可提现金额支付 */
//    private Integer refundNotWithdraw = 0;

    // ---------------------------------------------- 关联信息


    /** 订单ID */
    private String orderId;


    /** 退货退款申请 */
    private String refundId;

    /** 提现的申请 */
    private String withdrawCashId;

    /** 备注 */
    private String memo;

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

    public Staff getOperator() {
        return operator;
    }

    public void setOperator(Staff operator) {
        this.operator = operator;
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
    }

    public Integer getRefundPay() {
        return refundPay;
    }

    public void setRefundPay(Integer refundPay) {
        this.refundPay = refundPay;
    }

//    public Integer getRefundCanWithdraw() {
//        return refundCanWithdraw;
//    }
//
//    public void setRefundCanWithdraw(Integer refundCanWithdraw) {
//        this.refundCanWithdraw = refundCanWithdraw;
//    }
//
//    public Integer getRefundNotWithdraw() {
//        return refundNotWithdraw;
//    }
//
//    public void setRefundNotWithdraw(Integer refundNotWithdraw) {
//        this.refundNotWithdraw = refundNotWithdraw;
//    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getWithdrawCashId() {
        return withdrawCashId;
    }

    public void setWithdrawCashId(String withdrawCashId) {
        this.withdrawCashId = withdrawCashId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
