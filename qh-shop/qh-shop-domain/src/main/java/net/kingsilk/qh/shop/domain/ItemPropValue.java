package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 商品属性值的候选值列表.
 */
@Document
public class ItemPropValue extends Base {

    /**
     * 所属品牌。
     */
    private String brandAppId;

    /**
     * 门店id
     */
    private String shopId;

    /**
     * 商品属性
     */
    private String itemPropId;

    /**
     * 属性值。
     * <p>
     * 比如: "粉色"、"180*200"、"XL"、"16.0"
     */
    private String name;

    /**
     * 自定义代码
     */
    private String code;

    /**
     * 备注
     */
    private String memo;


    // --------------------------------------- getter && setter

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

    public String getItemPropId() {
        return itemPropId;
    }

    public void setItemPropId(String itemPropId) {
        this.itemPropId = itemPropId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
