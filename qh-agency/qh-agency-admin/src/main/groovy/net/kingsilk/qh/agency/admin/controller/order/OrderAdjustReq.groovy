package net.kingsilk.qh.agency.admin.controller.order

import io.swagger.annotations.ApiModel


@ApiModel(value = "订单修改提供信息")
class OrderAdjustReq {

     String id;
     Integer adjustPrice;

}
