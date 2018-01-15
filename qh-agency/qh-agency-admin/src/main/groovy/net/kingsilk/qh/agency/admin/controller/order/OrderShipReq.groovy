package net.kingsilk.qh.agency.admin.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum


@ApiModel(value = "订单修改提供信息")
class OrderShipReq {

     @ApiParam(value = "id")
     String id;
     @ApiParam(value = "物流公司")
     LogisticsCompanyEnum company;
     @ApiParam(value = "物流单号")
     String expressNo;

}
