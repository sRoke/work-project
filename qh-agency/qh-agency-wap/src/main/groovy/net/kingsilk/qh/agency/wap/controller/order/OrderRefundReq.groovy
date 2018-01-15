package net.kingsilk.qh.agency.wap.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam

@ApiModel
class OrderRefundReq {

    @ApiParam(value = "orderId", required = true)
    @ApiModelProperty(value = "订单id")
    String orderId;

    @ApiParam(value = "skuId", required = true)
    @ApiModelProperty(value = "申请售后的sku id")
    String skuId;

//    /**
//     * 申请的数量
//     */
//    @ApiParam(value = "num", required = true)
//    Integer num;

    @ApiParam(value = "applyPrice", required = true)
    @ApiModelProperty(value = "申请的金额，单位是分")
    Integer applyPrice

    @ApiParam(value = "type", required = true, allowableValues = "MONEY_ONLY,ITEM")
    @ApiModelProperty(value = "售后类型")
    String type;

    @ApiParam(value = "reason", required = true)
    @ApiModelProperty(value = "申请售后原因")
    String reason;

    @ApiParam(value = "memo", required = true)
    @ApiModelProperty(value = "退货说明")
    String memo;
}
