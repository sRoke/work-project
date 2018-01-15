package net.kingsilk.qh.agency.admin.controller.Refund

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

@ApiModel(value = "订单分页请求信息")
class RefundPageReq {

    @ApiParam(value = "当前页数", defaultValue = "1")
    Integer curPage = 1;

    @ApiParam(value = "每页数量", defaultValue = "15")
    Integer pageSize = 15;

    @ApiParam(value = "售后订单类型")
    String type;

    @ApiParam(value = "售后订单状态")
    String status;

    @ApiParam(value = "售后理由")
    String reason;
}