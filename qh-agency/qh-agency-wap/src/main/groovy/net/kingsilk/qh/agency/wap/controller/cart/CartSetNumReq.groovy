package net.kingsilk.qh.agency.wap.controller.cart

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam

@ApiModel
class CartSetNumReq {

    @ApiParam(value = "skuId", required = true)
    @ApiModelProperty(value = "skuId")
    String skuId;

    @ApiParam(value = "num", required = true)
    @ApiModelProperty(value = "数量，小于0即删除")
    int num;

}
