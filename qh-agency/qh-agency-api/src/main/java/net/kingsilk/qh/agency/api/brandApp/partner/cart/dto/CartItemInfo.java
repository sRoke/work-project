package net.kingsilk.qh.agency.api.brandApp.partner.cart.dto;


import net.kingsilk.qh.agency.api.common.dto.SkuInfoModel;

/**
 *
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
