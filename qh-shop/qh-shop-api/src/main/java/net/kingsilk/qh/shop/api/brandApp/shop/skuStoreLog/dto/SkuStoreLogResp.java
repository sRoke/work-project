package net.kingsilk.qh.shop.api.brandApp.shop.skuStoreLog.dto;

public class SkuStoreLogResp {

    private String brandAppId;

    private String shopId;

    private String storeId;
    /**
     * 库存记录ID
     */
    private String skuStoreId;

    /**
     * 变更的数量（正：增加；负：减少）
     */
    private int num;

    /**
     * 库存变更的原因
     * ps:例如
     * 进货、退货、卖出等
     */
    private String storeChangeEnum;


    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getSkuStoreId() {
        return skuStoreId;
    }

    public void setSkuStoreId(String skuStoreId) {
        this.skuStoreId = skuStoreId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getStoreChangeEnum() {
        return storeChangeEnum;
    }

    public void setStoreChangeEnum(String storeChangeEnum) {
        this.storeChangeEnum = storeChangeEnum;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
