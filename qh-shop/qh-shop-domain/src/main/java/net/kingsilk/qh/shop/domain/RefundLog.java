package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.OperatorTypeEnum;
import net.kingsilk.qh.shop.core.RefundStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 退货日志
 */
@Document
public class RefundLog extends Base{

    private String brandAppId;

    /**
     * 退款单ID
     */
    private String refundId;

    /**
     * 操作员ID
     */
    private String staffId;

    /**
     * 退款金额
     */
    private Integer refundMoney;

    /**
     * 修改金额
     */
    private Integer adjustMoney;

    /**
     *操作后的状态
     */
    private RefundStatusEnum status;

    /**
     * 操作前的状态
     */
    private RefundStatusEnum lastStatus;

    /**
     * 操作类型
     */
    private OperatorTypeEnum OperatorType;

    /**
     * 门店ID
     */
    private String shopId;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Integer getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Integer refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Integer getAdjustMoney() {
        return adjustMoney;
    }

    public void setAdjustMoney(Integer adjustMoney) {
        this.adjustMoney = adjustMoney;
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

    public OperatorTypeEnum getOperatorType() {
        return OperatorType;
    }

    public void setOperatorType(OperatorTypeEnum operatorType) {
        OperatorType = operatorType;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
