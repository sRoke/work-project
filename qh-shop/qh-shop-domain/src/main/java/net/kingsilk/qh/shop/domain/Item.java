package net.kingsilk.qh.shop.domain;

import net.kingsilk.qh.shop.core.ItemStatusEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 商品
 */
@Document
public class Item extends Base {

    /**
     * 所属品牌。
     */
    private String brandAppId;

    /**
     * 商品同步使用字段，同步商品原ID
     */
    private String syncId;

    /**
     * 商品同步使用字段，同步的版本
     */
    private Integer version;

    /**
     * 门店id
     */
    private String shopId;

    /**
     * 自定义编码
     */
    private String code;

    /**
     * 状态
     */
    private ItemStatusEnum status = ItemStatusEnum.EDITING;


    /**
     * 商品规格列表。
     */
    private Set<SpecDef> specs = new LinkedHashSet<>();

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
     * 标签。由店铺任意指定
     */
    private Set<String> categorys;

    /**
     * 图文详情
     */
    private String detail;

    /**
     * 商品单位
     */
    private String itemUnit;

    /**
     * 上架日期
     */
    private Date onSaleTime;

    /**
     * 运费
     */
    private Integer freight;

    /**
     * 商品规格。
     */
    public static class SpecDef {

        /**
         * 商品属性。
         */
        private String itemPropId;

        /**
         * 商品属性值列表。
         * <p>
         * 删除候选值时，必须检查是否已经已上架的商品已经在用该候选值。
         */
        private Set<String> itemPropValueIds = new LinkedHashSet<>();

        // --------------------------------------- getter && setter

        public String getItemPropId() {
            return itemPropId;
        }

        public void setItemPropId(String itemPropId) {
            this.itemPropId = itemPropId;
        }

        public Set<String> getItemPropValueIds() {
            return itemPropValueIds;
        }

        public void setItemPropValueIds(Set<String> itemPropValueIds) {
            this.itemPropValueIds = itemPropValueIds;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ItemStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemStatusEnum status) {
        this.status = status;
    }

    public Set<SpecDef> getSpecs() {
        return specs;
    }

    public void setSpecs(Set<SpecDef> specs) {
        this.specs = specs;
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

    public Set<String> getCategorys() {
        return categorys;
    }

    public void setCategorys(Set<String> categorys) {
        this.categorys = categorys;
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

    public Date getOnSaleTime() {
        return onSaleTime;
    }

    public void setOnSaleTime(Date onSaleTime) {
        this.onSaleTime = onSaleTime;
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

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }
}
