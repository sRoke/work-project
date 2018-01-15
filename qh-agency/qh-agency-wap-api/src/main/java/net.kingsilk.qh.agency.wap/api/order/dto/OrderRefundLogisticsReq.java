package net.kingsilk.qh.agency.wap.api.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.wap.api.common.dto.Logistics;

@ApiModel
public class OrderRefundLogisticsReq {
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Logistics getLogistics() {
        return logistics;
    }

    public void setLogistics(Logistics logistics) {
        this.logistics = logistics;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @ApiParam(value = "orderId", required = true)
    @ApiModelProperty(value = "订单id")
    private String orderId;
    @ApiParam(value = "skuId", required = true)
    @ApiModelProperty(value = "申请售后的sku id")
    private String skuId;
    @ApiParam(value = "type", required = true, allowableValues = "MONEY_ONLY,ITEM")
    @ApiModelProperty(value = "售后类型")
    private String type;
    @ApiParam(value = "reason", required = true)
    @ApiModelProperty(value = "物流信息")
    private Logistics logistics;
    @ApiParam(value = "memo", required = true)
    @ApiModelProperty(value = "退货说明")
    private String memo;
}
