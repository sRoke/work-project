package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.OperatorTypeEnum;

/**
 * 退款记录表
 */
public class RefundLog extends Base{

    /** 操作员工ID */
    private String staffId;

    /** 操作渠道商员工ID */
    private String partnerStaffId;

    /** 退款ID */
    private String refundId;

    /** 退款的金额（单位：分） */
    private Integer refundMoney = 0;

    /** 修改的差额（单位：分） */
    private Integer adjustMoney = 0;

    /** 操作类型 */
    private OperatorTypeEnum type;
    public RefundLog() {
    }
    public RefundLog(String staffId, String partnerStaffId, String refundId, Integer refundMoney, Integer adjustMoney, OperatorTypeEnum type) {
        this.staffId = staffId;
        this.partnerStaffId = partnerStaffId;
        this.refundId = refundId;
        this.refundMoney = refundMoney;
        this.adjustMoney = adjustMoney;
        this.type = type;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getPartnerStaffId() {
        return partnerStaffId;
    }

    public void setPartnerStaffId(String partnerStaffId) {
        this.partnerStaffId = partnerStaffId;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public Integer getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Integer refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Integer getAdjustMoney() {
        return adjustMoney;
    }

    public void setAdjustMoney(Integer adjustMoney) {
        this.adjustMoney = adjustMoney;
    }

    public OperatorTypeEnum getType() {
        return type;
    }

    public void setType(OperatorTypeEnum type) {
        this.type = type;
    }
}
