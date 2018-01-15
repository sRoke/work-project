package net.kingsilk.qh.agency.wap.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam

@ApiModel
class OrderCreateReq {

    @ApiParam(name = "orderId", value = "orderId", required = true)
    @ApiModelProperty(value = "订单id")
    String orderId;

    @ApiParam(name = "from", value = "from", allowableValues = "CART,ITEM,ORDER", required = false)
    @ApiModelProperty(value = "下单来源，购物车，直接下单，再次下单，默认直接下单")
    String from;
}
