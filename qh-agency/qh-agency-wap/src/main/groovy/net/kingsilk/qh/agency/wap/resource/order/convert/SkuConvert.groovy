package net.kingsilk.qh.agency.wap.resource.order.convert

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.domain.Sku
import net.kingsilk.qh.agency.wap.api.item.dto.SkuInfoModel
import net.kingsilk.qh.agency.wap.api.item.dto.SkuMiniInfoModel
import net.kingsilk.qh.agency.wap.api.order.dto.OrderInfoModel
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/20.
 */
@CompileStatic
@Component
class SkuConvert {

    OrderInfoModel.OrderSkuModel skuConvert(Order.OrderItem item, String tags) {
        OrderInfoModel.OrderSkuModel sku = new OrderInfoModel.OrderSkuModel();
        sku.skuId = item.sku.id
        sku.num = item.num
        sku.realTotalPrice = item.realTotalPrice / 100
        sku.skuPrice = item.skuPrice / 100
        sku.skuInfo = new SkuInfoModel()
        sku.skuInfo = skuInfoConvert(item.sku, tags)
        return sku
    }

    SkuInfoModel skuInfoConvert(Sku sku, String tags) {
        SkuInfoModel skuInfoModel = new SkuInfoModel()

        Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
            return tags == it.tag.code
        }.min { Sku.TagPrice it ->
            it.price
        }
        skuInfoModel.price = minTag.price / 100
        skuInfoModel.curTag = minTag.tag.code
        skuInfoModel.skuId = sku.id
        skuInfoModel.title = sku.item?.title
        skuInfoModel.imgs = sku.item?.imgs
        skuInfoModel.props = []
        skuInfoModel.specs = []
        sku.props.each {
            def m = [
                    name : it.itemProp?.name,
                    value: it.itemPropValue?.name
            ]
            skuInfoModel.props.add(m)
        }
        sku.specs.each {
            def m = [
                    name   : it.itemProp?.name,
                    nameId : it.itemProp?.id,
                    value  : it.itemPropValue?.name,
                    valueId: it.itemPropValue?.id
            ]
            skuInfoModel.specs.add(m)
        }
        skuInfoModel.detail = sku.detail
        skuInfoModel.status = sku.status

        return skuInfoModel
    }

    SkuMiniInfoModel skuMiniInfoConvert(Sku sku, tags) {
        SkuMiniInfoModel skuMiniInfoModel = new SkuMiniInfoModel()
        Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
            return tags == it.tag.code
        }.min { Sku.TagPrice it ->
            it.price
        }
        skuMiniInfoModel.price = minTag.price / 100
        skuMiniInfoModel.curTag = minTag.tag.code
        skuMiniInfoModel.skuId = sku.id
        skuMiniInfoModel.title = sku.item?.title
        skuMiniInfoModel.imgs = sku.item?.imgs

        return skuMiniInfoModel
    }

    OrderInfoModel.OrderSkuModel orderSkuModelConvert(Order.OrderItem item,String partnerTypeEnum) {
        OrderInfoModel.OrderSkuModel orderSkuModel = new OrderInfoModel.OrderSkuModel()
        orderSkuModel.skuId = item.sku.id
        orderSkuModel.num = item.num
        orderSkuModel.realTotalPrice = item.realTotalPrice / 100
        orderSkuModel.skuPrice = item.skuPrice / 100
        orderSkuModel.skuInfo = new SkuInfoModel()
        orderSkuModel.skuInfo=skuInfoConvert(item.sku, partnerTypeEnum)
        return orderSkuModel
    }

}
