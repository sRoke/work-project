package net.kingsilk.qh.agency.admin.controller.sms

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import org.springframework.format.annotation.DateTimeFormat

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
class SmsListReq {

    @ApiParam(value = "当前页数", required = false)
    int curPage = 1;

    @ApiParam(value = "分页大小", required = false)
    int pageSize = 15;

    //2017-03-16
    @ApiParam(value = "开始时间", required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    Date beginDate;

    @ApiParam(value = "结束时间", required = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    Date endDate;

    @ApiParam(value = "手机号", required = false)
    String keyword;
}
