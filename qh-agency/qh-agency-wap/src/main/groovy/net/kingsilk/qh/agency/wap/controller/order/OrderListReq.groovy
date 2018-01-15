package net.kingsilk.qh.agency.wap.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.wap.controller.BasePageReq

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
class OrderListReq {

    @ApiParam(value = "当前页数", required = false)
    int number;

    @ApiParam(value = "分页大小", required = false)
    int pageSize;

    @ApiParam(value = "订单状态", required = false)
    String status;
}
