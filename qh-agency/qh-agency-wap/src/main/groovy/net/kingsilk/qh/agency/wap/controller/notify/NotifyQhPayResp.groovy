package net.kingsilk.qh.agency.wap.controller.notify

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam

@ApiModel
class NotifyQhPayResp {

    @ApiParam(value = "code", required = true)
    @ApiModelProperty(value = "结果")
    String code;
}
