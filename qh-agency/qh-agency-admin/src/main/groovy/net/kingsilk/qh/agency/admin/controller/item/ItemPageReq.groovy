package net.kingsilk.qh.agency.admin.controller.item

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "商品分类分页请求信息")
 class ItemPageReq {

    @ApiParam(value = "当前页数", defaultValue = "1")
    Integer curPage

    @ApiParam(value = "每页数量", defaultValue = "15")
    Integer pageSize

    @ApiParam(value = "商品名称")
    String title;

    @ApiParam(value = "状态")
    String status;

}