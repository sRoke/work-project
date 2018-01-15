package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.ShopOrderStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document
public class ShopOrder extends Base {

    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;

    /**
     * 购买用户
     */
    private String userId;

    /**
     * 系统编码
     */
    private String seq;

    /**
     * 订单状态
     */
    private ShopOrderStatusEnum status;

    /**
     * 是否开发票
     */
    private String needInvoice;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 订单原价总和
     */
    private Integer totalPrice;

    /**
     * 使用的余额
     */
//    private Integer usedBalance;

    /**
     * 使用的优惠券
     */
//    private String couponId;

    /**
     * 优惠券抵用掉的总价格
     */
//    private Integer couponPrice;

    /**
     * 使用的积分
     */
//    private Integer usedIntegral;

    /**
     * 积分抵用的总价格
     */
//    private Integer integralPrice;

    /**
     * 客服改价的总金额
     */
//    private Integer adjustedAmount;

    /**
     * 支付记录id
     */
    private String qhPayId;

    /**
     * 使用的快递模板id
     */
//    private String deliveryTemplateId;

    /**
     * 实际支付金额
     */
    private Integer paymentAmount;

    /**
     * 拒绝接单原因
     */
//    private String refusedReason;

    /**
     * 确认收货时间
     */
//    private Date receivedTime;

    /**
     * 到点自提时间（OrderSourceTypeEnum订单类型为自取是有值）
     */
//    private Date sinceTakeTime;

    /**
     * 该比订单赠送的积分
     */
//    private Integer integral;

    /**
     * 买家备注
     */
    private String buyerMemo;


    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public ShopOrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ShopOrderStatusEnum status) {
        this.status = status;
    }

    public String getNeedInvoice() {
        return needInvoice;
    }

    public void setNeedInvoice(String needInvoice) {
        this.needInvoice = needInvoice;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getQhPayId() {
        return qhPayId;
    }

    public void setQhPayId(String qhPayId) {
        this.qhPayId = qhPayId;
    }

    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getBuyerMemo() {
        return buyerMemo;
    }

    public void setBuyerMemo(String buyerMemo) {
        this.buyerMemo = buyerMemo;
    }
}
