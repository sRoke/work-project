package net.kingsilk.qh.agency.admin.controller.itemProp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "商品属性删除请求信息")
class ItemPropDeleteReq {

    @ApiParam(value = "商品属性的ID", required = true)
    String id;

}
