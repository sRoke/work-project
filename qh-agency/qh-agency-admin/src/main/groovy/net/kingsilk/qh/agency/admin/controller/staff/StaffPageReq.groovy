package net.kingsilk.qh.agency.admin.controller.staff

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "员工分页请求信息")
class StaffPageReq {

    @ApiParam(value = "当前页数", defaultValue = "1")
    Integer curPage = 1;

    @ApiParam(value = "每页数量", defaultValue = "15")
    Integer pageSize = 15;

    @ApiParam(value = "帐号")
    String keyWord;

}