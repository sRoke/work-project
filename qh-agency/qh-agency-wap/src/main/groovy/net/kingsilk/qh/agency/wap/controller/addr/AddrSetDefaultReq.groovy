package net.kingsilk.qh.agency.wap.controller.addr

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
class AddrSetDefaultReq {

    /**
     * 地址id
     */
    @ApiParam(value = "id", required = true)
    String id;
}
