package net.kingsilk.qh.shop.domain;


import net.kingsilk.qh.shop.core.SkuStoreChangeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * SKU库存变更记录表
 */
@Document
public class SkuStoreLog extends Base {
    private String brandAppId;

    private String shopId;
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
     *      进货、退货、卖出等
     */
    private SkuStoreChangeEnum storeChangeEnum;

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

    public SkuStoreChangeEnum getStoreChangeEnum() {
        return storeChangeEnum;
    }

    public void setStoreChangeEnum(SkuStoreChangeEnum storeChangeEnum) {
        this.storeChangeEnum = storeChangeEnum;
    }
}
