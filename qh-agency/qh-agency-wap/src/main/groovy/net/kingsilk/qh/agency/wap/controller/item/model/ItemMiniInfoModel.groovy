package net.kingsilk.qh.agency.wap.controller.item.model

import io.swagger.annotations.ApiModelProperty
import net.kingsilk.qh.agency.domain.Item
import net.kingsilk.qh.agency.domain.Sku

/**
 * 精简版商品详情
 */
class ItemMiniInfoModel {

    @ApiModelProperty(value = "商品id")
    String itemId;

    @ApiModelProperty(value = "商品标题")
    String title;

    @ApiModelProperty(value = "商品金额")
    int price;

    @ApiModelProperty(value = "用户标签")
    String curTag

    @ApiModelProperty(value = "单位")
    String itemUnit;

    @ApiModelProperty(value = "商品描述")
    String desp

    @ApiModelProperty(value = "商品图片")
    Set<String> imgs

    @ApiModelProperty(value = "商品标签")
    Set<String> tags

    void convert(Item item, List<Sku> skus, String[] tags) {
        this.price = -1
        this.curTag = "ERROR"
        this.tags=item.tags
        if (skus && tags) {
            Sku sku = skus[0]           //暂时随便取一条
            Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
                return tags.contains(it.tag.code)
            }.min { Sku.TagPrice it ->
                it.price
            }
            this.price = minTag.price
            this.curTag = minTag.tag.code
        }

        this.itemId = item.id
        this.title = item.title
        this.itemUnit = item.itemUnit
        this.desp = item.desp
        this.imgs = item.imgs
    }
}
