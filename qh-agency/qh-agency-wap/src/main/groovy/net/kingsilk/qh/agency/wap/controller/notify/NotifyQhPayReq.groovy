package net.kingsilk.qh.agency.wap.controller.notify

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam

@ApiModel
class NotifyQhPayReq {

    @ApiParam(value = "bizOrderNo", required = true)
    @ApiModelProperty(value = "商户提供的订单号")
    String bizOrderNo;

    @ApiParam(value = "type", required = true, allowableValues = "PAY,REFUND")
    @ApiModelProperty(value = "回调类型，支付成功/退款成功")
    String type

    @ApiParam(value = "itemId", required = false)
    @ApiModelProperty(value = "商品id，单个sku退款该值不为空")
    String itemId;

    @ApiParam(value = "itemId", required = false)
    @ApiModelProperty(value = "商品id，单个sku退款该值不为空")
    String payType;

    @ApiParam(value = "itemId", required = false)
    @ApiModelProperty(value = "商品id，单个sku退款该值不为空")
    Date payTime;

}
