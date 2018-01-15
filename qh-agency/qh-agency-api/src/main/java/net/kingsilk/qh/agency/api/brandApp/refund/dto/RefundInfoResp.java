package net.kingsilk.qh.agency.api.brandApp.refund.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddressInfo;
import net.kingsilk.qh.agency.api.brandApp.order.dto.OrderItemInfo;
import net.kingsilk.qh.agency.core.OrderOperateEnum;
import net.kingsilk.qh.agency.core.OrderStatusEnum;
import net.kingsilk.qh.agency.core.RefundStatusEnum;
import net.kingsilk.qh.agency.core.RefundTypeEnum;

import java.util.*;

/**
 * Created by yanfq
 */
@ApiModel(value = "RefundInfoResp")
public class RefundInfoResp {

    @ApiParam(value = "id")
    private String id;
    /**
     * 退款类型
     */
    private RefundTypeEnum type;

    private String refundSeq;
    /**
     * 退款类型描述
     */
    private String typeDesp;
    /**
     * 退款状态
     */
    private RefundStatusEnum status;
    /**
     * 退款状态描述
     */
    private String statusDesp;
    /**
     * 用户申请的退款金额
     */
    private Integer applyNum;
    /**
     * 需退款的最终金额(单价：分)
     */
    private Integer refundAmount;
    /**
     * 需退款到支付宝的最终金额(单价：分)
     */
    private Integer aliAmount = 0;

    /**
     * 需退款到微信的最终金额(单价：分)
     */
    private Integer wxAmount = 0;
    /**
     * 需退款到支付宝的最终金额(单价：分)
     */
    private Integer balanceAmount = 0;

    /**
     * 需退款到微信的最终金额(单价：分)
     */
    private Integer noBalanceAmount = 0;
    /**
     * 退款原因
     */
    private String reason;
    /**
     * 拒绝退款原因
     */
    private String rejectReason;
    /**
     * 用户发货时间
     */
    private Date deliveryTime;
    /**
     * 后台确认收货时间
     */
    private Date receiveTime;
    /**
     * 退款订单创建时间
     */
    private Date dateCreated;
    /**
     * 备注
     */
    private String memo;
    /**
     * 买家
     */
    private String realName;
    /**
     * 退款者手机号
     */
    private String phone;
    /**
     * 退款订单的id
     */
    private String orderId;
    /**
     * 成交时间
     */
    private Date orderDate;
    /**
     * sku主图
     */
    private String skuImg;

    private RefundPageInfo.LogisticsInfo logisticsInfo;

    private List<OrderItemInfo> orderItemInfos = new ArrayList<>();

    /**
     * sku的规格信息
     */
    private List<SpecInfo> specInfos = new ArrayList<SpecInfo>();
    /**
     * 退款商品的标题
     */
    private String itemTitle;

    /**
     * 退货商品总数
     */
    private Integer total;
    /**
     * 订单日志信息
     */
    private Map<String, OrderLogInfo> orderLogInfoMap = new HashMap<String, OrderLogInfo>();
    /**
     * 物流信息
     */
    private Map<String, String> logisticsMap = new HashMap<String, String>();

    @ApiModelProperty(value = "用户收货地址")
    private AddressInfo address;


    public static class SpecInfo {

        private String propName;
        private String propValue;

        public String getPropName() {
            return propName;
        }

        public void setPropName(String propName) {
            this.propName = propName;
        }

        public String getPropValue() {
            return propValue;
        }

        public void setPropValue(String propValue) {
            this.propValue = propValue;
        }
    }

    public static class OrderLogInfo {

        private OrderStatusEnum orderStatus;
        private OrderOperateEnum operate;
        private Integer price = 0;
        private Date dateCreated;

        public OrderStatusEnum getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(OrderStatusEnum orderStatus) {
            this.orderStatus = orderStatus;
        }

        public OrderOperateEnum getOperate() {
            return operate;
        }

        public void setOperate(OrderOperateEnum operate) {
            this.operate = operate;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Date getDateCreated() {
            return dateCreated;
        }

        public void setDateCreated(Date dateCreated) {
            this.dateCreated = dateCreated;
        }
    }

    public RefundPageInfo.LogisticsInfo getLogisticsInfo() {
        return logisticsInfo;
    }

    public void setLogisticsInfo(RefundPageInfo.LogisticsInfo logisticsInfo) {
        this.logisticsInfo = logisticsInfo;
    }

    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
        this.address = address;
    }

    public String getRefundSeq() {
        return refundSeq;
    }

    public void setRefundSeq(String refundSeq) {
        this.refundSeq = refundSeq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RefundTypeEnum getType() {
        return type;
    }

    public void setType(RefundTypeEnum type) {
        this.type = type;
    }

    public String getTypeDesp() {
        return typeDesp;
    }

    public void setTypeDesp(String typeDesp) {
        this.typeDesp = typeDesp;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public RefundStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RefundStatusEnum status) {
        this.status = status;
    }

    public String getStatusDesp() {
        return statusDesp;
    }

    public void setStatusDesp(String statusDesp) {
        this.statusDesp = statusDesp;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public Integer getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Integer refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg;
    }

    public List<SpecInfo> getSpecInfos() {
        return specInfos;
    }

    public void setSpecInfos(List<SpecInfo> specInfos) {
        this.specInfos = specInfos;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public Map<String, OrderLogInfo> getOrderLogInfoMap() {
        return orderLogInfoMap;
    }

    public void setOrderLogInfoMap(Map<String, OrderLogInfo> orderLogInfoMap) {
        this.orderLogInfoMap = orderLogInfoMap;
    }

    public Integer getAliAmount() {
        return aliAmount;
    }

    public void setAliAmount(Integer aliAmount) {
        this.aliAmount = aliAmount;
    }

    public Integer getWxAmount() {
        return wxAmount;
    }

    public void setWxAmount(Integer wxAmount) {
        this.wxAmount = wxAmount;
    }

    public Integer getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Integer balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Integer getNoBalanceAmount() {
        return noBalanceAmount;
    }

    public void setNoBalanceAmount(Integer noBalanceAmount) {
        this.noBalanceAmount = noBalanceAmount;
    }

    public Map<String, String> getLogisticsMap() {
        return logisticsMap;
    }

    public void setLogisticsMap(Map<String, String> logisticsMap) {
        this.logisticsMap = logisticsMap;
    }

    public List<OrderItemInfo> getOrderItemInfos() {
        return orderItemInfos;
    }

    public void setOrderItemInfos(List<OrderItemInfo> orderItemInfos) {
        this.orderItemInfos = orderItemInfos;
    }
}
