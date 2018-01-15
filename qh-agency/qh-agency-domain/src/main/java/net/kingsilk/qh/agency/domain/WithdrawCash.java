package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.WithdrawCashStatusEnum;

/**
 * 提现
 */

public class WithdrawCash extends Base {

    /** 品牌商ID */
    private String brandAppId;

    /** 渠道商ID */
    private String partnerId;

    /**
     * 编号
     */
    private String seq;

    /** 提现申请员工 */
    private String partnerStaffId;

    /** 提现金额(分)  */
    private Integer withdrawAmount = 0;

    /** 提现状态 */
    private WithdrawCashStatusEnum status = WithdrawCashStatusEnum.CASHING;

    /** 支付记录 */
    private String paymentId;

    /** 提现后余额(分) */
    private Integer available = 0;

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

    public String getPartnerStaffId() {
        return partnerStaffId;
    }

    public void setPartnerStaffId(String partnerStaffId) {
        this.partnerStaffId = partnerStaffId;
    }

    public Integer getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(Integer withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public WithdrawCashStatusEnum getStatus() {
        return status;
    }

    public void setStatus(WithdrawCashStatusEnum status) {
        this.status = status;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}
