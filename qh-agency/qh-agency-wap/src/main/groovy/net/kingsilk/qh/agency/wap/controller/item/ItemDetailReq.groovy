package net.kingsilk.qh.agency.wap.controller.item

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by zcw on 3/17/17.
 */
@ApiModel
class ItemDetailReq {

    /**
     * itemId
     */
    @ApiParam(value = "itemId", required = true)
    String itemId;
}
