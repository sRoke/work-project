package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 各渠道商各sku库存表
 */
@Document
public class SkuStore extends Base {

    /**
     * 所属品牌
     */
    private String brandAppId;

    private String shopId;


    /**
     * 仓库ID
     */
    private String storeId;

    /**
     * SKU
     */
    private String skuId;

    /**
     * 库存量
     */
    private Integer num;

    /**
     * 销量
     */
    private Integer salesVolume = 0;

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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
