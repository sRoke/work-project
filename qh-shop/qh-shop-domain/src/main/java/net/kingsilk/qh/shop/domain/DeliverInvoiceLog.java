package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.DeliverStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 发货单日志表
 */
@Document
public class DeliverInvoiceLog extends Base {

    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;

    /**
     * 关联的order的id
     */
    private String orderId;

    /**
     * 关联的发货单Id
     */
    private String deliverInvoiceId;

    /**
     * 操作员工ID
     */
    private String deliverStaffId;

    /**
     * 发货单状态
     */
    private DeliverStatusEnum deliverStatusEnum;

    /**
     * 发货时间
     */
    private Date deliverTime;

    /**
     * 关联的物流信息
     */
    private String logisticsId;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getDeliverInvoiceId() {
        return deliverInvoiceId;
    }

    public void setDeliverInvoiceId(String deliverInvoiceId) {
        this.deliverInvoiceId = deliverInvoiceId;
    }

    public String getDeliverStaffId() {
        return deliverStaffId;
    }

    public void setDeliverStaffId(String deliverStaffId) {
        this.deliverStaffId = deliverStaffId;
    }

    public DeliverStatusEnum getDeliverStatusEnum() {
        return deliverStatusEnum;
    }

    public void setDeliverStatusEnum(DeliverStatusEnum deliverStatusEnum) {
        this.deliverStatusEnum = deliverStatusEnum;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
