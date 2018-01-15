package net.kingsilk.qh.agency.admin.controller.Refund

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam

@ApiModel
class RefundPaymentReq {

    @ApiModelProperty(value = "退款id")
    @ApiParam(value = "id", required = true)
    String id;

    @ApiModelProperty(value = "需退款的金额，单位是分")
    @ApiParam(value = "price", required = true)
    Integer price;
}
