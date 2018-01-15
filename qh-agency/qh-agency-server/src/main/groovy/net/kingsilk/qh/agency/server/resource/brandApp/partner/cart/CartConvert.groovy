package net.kingsilk.qh.agency.server.resource.brandApp.partner.cart

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.brandApp.partner.cart.dto.CartItemInfo
import net.kingsilk.qh.agency.api.common.dto.SkuInfoModel
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.SkuStoreRepo
import net.kingsilk.qh.agency.service.PartnerStaffService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 */
@Component
class CartConvert {

    @Autowired
    PartnerStaffService partnerStaffService

    @Autowired
    SkuStoreRepo skuStoreRepo

    List<CartItemInfo> cartItemInfoConvert(Set<Cart.CartItem> cartItems, String partnerType,String type) {
        List<CartItemInfo> items = []
        if (!cartItems) {
            return
        }
        cartItems.each {
            SkuInfoModel skuInfoModel = skuInfoModelConvert(it.sku, partnerType,type)

            CartItemInfo cartItemInfo = new CartItemInfo()
            cartItemInfo.sku = skuInfoModel
            cartItemInfo.num = it.num
            items.add(cartItemInfo)
        }
        return items
    }

    SkuInfoModel skuInfoModelConvert(Sku sku, String partnerType, String type ) {
        PartnerStaff curMember = partnerStaffService.getCurPartnerStaff()
        SkuInfoModel skuInfoModel = new SkuInfoModel()
        Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
            return partnerType == it.tag.code
        }.min { Sku.TagPrice it ->
            it.price
        }
        skuInfoModel.price = minTag.price
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

        SkuStore skuStore = skuStoreRepo.findOne(
                Expressions.allOf(
                        QSkuStore.skuStore.sku.eq(sku),
                        type== "REFUND" ? QSkuStore.skuStore.partner.eq(curMember.partner) :
                                QSkuStore.skuStore.partner.eq(curMember.partner.parent),
                        QSkuStore.skuStore.brandAppId.eq(curMember.brandAppId),
                )
        )
        skuInfoModel.storage = skuStore ? skuStore.num : 0

        return skuInfoModel
    }
//
//    SkuMiniInfoModel skuMiniInfoModelConvert(Sku sku, String[] tags) {
//        SkuMiniInfoModel skuMiniInfoModel = new SkuMiniInfoModel()
//        Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
//            return tags.contains(it.tag.code)
//        }.min { Sku.TagPrice it ->
//            it.price
//        }
//        skuMiniInfoModel.price = minTag.price / 100
//        skuMiniInfoModel.curTag = minTag.tag.code
//        skuMiniInfoModel.skuId = sku.id
//        skuMiniInfoModel.title = sku.item?.title
//        skuMiniInfoModel.imgs = sku.item?.imgs
//        return skuMiniInfoModel
//    }
}
