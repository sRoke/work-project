package net.kingsilk.qh.agency.api.brandApp.refund.dto;

import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.brandApp.order.dto.OrderItemInfo;
import net.kingsilk.qh.agency.core.RefundStatusEnum;
import net.kingsilk.qh.agency.core.RefundTypeEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lit on 17/8/8.
 */
public class RefundPageInfo {

    @ApiParam(value = "id")
    /**
     * id
     */
    private String id;
    /**
     * sku信息
     */
    private SkuInfo skuInfo;

    private Integer num;
    private List<OrderItemInfo> orderItemInfos = new ArrayList<>();
    /**
     * 退款类型
     */
    private RefundTypeEnum type;
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
     * 申请退款人真实姓名
     */
    private String realName;
    /**
     * 申请退款人手机号
     */
    private String phone;
    /**
     * 退款订单编号
     */
    private String orderSeq;
    /**
     * 订单总金额
     */
    private Integer orderAmount;
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
     * 物流地址信息
     */
    private LogisticsInfo logisticsInfo;
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
     * 退款编号
     */
    private String refundSeq;

    /**
     * 申请退款渠道商负责人联系方式
     */
    private String buyerPartnerPhone;

    /**
     * 申请退款渠道商负责人真实姓名
     */
    private String buyerPartnerName;

    /**
     * 退货总数量
     */
    private Integer total;

    public static class SkuInfo {


        private String id;
        private String title;
        private String skuTitle;
        private Integer skuPrice;
        private String skuImage;
        private List<RefundInfoResp.SpecInfo> specInfos = new ArrayList<>();

        public Integer getSkuPrice() {
            return skuPrice;
        }

        public void setSkuPrice(Integer skuPrice) {
            this.skuPrice = skuPrice;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSkuTitle() {
            return skuTitle;
        }

        public void setSkuTitle(String skuTitle) {
            this.skuTitle = skuTitle;
        }

        public String getSkuImage() {
            return skuImage;
        }

        public void setSkuImage(String skuImage) {
            this.skuImage = skuImage;
        }

        public List<RefundInfoResp.SpecInfo> getSpecInfos() {
            return specInfos;
        }

        public void setSpecInfos(List<RefundInfoResp.SpecInfo> specInfos) {
            this.specInfos = specInfos;
        }
    }

    public static class LogisticsInfo {


        private String company;
        private String expressNo;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getExpressNo() {
            return expressNo;
        }

        public void setExpressNo(String expressNo) {
            this.expressNo = expressNo;
        }
    }

    public String getTypeDesp() {
        return typeDesp;
    }

    public void setTypeDesp(String typeDesp) {
        this.typeDesp = typeDesp;
    }

    public String getBuyerPartnerPhone() {
        return buyerPartnerPhone;
    }

    public void setBuyerPartnerPhone(String buyerPartnerPhone) {
        this.buyerPartnerPhone = buyerPartnerPhone;
    }

    public String getBuyerPartnerName() {
        return buyerPartnerName;
    }

    public void setBuyerPartnerName(String buyerPartnerName) {
        this.buyerPartnerName = buyerPartnerName;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SkuInfo getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(SkuInfo skuInfo) {
        this.skuInfo = skuInfo;
    }

    public List<OrderItemInfo> getOrderItemInfos() {
        return orderItemInfos;
    }

    public void setOrderItemInfos(List<OrderItemInfo> orderItemInfos) {
        this.orderItemInfos = orderItemInfos;
    }

    public LogisticsInfo getLogisticsInfo() {
        return logisticsInfo;
    }

    public void setLogisticsInfo(LogisticsInfo logisticsInfo) {
        this.logisticsInfo = logisticsInfo;
    }

    public RefundTypeEnum getType() {
        return type;
    }

    public void setType(RefundTypeEnum type) {
        this.type = type;
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


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getRefundSeq() {
        return refundSeq;
    }

    public void setRefundSeq(String refundSeq) {
        this.refundSeq = refundSeq;
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

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
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

    public Integer getWxAmount() {
        return wxAmount;
    }

    public void setWxAmount(Integer wxAmount) {
        this.wxAmount = wxAmount;
    }

    public Integer getAliAmount() {

        return aliAmount;
    }

    public void setAliAmount(Integer aliAmount) {
        this.aliAmount = aliAmount;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
