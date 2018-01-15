package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.RefundStatusEnum;
import net.kingsilk.qh.agency.core.RefundTypeEnum;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 售后,一条记录只能退款一次，若需多次，请生成多条记录
 *
 * ps. 退款按照不同维度可分为以下几类
 *
 * 1. 按照退款来源方式划分
 *
 *     - 以订单为粒度进行退款（取消订单、拒绝接单等用户未收货时发生的退款），退款金额按照订单的金额去计算
 *     - 以SKU为粒度进行退款（用户已确认收货，从库存中进行退款操作），退款金额按照当前该SKU的各级销售价去计算
 *
 * 2. 按照退款方式去划分
 *
 *     - 全额退款       //TODO 去掉全额退款
 *     - 协议退款
 *
 * 3. 按照退款类型划分
 *
 *     - 退款退货
 *     - 仅退款
 */
@Document
public class Refund extends Base {


    /**
     * 所属公司。
     */
    private String brandAppId;

    /**
     * 申请退款的渠道商
     */
    @DBRef
    private Partner buyerPartner;

    /**
     * 处理退款的渠道商,如果为空，则为品牌商
     */
    @DBRef
    private Partner sellerPartner;

    /**
     * 当前退货记录所对应的订单，同一个订单可能有多条
     * 当order字段非空时，RefundSku不会生成相应记录
     */
    @DBRef
    private Order order;

    /**
     * 退款编号
     */
    private String seq;

//    /**
//     * 申请售后的sku，若退单个商品，必传
//     */
//    @DBRef
//    private Sku sku;

    /**
     * 退款类型
     */
    private RefundTypeEnum refundType;

    /**
     * 售后状态
     */
    private RefundStatusEnum status = RefundStatusEnum.UNCHECKED;

    /**
     * 售后状态
     */
    private RefundStatusEnum lastStatus = RefundStatusEnum.UNCHECKED;

    /**
     * 申请的金额
     */
    private Integer applyPrice = 0;

//    /**
//     * 申请售后的数量, 若取消订单，忽略该字段
//     */
//    @Deprecated
//    private Integer applyNum = 0;

    /**
     * 客服调整的金额
     * 正值为加（多退钱），负值为减（少退钱）
     */
    private Integer adjustPrice = 0;

    /**
     * 需退款的最终金额(单价：分)
     */
    private Integer refundAmount = 0;

    /**
     * 需退款到支付宝的最终金额(单价：分)
     */
    private Integer aliAmount = 0;

    /**
     * 需退款到微信的最终金额(单价：分)
     */
    private Integer wxAmount = 0;

    /**
     * 退货截图,TODO
     */
    //private List<String> imgs = new ArrayList<String>();

    /**
     * 申请售后的原因
     */
    private String reason;

    /**
     * 拒绝售后的理由
     */
    private String rejectReason;


    //---------------------- 当 status = ??? 时，下列字段有值。
    /**
     * 用户物流
     */
    @DBRef
    private Logistics logistics;

    /**
     * 用户发货时间
     */
    private Date deliveryTime;

    /**
     * 后台确认收货时间
     */
    private Date receiveTime;

    /**
     * 备注。
     */
    private String memo;

    /**
     * 渠道商收货地址
     */
    private RefundAddress refundAddress;


    public static class RefundAddress {
        private String adc;
        private String street;
        private String receiver;
        private String phone;

        public String getAdc() {
            return adc;
        }

        public void setAdc(String adc) {
            this.adc = adc;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
    // --------------------------------------- getter && setter


    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public Partner getBuyerPartner() {
        return buyerPartner;
    }

    public void setBuyerPartner(Partner buyerPartner) {
        this.buyerPartner = buyerPartner;
    }

    public Partner getSellerPartner() {
        return sellerPartner;
    }

    public void setSellerPartner(Partner sellerPartner) {
        this.sellerPartner = sellerPartner;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public RefundTypeEnum getRefundType() {
        return refundType;
    }

    public void setRefundType(RefundTypeEnum refundType) {
        this.refundType = refundType;
    }

    public RefundStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RefundStatusEnum status) {
        this.status = status;
    }

    public Integer getApplyPrice() {
        return applyPrice;
    }

    public void setApplyPrice(Integer applyPrice) {
        this.applyPrice = applyPrice;
    }

    public Integer getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(Integer adjustPrice) {
        this.adjustPrice = adjustPrice;
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

    public Logistics getLogistics() {
        return logistics;
    }

    public void setLogistics(Logistics logistics) {
        this.logistics = logistics;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public RefundAddress getRefundAddress() {
        return refundAddress;
    }

    public void setRefundAddress(RefundAddress refundAddress) {
        this.refundAddress = refundAddress;
    }

    public RefundStatusEnum getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(RefundStatusEnum lastStatus) {
        this.lastStatus = lastStatus;
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
}
