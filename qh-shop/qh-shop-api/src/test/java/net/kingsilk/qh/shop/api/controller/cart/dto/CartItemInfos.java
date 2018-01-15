package net.kingsilk.qh.shop.api.controller.cart.dto;


import net.kingsilk.qh.shop.api.controller.order.dto.SkuInfo;

/**
 *
 */
public class CartItemInfos {

    private SkuInfo sku;
    private int num;

    public SkuInfo getSku() {
        return sku;
    }

    public void setSku(SkuInfo sku) {
        this.sku = sku;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
