package net.kingsilk.qh.agency.admin.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

@ApiModel
class AddrReq {

    @ApiParam(value = "地区编码", required = false)
    String typeNo;

    @ApiParam(value = "级别")
    String level;
}
