package net.kingsilk.qh.agency.wap.controller.item.model

import groovy.transform.CompileStatic
import io.swagger.annotations.ApiModelProperty
import net.kingsilk.qh.agency.domain.Sku

/**
 * sku详情
 */
@CompileStatic
class SkuInfoModel extends SkuMiniInfoModel {

    @ApiModelProperty(value = "属性列表")
    List props;

    @ApiModelProperty(value = "规格列表")
    List specs;

    @ApiModelProperty(value = "图文描述")
    String detail

    String status

    void convert(Sku sku, String[] tags) {
        super.convert(sku, tags)
        this.props = []
        this.specs = []
        sku.props.each {
            def m = [
                    name : it.itemProp?.name,
                    value: it.itemPropValue?.name
            ]
            this.props.add(m)
        }
        sku.specs.each {
            def m = [
                    name   : it.itemProp?.name,
                    nameId : it.itemProp?.id,
                    value  : it.itemPropValue?.name,
                    valueId: it.itemPropValue?.id
            ]
            this.specs.add(m)
        }
        this.detail = sku.detail
        this.status=sku.status
    }
}
