package net.kingsilk.qh.agency.wap.resource.cart.convert

import net.kingsilk.qh.agency.domain.Cart
import net.kingsilk.qh.agency.domain.Sku
import net.kingsilk.qh.agency.wap.api.cart.dto.CartItemInfo
import net.kingsilk.qh.agency.wap.api.cart.dto.CartListResp
import net.kingsilk.qh.agency.wap.api.item.dto.SkuInfoModel
import net.kingsilk.qh.agency.wap.api.item.dto.SkuMiniInfoModel
import org.springframework.stereotype.Component

/**
 */
@Component
class CartConvert {

    static CartListResp cartItemInfoConvert(Set<Cart.CartItem> cartItems, String partnerType) {
        CartListResp cartListResp=new CartListResp()
        List<CartItemInfo> items = []
        if (!cartItems) {
            return
        }
        cartItems.each {
            SkuInfoModel skuInfoModel = skuInfoModelConvert(it.sku, partnerType)

            CartItemInfo cartItemInfo = new CartItemInfo()
            cartItemInfo.sku = skuInfoModel
            cartItemInfo.num = it.num
            items.add(cartItemInfo)
        }
        cartListResp.items=items
        return cartListResp
    }

    static SkuInfoModel skuInfoModelConvert(Sku sku, String partnerType) {
        SkuInfoModel skuInfoModel = new SkuInfoModel()
        Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
            return partnerType == it.tag.code
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

    SkuMiniInfoModel skuMiniInfoModelConvert(Sku sku, String[] tags) {
        SkuMiniInfoModel skuMiniInfoModel = new SkuMiniInfoModel()
        Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
            return tags.contains(it.tag.code)
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
}
