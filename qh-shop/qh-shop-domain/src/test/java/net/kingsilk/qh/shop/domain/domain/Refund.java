package net.kingsilk.qh.shop.domain.bak;

import net.kingsilk.qh.shop.core.RefundStatusEnum;
import net.kingsilk.qh.shop.core.RefundTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 售后,一条记录只能退款一次，若需多次，请生成多条记录
 * <p>
 * ps. 退款按照不同维度可分为以下几类
 * <p>
 * 1. 按照退款来源方式划分
 * <p>
 * - 以订单为粒度进行退款（取消订单、拒绝接单等用户未收货时发生的退款），退款金额按照订单的金额去计算
 * - 以SKU为粒度进行退款（用户已确认收货，从库存中进行退款操作），退款金额按照当前该SKU的各级销售价去计算
 * <p>
 * 2. 按照退款方式去划分
 * <p>
 * - 全额退款       //TODO 去掉全额退款
 * - 协议退款
 * <p>
 * 3. 按照退款类型划分
 * <p>
 * - 退款退货
 * - 仅退款
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
    private String partnerId;

    /**
     * 当前退货记录所对应的订单，同一个订单可能有多条
     * 当order字段非空时，RefundSku不会生成相应记录
     */
    private String orderId;

//    /**
//     * 退款编号 FIXME:
//     */
//    private String seq;

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
    private RefundStatusEnum status;

    /**
     * 前一次售后状态
     */
    private RefundStatusEnum lastStatus;

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
     * 申请售后的原因
     */
    private String reason;

    /**
     * 拒绝售后的理由
     */
    private String rejectReason;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public RefundStatusEnum getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(RefundStatusEnum lastStatus) {
        this.lastStatus = lastStatus;
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
}
