package net.kingsilk.qh.agency.api.brandApp.withdrawCashManage.dto;

import java.util.Date;

/**
 * 提现记录列表页返回
 */
public class WithdrawCashPageResp {

    /**
     * id
     */
    private String id;

    /**
     * 提现编号
     */
    private String seq;

    /**
     * 提现申请员工手机号
     */
    private String partnerStaffPhone;

    /**
     * 提现申请员工name
     */
    private String partnerStaffName;

    /**
     * 提现金额
     */
    private Integer withdrawAmount ;

    /**
     * 提现渠道商ID
     */
    private String partnerId;

    /**
     * 钱包余额
     */
    private Integer available;

    /**
     * 申请时间
     */
    private Date applyTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 状态描述
     */
    private String statusDesp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getPartnerStaffPhone() {
        return partnerStaffPhone;
    }

    public void setPartnerStaffPhone(String partnerStaffPhone) {
        this.partnerStaffPhone = partnerStaffPhone;
    }

    public String getPartnerStaffName() {
        return partnerStaffName;
    }

    public void setPartnerStaffName(String partnerStaffName) {
        this.partnerStaffName = partnerStaffName;
    }

    public Integer getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(Integer withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesp() {
        return statusDesp;
    }

    public void setStatusDesp(String statusDesp) {
        this.statusDesp = statusDesp;
    }
}
