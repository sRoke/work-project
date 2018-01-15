package net.kingsilk.qh.shop.api.brandApp.shop.mall.cart.dto;

import net.kingsilk.qh.shop.api.common.dto.SkuInfoModel;

public class CartItemInfo {

    private SkuInfoModel sku;
    private int num;

    public SkuInfoModel getSku() {
        return sku;
    }

    public void setSku(SkuInfoModel sku) {
        this.sku = sku;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
