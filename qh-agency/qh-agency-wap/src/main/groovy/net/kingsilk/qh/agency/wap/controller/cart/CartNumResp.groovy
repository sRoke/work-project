package net.kingsilk.qh.agency.wap.controller.cart

import groovy.transform.CompileStatic
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@CompileStatic
@ApiModel
class CartNumResp {

    @ApiModelProperty(value = "商品总数")
    int totalNum;

    @ApiModelProperty(value = "商品类型数量")
    int typeNum;
}
