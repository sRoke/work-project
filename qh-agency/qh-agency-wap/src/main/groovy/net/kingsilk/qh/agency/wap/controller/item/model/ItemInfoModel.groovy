package net.kingsilk.qh.agency.wap.controller.item.model

import io.swagger.annotations.ApiModelProperty
import net.kingsilk.qh.agency.domain.Item
import net.kingsilk.qh.agency.domain.Sku

/**
 * Created by zcw on 3/16/17.
 * 商品详情
 */
class ItemInfoModel extends ItemMiniInfoModel {

    @ApiModelProperty(value = "属性")
    List props;

    @ApiModelProperty(value = "规格")
    List specs;

    @ApiModelProperty(value = "图文描述")
    String detail

    @ApiModelProperty(value = "商品标签")
    Set<String> tags;

    @ApiModelProperty(value = "sku详情")
    List<SkuInfoModel> skus;

    void convert(Item item, List<Sku> skus, String[] tags) {
        super.convert(item, skus, tags);
        this.props = []
        this.specs = []
        item.props.each {
            def m = [
                    name : it.itemProp?.name,
                    value: it.itemPropValue?.name
            ]
            this.props.add(m)
        }
        item.specs.each {
            def values = []
            it.itemPropValues.each {
                values.add([
                        valueId: it.id,
                        value  : it.name
                ])
            }
            def m = [
                    name  : it.itemProp?.name,
                    nameId: it.itemProp?.id,
                    values: values
            ]
            this.specs.add(m)
        }
        this.detail = item.detail
        this.tags = item.tags
        this.skus = []
    }
}
