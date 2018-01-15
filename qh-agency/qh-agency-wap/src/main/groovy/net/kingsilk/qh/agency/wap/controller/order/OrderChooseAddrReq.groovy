package net.kingsilk.qh.agency.wap.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
class OrderChooseAddrReq {

    /**
     * 订单id
     */
    @ApiParam(value = "orderId", required = true)
    String orderId;

    /**
     * 地址id
     */
    @ApiParam(value = "addrId", required = true)
    String addrId;
}
