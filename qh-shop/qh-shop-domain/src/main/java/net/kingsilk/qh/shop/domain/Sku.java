package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 商品的sku
 */
@Document
public class Sku extends Base {

    /**
     * 所属品牌商id
     */
    private String brandAppId;

    /**
     * 同步SKU的id
     */
    private String syncId;

    private Integer version;

    private String shopId;
    /**
     * 所属商品
     */
    private String itemId;

    /**
     * 该 SKU 的规格信息
     */
    private Set<Spec> specs = new LinkedHashSet<>();


    /**
     * 吊牌价 (单位:分)
     */
    private Integer labelPrice;

    /**
     * 促销价
     */
    private Integer salePrice;

    /**
     * 采购价
     */
    private Integer buyPrice;

    /**
     * 自定义编码
     */
    private String code;

    // ----------------------------- 以下为可以被 SKU 覆盖的属性。

    /**
     * 标题
     */
    private String title;

    /**
     * 描述 (标题下面较长的文本)
     */
    private String desp;

    /**
     * 图片列表，第一张图片为主图 (请注意去除重复)
     */
    private LinkedHashSet<String> imgs = new LinkedHashSet<>();


    /**
     * 图文详情
     */
    private String detail;

    /**
     * 商品单位
     */
    private String itemUnit;

    /**
     * sku状态
     */
    private Boolean enable;

    /**
     * 规格信息
     */
    public static class Spec {

        /**
         * 商品属性。
         */
        private String itemPropId;

        /**
         * 商品属性值列表。
         * <p>
         * 删除候选值时，必须检查是否已经已上架的商品已经在用该候选值。
         */
        private String itemPropValueId;

        // --------------------------------------- getter && setter

        public String getItemPropId() {
            return itemPropId;
        }

        public void setItemPropId(String itemPropId) {
            this.itemPropId = itemPropId;
        }

        public String getItemPropValueId() {
            return itemPropValueId;
        }

        public void setItemPropValueId(String itemPropValueId) {
            this.itemPropValueId = itemPropValueId;
        }
    }

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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Set<Spec> getSpecs() {
        return specs;
    }

    public void setSpecs(Set<Spec> specs) {
        this.specs = specs;
    }

    public Integer getLabelPrice() {
        return labelPrice;
    }

    public void setLabelPrice(Integer labelPrice) {
        this.labelPrice = labelPrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Integer buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public LinkedHashSet<String> getImgs() {
        return imgs;
    }

    public void setImgs(LinkedHashSet<String> imgs) {
        this.imgs = imgs;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getSyncId() {
        return syncId;
    }

    public void setSyncId(String syncId) {
        this.syncId = syncId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
