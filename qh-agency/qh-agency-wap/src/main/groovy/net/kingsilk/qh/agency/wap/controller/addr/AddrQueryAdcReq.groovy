package net.kingsilk.qh.agency.wap.controller.addr

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
class AddrQueryAdcReq {

    @ApiParam(value = "地区编码", required = false)
    String adc;
}
