package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.DeliverStatusEnum;

import java.util.Date;

/**
 * 发货单操作记录
 */
public class DeliverInvoiceLog extends Base{
    /**
     * 关联的发货单
     */
    private String deliverInvoiceId;

    /**
     * 发货单关联的订单
     */
    private String orderId;
    /**
     * 发货的品牌商员工
     */
    private String deliverStaffId;

    /**
     * 发货单状态
     */
    private DeliverStatusEnum deliverStatusEnum;
    /**
     * 发货的渠道商员工
     */
    private String partnerStaffId;

    /**
     * 操作时间
     */
    private Date deliverTime;

    /**
     * 发货单的物流详情
     */
    private String logisticsId;

    public DeliverInvoiceLog(){

    }
    public DeliverInvoiceLog(String deliverInvoiceId, String orderId,
                             DeliverStatusEnum deliverStatusEnum,String deliverStaffId, String partnerStaffId, Date deliverTime, String logisticsId) {
        this.deliverInvoiceId = deliverInvoiceId;
        this.orderId = orderId;
        this.deliverStatusEnum=deliverStatusEnum;
        this.deliverStaffId = deliverStaffId;
        this.partnerStaffId = partnerStaffId;
        this.deliverTime = deliverTime;
        this.logisticsId = logisticsId;
    }

    public DeliverStatusEnum getDeliverStatusEnum() {
        return deliverStatusEnum;
    }

    public void setDeliverStatusEnum(DeliverStatusEnum deliverStatusEnum) {
        this.deliverStatusEnum = deliverStatusEnum;
    }

    public String getDeliverInvoiceId() {
        return deliverInvoiceId;
    }

    public void setDeliverInvoiceId(String deliverInvoiceId) {
        this.deliverInvoiceId = deliverInvoiceId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDeliverStaffId() {
        return deliverStaffId;
    }

    public void setDeliverStaffId(String deliverStaffId) {
        this.deliverStaffId = deliverStaffId;
    }

    public String getPartnerStaffId() {
        return partnerStaffId;
    }

    public void setPartnerStaffId(String partnerStaffId) {
        this.partnerStaffId = partnerStaffId;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }
}
