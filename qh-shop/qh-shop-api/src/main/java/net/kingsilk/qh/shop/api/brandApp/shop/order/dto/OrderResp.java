package net.kingsilk.qh.shop.api.brandApp.shop.order.dto;

import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.core.OrderStatusEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderResp {


    @ApiParam(value = "店铺id")
    private String shopId;

    @ApiParam(value = "id")
    private String id;
    @ApiParam(value = "订单号")
    private String seq;
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
    private String statusDesp;
    @ApiParam(value = "售后")
    private List<RefundInfo> refundList = new ArrayList<>();
    @ApiParam(value = "创建日期")
    private Date dateCreated;

    /**
     * 该订单总商品数量
     */
    private int total;
    /**
     * 下单的商品
     */
    @ApiParam(value = "下单的商品信息")
    private List<OrderItemInfo> orderItems = new ArrayList<>();


    public static class RefundInfo {


        private String id;
        private String skuId;
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSkuId() {
            return skuId;
        }

        public void setSkuId(String skuId) {
            this.skuId = skuId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
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

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
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

    public List<RefundInfo> getRefundList() {
        return refundList;
    }

    public void setRefundList(List<RefundInfo> refundList) {
        this.refundList = refundList;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OrderItemInfo> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemInfo> orderItems) {
        this.orderItems = orderItems;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
