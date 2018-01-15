package net.kingsilk.qh.agency.wap.api.cart.dto;

import net.kingsilk.qh.agency.wap.api.item.dto.SkuInfoModel;

/**
 * Created by zcw on 3/30/17.
 */
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
