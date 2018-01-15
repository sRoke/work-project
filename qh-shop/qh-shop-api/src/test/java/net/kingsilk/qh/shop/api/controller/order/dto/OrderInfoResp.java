package net.kingsilk.qh.shop.api.controller.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.core.OrderStatusEnum;

import java.util.*;

/**
 *
 */
@ApiModel(value = "OrderInfoResp")
public class OrderInfoResp {

    @ApiParam(value = "id")
    private String id;

    @ApiParam(value = "订单号")
    private String seq;

    @ApiParam(value = "支付方式")
    private String payType;

    @ApiParam(value = "支付方式")
    private Date payTime;

    /**
     * 下单的用户，即代理商
     */
    @ApiParam(value = "realName")
    private String realName;

    @ApiParam(value = "phone")
    private String phone;

    /**
     * 订单价，原价，不包含改价
     */
    @ApiParam(value = "订单价")
    private Integer orderPrice;

    @ApiParam(value = "是否存在退款请求")
    private Boolean haveRefund = false;

    /**
     * 实际支付的金额
     */
    @ApiParam(value = "实际支付金额")
    private Integer paymentPrice;

    /**
     * 订单当前状态
     */
    @ApiParam(value = "订单当前状态")
    private OrderStatusEnum status;
    @ApiParam(value = "状态描述")
    private String statusDesp;

    @ApiParam(value = "创建时间")
    private Date dateCreated;

    @ApiParam(value = "渠道商类型")
    private String partnerType;

    /**
     * 下单的商品
     */
    @ApiParam(value = "下单的商品信息")
    private List<OrderItemInfo> orderItems = new ArrayList<OrderItemInfo>();

    @ApiParam(value = "卖家备注")
    private String memo;

    @ApiParam(value = "买家备注")
    private String buyerMemo;


    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Boolean getHaveRefund() {
        return haveRefund;
    }

    public void setHaveRefund(Boolean haveRefund) {
        this.haveRefund = haveRefund;
    }

    public Integer getPaymentPrice() {
        return paymentPrice;
    }

    public void setPaymentPrice(Integer paymentPrice) {
        this.paymentPrice = paymentPrice;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo;
    }
}
