package net.kingsilk.qh.agency.wap.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
class OrderCancelReq {
    /**
     * 订单id
     */
    @ApiParam(value = "id", required = true)
    String id;

    /**
     * 取消原因
     */
    @ApiParam(value = "reason", required = false)
    String reason;
}
