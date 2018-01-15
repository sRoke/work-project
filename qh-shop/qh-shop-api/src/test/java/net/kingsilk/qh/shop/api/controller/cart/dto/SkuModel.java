package net.kingsilk.qh.shop.api.controller.cart.dto;


import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 商品的sku
 */
public class SkuModel extends Base {

    /**
     * 所属品牌商id
     */
    private String brandAppId;

    /**
     * 所属商品
     */
    private String itemId;

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


    // ----------------------------- 以下为可以被 SKU 覆盖的属性。

    /**
     * 使用到的商品属性、以及商品属性值。
     * <p>
     * XXX : 注意：该值如果被SKU覆盖，是需要将 item 中的该字段进行合并，但这里的优先级更高。
     */
    private Set<Prop> props = new LinkedHashSet<>();

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
    private Set<String> imgs = new LinkedHashSet<>();


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
    public static class Prop {


        /**
         * 规格ID。
         * <p>
         * 由前端生成，必须能转换为 ObjectId。主要用以方便更新。
         */
        private String id;

        /**
         * 商品属性。
         */
        private ItemPropModel itemProp;

        /**
         * 商品属性值列表。
         * <p>
         * 删除候选值时，必须检查是否已经已上架的商品已经在用该候选值。
         */
        private ItemPropValueModel itemPropValue;

        // --------------------------------------- getter && setter

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public ItemPropModel getItemProp() {
            return itemProp;
        }

        public void setItemProp(ItemPropModel itemProp) {
            this.itemProp = itemProp;
        }

        public ItemPropValueModel getItemPropValue() {
            return itemPropValue;
        }

        public void setItemPropValue(ItemPropValueModel itemPropValue) {
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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public Set<Prop> getProps() {
        return props;
    }

    public void setProps(Set<Prop> props) {
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

    public Set<String> getImgs() {
        return imgs;
    }

    public void setImgs(Set<String> imgs) {
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
