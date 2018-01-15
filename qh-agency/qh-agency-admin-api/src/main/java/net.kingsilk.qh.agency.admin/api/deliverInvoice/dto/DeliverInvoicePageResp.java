package net.kingsilk.qh.agency.admin.api.deliverInvoice.dto;

import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.order.dto.OrderInfoResp;
import net.kingsilk.qh.agency.admin.api.order.dto.OrderPageResp;
import net.kingsilk.qh.agency.core.DeliverStatusEnum;
import net.kingsilk.qh.agency.core.OrderStatusEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lit on 17/7/18.
 */
public class DeliverInvoicePageResp {

    @ApiParam(value = "id")
    private String id;
    @ApiParam(value = "发货单编号")
    private String seq;

    @ApiParam(value = "订单编号")
    private String orderSeq;

    private OrderInfoResp.AddressInfo addressInfo;
    /**
     * 下单的用户，即代理商
     */
    @ApiParam(value = "realName")
    private String realName;
    @ApiParam(value = "phone")
    private String phone;
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
    private List<OrderInfoResp.OrderItemInfo> orderItems = new ArrayList<>();


    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public OrderInfoResp.AddressInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(OrderInfoResp.AddressInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

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

    public List<OrderInfoResp.OrderItemInfo> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderInfoResp.OrderItemInfo> orderItems) {
        this.orderItems = orderItems;
    }
}
