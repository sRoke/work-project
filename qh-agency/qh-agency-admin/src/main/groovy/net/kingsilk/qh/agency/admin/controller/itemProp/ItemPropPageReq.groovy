package net.kingsilk.qh.agency.admin.controller.itemProp

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "商品属性分页请求信息")
class ItemPropPageReq {

    @ApiParam(value = "当前页数", defaultValue = "1")
    Integer curPage = 1;

    @ApiParam(value = "每页数量", defaultValue = "15")
    Integer pageSize = 15;

    @ApiParam(value = "属性名称")
    String name;


}