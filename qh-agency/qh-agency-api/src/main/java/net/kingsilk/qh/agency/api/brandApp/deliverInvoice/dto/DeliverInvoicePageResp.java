package net.kingsilk.qh.agency.api.brandApp.deliverInvoice.dto;

import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddressInfo;
import net.kingsilk.qh.agency.api.brandApp.order.dto.LogisticInfo;
import net.kingsilk.qh.agency.api.brandApp.order.dto.OrderItemInfo;
import net.kingsilk.qh.agency.core.DeliverStatusEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
public class DeliverInvoicePageResp {

    @ApiParam(value = "id")
    private String id;
    @ApiParam(value = "发货单编号")
    private String seq;

    @ApiParam(value = "订单编号")
    private String orderSeq;

    private AddressInfo addressInfo;
    /**
     * 下单的用户，即代理商
     */
    @ApiParam(value = "realName")
    private String realName;
    @ApiParam(value = "phone")
    private String phone;

    /**
     * 实际支付价格
     */
    @ApiParam(value = "paymentPrice")
    private Integer paymentPrice;

    /**
     * 商品个数
     */
    @ApiParam(value = "total")
    private Integer total;
    /**
     * 订单当前状态
     */
    @ApiParam(value = "订单当前状态")
    private DeliverStatusEnum status;
    private String statusDesp;

    @ApiParam(value = "创建日期")
    private Date dateCreated;
    /**
     * 下单的商品
     */
    @ApiParam(value = "下单的商品信息")
    private List<OrderItemInfo> orderItems = new ArrayList<>();

    @ApiParam(value = "物流信息")
    private List<LogisticInfo> logisticInfo;

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

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DeliverStatusEnum getStatus() {
        return status;
    }

    public void setStatus(DeliverStatusEnum status) {
        this.status = status;
    }

    public String getStatusDesp() {
        return statusDesp;
    }

    public void setStatusDesp(String statusDesp) {
        this.statusDesp = statusDesp;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<OrderItemInfo> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemInfo> orderItems) {
        this.orderItems = orderItems;
    }

    public Integer getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(Integer paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<LogisticInfo> getLogisticInfo() {
        return logisticInfo;
    }

    public void setLogisticInfo(List<LogisticInfo> logisticInfo) {
        this.logisticInfo = logisticInfo;
    }
}
