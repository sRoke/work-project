package net.kingsilk.qh.agency.api.brandApp.withdrawCashManage.dto;

import net.kingsilk.qh.agency.api.UniPageReq;

import java.util.Date;

/**
 * 提现列表页请求
 */
public class WithdrawCashPageReq extends UniPageReq{

    /**
     * 用户
     */
    private String partnerStaffId;

    /**
     * 状态
     */
    private String status;

    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    public String getPartnerStaffId() {
        return partnerStaffId;
    }

    public void setPartnerStaffId(String partnerStaffId) {
        this.partnerStaffId = partnerStaffId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
