package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.PayTypeEnum;
import net.kingsilk.qh.shop.core.WithdrawInitTypeEnum;
import net.kingsilk.qh.shop.core.WithdrawStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 提现表
 */
@Document
public class Withdraw extends Base {

    private String brandAppId;

    /**
     * 提现发起方式
     */
    private WithdrawInitTypeEnum initType;

    /**
     * 提现状态
     */
    private WithdrawStatusEnum status;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 支付金额
     */
    private Integer refundFee;

    /**
     * 支付类型
     */
    private PayTypeEnum payType;

    /**
     * 支付记录
     */
    private String paymentId;

    /**
     * 支付记录
     */
    private String memo;

    /**
     * 系统编号
     */
    private String seq;

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

    public WithdrawInitTypeEnum getInitType() {
        return initType;
    }

    public void setInitType(WithdrawInitTypeEnum initType) {
        this.initType = initType;
    }

    public WithdrawStatusEnum getStatus() {
        return status;
    }

    public void setStatus(WithdrawStatusEnum status) {
        this.status = status;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Integer refundFee) {
        this.refundFee = refundFee;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public PayTypeEnum getPayType() {
        return payType;
    }

    public void setPayType(PayTypeEnum payType) {
        this.payType = payType;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
