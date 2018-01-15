package net.kingsilk.qh.shop.api.brandApp.shop.mall.refund.dto;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;


public class RefundLogisticsReq {

    @ApiParam(value = "orderId")
    @ApiModelProperty(value = "订单id")
    private String orderId;
    @ApiParam(value = "skuId", required = true)
    @ApiModelProperty(value = "申请售后的sku id")
    private String skuId;
    @ApiParam(value = "type", required = true, allowableValues = "MONEY_ONLY,ITEM")
    @ApiModelProperty(value = "售后类型")
    private String type;
    /**
     * 所选的物流公司
     */
    @ApiParam(value = "company", required = true)
    private String company;

    /**
     * 快递单号
     */
    @ApiParam(value = "expressNo", required = true)
    private String expressNo;
    @ApiParam(value = "memo", required = true)
    @ApiModelProperty(value = "退货说明")
    private String memo;

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
