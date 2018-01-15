package net.kingsilk.qh.agency.wap.controller.addr

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam

@ApiModel
class AddrDeleteReq {

    @ApiParam(value = "id", required = true)
    @ApiModelProperty(value = "id")
    String id;
}
