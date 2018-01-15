package net.kingsilk.qh.agency.api.brandApp.partner.skuStore.dto;

/**
 *
 */
@Deprecated
public class SkuStoreInfoResp {

    /**
     * 库存SKU的ID
     */
    private String skuId;

    /**
     * 库存Sku的数量
     */
    private int num;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
