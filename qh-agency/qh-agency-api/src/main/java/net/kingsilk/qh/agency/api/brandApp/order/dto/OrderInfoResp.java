package net.kingsilk.qh.agency.api.brandApp.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddressInfo;
import net.kingsilk.qh.agency.core.OrderStatusEnum;

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
     * 客服调整的金额。
     * <p>
     * - 正值 : 让购买者多支付
     * - 负值 : 让购买者少支付
     */
    private Integer adjustPrice = 0;

    /**
     * 余额抵扣金额
     */
    private Integer balancePrice;

    /**
     * 货款抵扣金额
     */
    private Integer noCashBalancePrice;

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
    private AddressInfo address;

    @ApiParam(value = "创建时间")
    private Date dateCreated;


    @ApiParam(value = "渠道商类型")
    private String partnerType;

    @ApiParam(value = "总商品数")
    private Integer total;

    /**
     * 下单的商品
     */
    @ApiParam(value = "下单的商品信息")
    private List<OrderItemInfo> orderItems = new ArrayList<OrderItemInfo>();

    @ApiParam(value = "物流")
    private Set<LogisticInfo> logisticses = new LinkedHashSet<LogisticInfo>();
    @ApiParam(value = "物流公司列表")
    private Map<String, String> logisticsCompanys = new HashMap<String, String>();

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

    public Integer getBalancePrice() {
        return balancePrice;
    }

    public void setBalancePrice(Integer balancePrice) {
        this.balancePrice = balancePrice;
    }

    public Integer getNoCashBalancePrice() {
        return noCashBalancePrice;
    }

    public void setNoCashBalancePrice(Integer noCashBalancePrice) {
        this.noCashBalancePrice = noCashBalancePrice;
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

    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
        this.address = address;
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

    public Set<LogisticInfo> getLogisticses() {
        return logisticses;
    }

    public void setLogisticses(Set<LogisticInfo> logisticses) {
        this.logisticses = logisticses;
    }

    public Map<String, String> getLogisticsCompanys() {
        return logisticsCompanys;
    }

    public void setLogisticsCompanys(Map<String, String> logisticsCompanys) {
        this.logisticsCompanys = logisticsCompanys;
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

    public Integer getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(Integer adjustPrice) {
        this.adjustPrice = adjustPrice;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
