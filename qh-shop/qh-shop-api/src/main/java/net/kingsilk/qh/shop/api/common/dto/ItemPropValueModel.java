package net.kingsilk.qh.shop.api.common.dto;


/**
 * 商品属性值的候选值列表.
 */
public class ItemPropValueModel {

    /**
     * 所属品牌。
     */
    private String brandAppId;

    private String shopId;

    /**
     * id
     */
    private String id;

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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
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

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
