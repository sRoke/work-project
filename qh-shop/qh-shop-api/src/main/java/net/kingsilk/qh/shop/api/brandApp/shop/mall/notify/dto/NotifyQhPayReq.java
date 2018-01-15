package net.kingsilk.qh.shop.api.brandApp.shop.mall.notify.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public class NotifyQhPayReq {

    @ApiParam(value = "bizOrderNo", required = true)
    @ApiModelProperty(value = "商户提供的订单号")
    private String bizOrderNo;

    @ApiParam(value = "type", required = true, allowableValues = "PAY,REFUND")
    @ApiModelProperty(value = "回调类型，支付成功/退款成功")
    private String type;

    @ApiParam(value = "itemId", required = false)
    @ApiModelProperty(value = "商品id，单个sku退款该值不为空")
    private String itemId;

    @ApiParam(value = "itemId", required = false)
    @ApiModelProperty(value = "支付类型")
    private String payType;

    @ApiParam(value = "itemId", required = false)
    @ApiModelProperty(value = "支付时间")
    private String payTime;


    public String getBizOrderNo() {
        return bizOrderNo;
    }

    public void setBizOrderNo(String bizOrderNo) {
        this.bizOrderNo = bizOrderNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
}
