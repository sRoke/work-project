package net.kingsilk.qh.agency.wap.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
class OrderCheckReq {

    /**
     * skuId
     */
    @ApiParam(name = "skuId", value = "skuId", required = true)
    String skuId;

    /**
     * 下单数量
     */
    @ApiParam(name = "num", value = "num", required = true)
    Integer num;
}
