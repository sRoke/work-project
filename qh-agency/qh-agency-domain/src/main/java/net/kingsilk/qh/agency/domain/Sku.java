package net.kingsilk.qh.agency.domain;

import net.kingsilk.qh.agency.core.PartnerTypeEnum;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
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
     * 所属商品
     */
    @DBRef
    private Item item;

    /**
     * 该 SKU 的规格信息
     */
    private Set<Spec> specs = new LinkedHashSet<>();


    /**
     * 吊牌价 (单位:分)
     */
    private Integer labelPrice = 0;

    /**
     * 销售价
     */
    private Integer salePrice;

    /**
     * 自定义编码
     */
    private String code;
    /**
     * 根据不同标签给不同打价格。
     * <p>
     * 标签可以随意定。当前是与 PartnerStaff 中的 tags 进行匹配。
     * 如果匹配到多个标签，则使用最低价。
     */
    private Set<TagPrice> tagPrices = new LinkedHashSet<>();


    // ----------------------------- 以下为可以被 SKU 覆盖的属性。

    /**
     * 使用到的商品属性、以及商品属性值。
     * <p>
     * XXX : 注意：该值如果被SKU覆盖，是需要将 item 中的该字段进行合并，但这里的优先级更高。
     */
    private Set<Item.UsedItemProp> props = new LinkedHashSet<>();

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

    /**
     * sku状态  FIXME  boolean disabled
     */
    private String status;

    /**
     * 规格信息
     */
    public static class Spec {


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

    /**
     * 跟标签相关的价格。
     */
    public static class TagPrice {

        /**
         * 规格ID。
         * <p>
         * 由前端生成，必须能转换为 ObjectId。主要用以方便更新。
         */
        private String id;

        /**
         * 标签
         */
        private PartnerTypeEnum tag;

        /**
         * 价格。 单位：分。
         */
        private Integer price;


        // --------------------------------------- getter && setter

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public PartnerTypeEnum getTag() {
            return tag;
        }

        public void setTag(PartnerTypeEnum tag) {
            this.tag = tag;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }
    }

    // --------------------------------------- getter && setter


    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<TagPrice> getTagPrices() {
        return tagPrices;
    }

    public void setTagPrices(Set<TagPrice> tagPrices) {
        this.tagPrices = tagPrices;
    }

    public Set<Item.UsedItemProp> getProps() {
        return props;
    }

    public void setProps(Set<Item.UsedItemProp> props) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
