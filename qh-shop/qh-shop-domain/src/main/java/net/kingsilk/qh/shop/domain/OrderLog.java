package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.OperatorTypeEnum;
import net.kingsilk.qh.shop.core.OrderSourceTypeEnum;
import net.kingsilk.qh.shop.core.OrderStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 */
@Document
public class OrderLog extends Base {

    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 关联的订单ID
     */
    private String orderId;

    /**
     * 操作员工ID
     */
    private String staffId;

    /**
     * 客服改价的金额
     */
    private String adjustMoney;

    /**
     * 订单来源
     */
    private OrderSourceTypeEnum orderSourceType;

    /**
     * 操作后的订单状态
     */
    private OrderStatusEnum status;

    /**
     * 操作前的订单状态
     */
    private OrderStatusEnum lastStatus;

    /**
     * 操作类型
     */
    private OperatorTypeEnum type;

    /**
     * 备注
     */
    private String memo;

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

//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getAdjustMoney() {
        return adjustMoney;
    }

    public void setAdjustMoney(String adjustMoney) {
        this.adjustMoney = adjustMoney;
    }

    public OrderSourceTypeEnum getOrderSourceType() {
        return orderSourceType;
    }

    public void setOrderSourceType(OrderSourceTypeEnum orderSourceType) {
        this.orderSourceType = orderSourceType;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public OrderStatusEnum getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(OrderStatusEnum lastStatus) {
        this.lastStatus = lastStatus;
    }

    public OperatorTypeEnum getType() {
        return type;
    }

    public void setType(OperatorTypeEnum type) {
        this.type = type;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
