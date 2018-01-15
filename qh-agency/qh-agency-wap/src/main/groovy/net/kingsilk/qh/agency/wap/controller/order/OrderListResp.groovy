package net.kingsilk.qh.agency.wap.controller.order

import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.wap.controller.order.model.OrderInfoModel
import org.springframework.data.domain.Page

/**
 * Created by zcw on 3/16/17.
 * 订单列表返回信息
 */
class OrderListResp {

    @ApiParam(value = "内容", required = false)
    Page<OrderInfoModel> orderInfoModel

}
