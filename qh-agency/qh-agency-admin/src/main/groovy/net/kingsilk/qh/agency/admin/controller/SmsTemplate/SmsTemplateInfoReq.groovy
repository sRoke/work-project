package net.kingsilk.qh.agency.admin.controller.SmsTemplate

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
class SmsTemplateInfoReq {
    @ApiParam(value = "模板id", required = true)
    String id;
}
