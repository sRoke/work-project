package net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.addr.dto.AddrModel;
import net.kingsilk.qh.shop.api.brandApp.shop.order.dto.LogisticInfo;
import net.kingsilk.qh.shop.core.OrderSourceTypeEnum;
import net.kingsilk.qh.shop.core.OrderStatusEnum;

import java.text.SimpleDateFormat;
import java.util.*;

@ApiModel(value = "OrderInfoResp")
public class OrderInfoResp {

    @ApiParam(value = "id")
    private String id;

    @ApiParam(value = "订单号")
    private String seq;

    @ApiParam(value = "支付方式")
    private String payType;

    @ApiParam(value = "支付方式")
    private String payTypeDesp;

    @ApiParam(value = "支付时间")
    private String payTime;

    /**
     * 下单的用户，会员
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
     * 客服调整的金额。
     * <p>
     * - 正值 : 让购买者多支付
     * - 负值 : 让购买者少支付
     */
    private Integer adjustPrice = 0;

//    /**
//     * 余额抵扣金额
//     */
//    private Integer balancePrice;

//    /**
//     * 货款抵扣金额
//     */
//    private Integer noCashBalancePrice;

    /**
     * 订单当前状态
     */
    @ApiParam(value = "订单当前状态")
    private OrderStatusEnum status;
    @ApiParam(value = "状态描述")
    private String statusDesp;

    /**
     * 收货地址
     */
    @ApiParam(value = "地址")
    private AddrModel address;

    @ApiParam(value = "创建时间")
    private Date dateCreated;


    @ApiParam(value = "渠道商类型")
    private String partnerType;

    @ApiParam(value = "总商品数")
    private Integer total;


    @ApiParam(value = "订单来源类型／配送方式")
    private OrderSourceTypeEnum orderSourceType;
    private String orderSourceTypeDesp;

    @ApiParam(value = "到店自取时间")
    private String sinceTakeTime;

    @ApiParam(value = "运费")
    private Integer freight;

    /**
     * 下单的商品
     */
    @ApiParam(value = "下单的商品信息")
    private List<OrderItemInfo> orderItems = new ArrayList<OrderItemInfo>();

    @ApiParam(value = "物流")
    private Set<LogisticInfo> logisticses = new LinkedHashSet<LogisticInfo>();
//    @ApiParam(value = "物流公司列表")
//    private Map<String, String> logisticsCompanys = new HashMap<String, String>();

//    @ApiParam(value = "卖家备注")
//    private String memo;

    @ApiParam(value = "买家备注")
    private String buyerMemo;

    @ApiParam(value = "当前系统时间")
    private String curSysDate;


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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
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

    public AddrModel getAddress() {
        return address;
    }

    public void setAddress(AddrModel address) {
        this.address = address;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<OrderItemInfo> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemInfo> orderItems) {
        this.orderItems = orderItems;
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo;
    }

    public String getStatusDesp() {
        return statusDesp;
    }

    public void setStatusDesp(String statusDesp) {
        this.statusDesp = statusDesp;
    }

    public String getCurSysDate() {
        Date curDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(curDate);
    }
//    public void setCurSysDate(String curSysDate) {
//        this.curSysDate = curSysDate;
//    }

    public Integer getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(Integer adjustPrice) {
        this.adjustPrice = adjustPrice;
    }

    public String getPayTypeDesp() {
        return payTypeDesp;
    }

    public void setPayTypeDesp(String payTypeDesp) {
        this.payTypeDesp = payTypeDesp;
    }

    public Set<LogisticInfo> getLogisticses() {
        return logisticses;
    }

    public void setLogisticses(Set<LogisticInfo> logisticses) {
        this.logisticses = logisticses;
    }

    public OrderSourceTypeEnum getOrderSourceType() {
        return orderSourceType;
    }

    public void setOrderSourceType(OrderSourceTypeEnum orderSourceType) {
        this.orderSourceType = orderSourceType;
    }

    public String getOrderSourceTypeDesp() {
        return orderSourceTypeDesp;
    }

    public void setOrderSourceTypeDesp(String orderSourceTypeDesp) {
        this.orderSourceTypeDesp = orderSourceTypeDesp;
    }

    public String getSinceTakeTime() {
        return sinceTakeTime;
    }

    public void setSinceTakeTime(String sinceTakeTime) {
        this.sinceTakeTime = sinceTakeTime;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }
}