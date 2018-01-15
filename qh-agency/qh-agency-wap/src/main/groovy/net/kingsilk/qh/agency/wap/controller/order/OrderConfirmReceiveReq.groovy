package net.kingsilk.qh.agency.wap.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam

@ApiModel
class OrderConfirmReceiveReq {

    @ApiParam(value = "id", required = true)
    @ApiModelProperty(value = "订单id")
    String id;
}
