package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.ItemStatusEnum;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
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
     * 自定义编码
     */
    private String code;

    /**
     * 状态
     */
    private ItemStatusEnum status;


    /**
     * 商品规格列表。
     */
    private Set<SpecDef> specs = new LinkedHashSet<>();


    // ----------------------------- 以下为可以被 SKU 覆盖的属性。


    /**
     * 使用到的商品属性、以及商品属性值。
     * <p>
     * XXX : 注意：如果SKU覆盖该值，是需要将
     */
    private Set<UsedItemProp> props = new LinkedHashSet<>();

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
    private ArrayList<String> imgs = new ArrayList<>();


    /**
     * 标签。由店铺任意指定
     */
    private Set<String> tags;

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

    private Integer version;

    /**
     * 商品规格。
     */
    public static class SpecDef {

        /**
         * 规格ID。
         * <p>
         * 由前端生成，必须能转换为 ObjectId。主要用以方便更新。
         */
        private String id;

        /**
         * 商品属性。
         */
        @DBRef
        private ItemProp itemProp;

        /**
         * 商品属性值列表。
         * <p>
         * 删除候选值时，必须检查是否已经已上架的商品已经在用该候选值。
         */
        @DBRef
        private Set<ItemPropValue> itemPropValues = new LinkedHashSet<>();

        // --------------------------------------- getter && setter


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ItemProp getItemProp() {
            return itemProp;
        }

        public void setItemProp(ItemProp itemProp) {
            this.itemProp = itemProp;
        }

        public Set<ItemPropValue> getItemPropValues() {
            return itemPropValues;
        }

        public void setItemPropValues(Set<ItemPropValue> itemPropValues) {
            this.itemPropValues = itemPropValues;
        }
    }

    /**
     * 使用的商品属性。
     */
    public static class UsedItemProp {
        /**
         * ID。
         * <p>
         * 由前端生成，必须能转换为 ObjectId。主要用以方便更新。
         */
        private String id;

        /**
         * 商品属性。
         */
        @DBRef
        private ItemProp itemProp;

        /**
         * 使用的商品属性值。
         */
        @DBRef
        private ItemPropValue itemPropValue;


        // --------------------------------------- getter && setter


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ItemProp getItemProp() {
            return itemProp;
        }

        public void setItemProp(ItemProp itemProp) {
            this.itemProp = itemProp;
        }

        public ItemPropValue getItemPropValue() {
            return itemPropValue;
        }

        public void setItemPropValue(ItemPropValue itemPropValue) {
            this.itemPropValue = itemPropValue;
        }
    }


    // --------------------------------------- getter && setter


    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
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

    public Set<UsedItemProp> getProps() {
        return props;
    }

    public void setProps(Set<UsedItemProp> props) {
        this.props = props;
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

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
