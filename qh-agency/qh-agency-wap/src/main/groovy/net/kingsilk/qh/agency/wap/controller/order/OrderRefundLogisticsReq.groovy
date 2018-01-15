package net.kingsilk.qh.agency.wap.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.domain.Logistics

@ApiModel
class OrderRefundLogisticsReq {

    @ApiParam(value = "orderId", required = true)
    @ApiModelProperty(value = "订单id")
    String orderId;

    @ApiParam(value = "skuId", required = true)
    @ApiModelProperty(value = "申请售后的sku id")
    String skuId;

    @ApiParam(value = "type", required = true, allowableValues = "MONEY_ONLY,ITEM")
    @ApiModelProperty(value = "售后类型")
    String type;

    @ApiParam(value = "reason", required = true)
    @ApiModelProperty(value = "物流信息")
    Logistics logistics

    @ApiParam(value = "memo", required = true)
    @ApiModelProperty(value = "退货说明")
    String memo;
}
