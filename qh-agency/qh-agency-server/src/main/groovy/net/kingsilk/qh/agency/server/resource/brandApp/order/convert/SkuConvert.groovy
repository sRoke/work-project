package net.kingsilk.qh.agency.server.resource.brandApp.order.convert

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.api.common.dto.SkuInfoModel
import net.kingsilk.qh.agency.api.common.dto.SkuMiniInfoModel
import net.kingsilk.qh.agency.api.brandApp.order.dto.OrderInfoModel
import net.kingsilk.qh.agency.core.PartnerTypeEnum
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.domain.Sku
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
        sku.realTotalPrice = item.realTotalPrice
        sku.skuPrice = item.skuPrice
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
        if (!tags.equals(PartnerTypeEnum.BRAND_COM.code)) {
            skuInfoModel.price = minTag.price
            skuInfoModel.curTag = minTag.tag.code
        } else {
            skuInfoModel.curTag = PartnerTypeEnum.BRAND_COM.code
        }
        skuInfoModel.salePrice = sku.salePrice
        skuInfoModel.labelPrice = sku.labelPrice
        skuInfoModel.skuId = sku.id
        skuInfoModel.title = sku.item?.title
        skuInfoModel.imgs = sku.imgs?:sku.item.imgs
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
        skuMiniInfoModel.price = minTag.price
        skuMiniInfoModel.curTag = minTag.tag.code
        skuMiniInfoModel.skuId = sku.id
        skuMiniInfoModel.title = sku.item?.title
        skuMiniInfoModel.imgs = sku.item?.imgs

        return skuMiniInfoModel
    }

    OrderInfoModel.OrderSkuModel orderSkuModelConvert(Order.OrderItem item, String partnerTypeEnum) {
        OrderInfoModel.OrderSkuModel orderSkuModel = new OrderInfoModel.OrderSkuModel()
        orderSkuModel.skuId = item.sku.id
        orderSkuModel.num = item.num
        orderSkuModel.realTotalPrice = item.realTotalPrice
        orderSkuModel.skuPrice = item.skuPrice
        orderSkuModel.skuInfo = new SkuInfoModel()
        orderSkuModel.skuInfo = skuInfoConvert(item.sku, partnerTypeEnum)
        return orderSkuModel
    }

}
