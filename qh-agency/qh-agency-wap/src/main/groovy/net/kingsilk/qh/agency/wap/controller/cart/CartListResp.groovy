package net.kingsilk.qh.agency.wap.controller.cart

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.domain.Cart
import net.kingsilk.qh.agency.wap.controller.cart.model.CartItemInfo
import net.kingsilk.qh.agency.wap.controller.item.model.SkuInfoModel

@CompileStatic
class CartListResp {

    List<CartItemInfo> items;

    void convert(Set<Cart.CartItem> cartItems, String[] tags) {
        items = []
        if (!cartItems) {
            return
        }
        cartItems.each {
            SkuInfoModel skuInfoModel = new SkuInfoModel()
            skuInfoModel.convert(it.sku, tags)

            CartItemInfo cartItemInfo = new CartItemInfo()
            cartItemInfo.sku = skuInfoModel
            cartItemInfo.num = it.num
            items.add(cartItemInfo)
        }
    }
}
