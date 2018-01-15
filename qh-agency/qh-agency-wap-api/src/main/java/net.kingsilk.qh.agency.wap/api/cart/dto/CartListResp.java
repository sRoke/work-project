package net.kingsilk.qh.agency.wap.api.cart.dto;

import java.util.List;

public class CartListResp {
//    public void convert(Set<Cart.CartItem> cartItems, final String[] tags) {
//        items = new ArrayList<CartItemInfo>();
//        if (!DefaultGroovyMethods.asBoolean(cartItems)) {
//            return;
//
//        }
//
//        DefaultGroovyMethods.each(cartItems, new Closure<Boolean>(this, this) {
//            public Boolean doCall(Cart.CartItem it) {
//                SkuInfoModel skuInfoModel = new SkuInfoModel();
//                skuInfoModel.invokeMethod("convert", new Object[]{it.getSku(), tags});
//
//                CartItemInfo cartItemInfo = new CartItemInfo();
//                cartItemInfo.sku = skuInfoModel;
//                cartItemInfo.num = it.getNum();
//                return items.add(cartItemInfo);
//            }
//
//            public Boolean doCall() {
//                return doCall(null);
//            }
//
//        });
//    }


    private List<CartItemInfo> items;

    public List<CartItemInfo> getItems() {
        return items;
    }

    public void setItems(List<CartItemInfo> items) {
        this.items = items;
    }
}
