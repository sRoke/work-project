package net.kingsilk.qh.agency.admin.controller.SmsTemplate

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
class SmsTemplateListReq {
    
    @ApiParam(value = "当前页数", required = false)
    int curPage = 1;

    @ApiParam(value = "分页大小", required = false)
    int pageSize = 15;
}
