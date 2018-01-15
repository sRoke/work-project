package net.kingsilk.qh.agency.api.brandApp.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropModel;
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropValueModel;
import net.kingsilk.qh.agency.core.ItemStatusEnum;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 */
@ApiModel(value = "ItemInfoResp")
public class ItemInfoResp {

    @ApiModelProperty(value = "商品ID")
    private java.lang.String id;

    @ApiModelProperty(value = "商品图文描述")
    private java.lang.String detail;

    @ApiModelProperty(value = "商品编码")
    private java.lang.String code;

    @ApiModelProperty(value = "商品标题")
    private java.lang.String title;

    @ApiModelProperty(value = "描述（标题下面较长的文本）")
    private java.lang.String desp;

    @ApiModelProperty(value = "商品状态")
    private ItemStatusEnum status;
    /**
     * 商品单位
     */
    @ApiModelProperty(value = "商品单位")
    private java.lang.String itemUnit;
    /**
     * 标签。由店铺任意指定
     */
    @ApiModelProperty(value = "标签。由店铺任意指定")
    private java.util.Set<java.lang.String> tags = new java.util.HashSet<java.lang.String>();
    @ApiModelProperty(value = "商品属性")

    private java.util.Set<ItemInfoResp.ItemProps> props = new java.util.HashSet<>();
    @ApiModelProperty(value = "商品规格以及候选值")

    private java.util.Set<ItemInfoResp.SpecDef> specs = new java.util.HashSet<>();
    @ApiModelProperty(value = "商品sku")

    private java.util.Set<SkuMiniInfo> skuList = new java.util.HashSet<>();
    @ApiModelProperty(value = "商品图片")

    private java.util.LinkedHashSet<java.lang.String> imgs = new java.util.LinkedHashSet<java.lang.String>();

    @ApiModel(value = "ItemSaveReq_Props")
    public static class ItemProps {

        @ApiModelProperty(value = "前端生成的 ObjectId")
        private java.lang.String id;
        @ApiModelProperty(value = "商品属性")
        private ItemPropModel itemProp;
        @ApiModelProperty(value = "商品属性候选值列表")
        private java.util.Set<ItemPropValueModel> itemPropValueList;
        @ApiModelProperty(value = "商品属性候选值")
        private ItemPropValueModel itemPropValue;

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

        public Set<ItemPropValueModel> getItemPropValueList() {
            return itemPropValueList;
        }

        public void setItemPropValueList(Set<ItemPropValueModel> itemPropValueList) {
            this.itemPropValueList = itemPropValueList;
        }

        public ItemPropValueModel getItemPropValue() {
            return itemPropValue;
        }

        public void setItemPropValue(ItemPropValueModel itemPropValue) {
            this.itemPropValue = itemPropValue;
        }
    }

    @ApiModel(value = "ItemSaveReq_Spec")
    public static class SpecDef {

        @ApiModelProperty(value = "前端生成的 ObjectId")
        private java.lang.String id;
        @ApiModelProperty(value = "商品规格候选值的列表列表")
        private java.util.Set<ItemPropValueModel> itemPropValueList;
        @ApiModelProperty(value = "商品规格")
        private ItemPropModel itemProp;
        @ApiModelProperty(value = "商品规格候选值的列表")
        private java.util.Set<ItemPropValueModel> itemPropValue = new java.util.HashSet<ItemPropValueModel>();

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Set<ItemPropValueModel> getItemPropValueList() {
            return itemPropValueList;
        }

        public void setItemPropValueList(Set<ItemPropValueModel> itemPropValueList) {
            this.itemPropValueList = itemPropValueList;
        }

        public ItemPropModel getItemProp() {
            return itemProp;
        }

        public void setItemProp(ItemPropModel itemProp) {
            this.itemProp = itemProp;
        }

        public Set<ItemPropValueModel> getItemPropValue() {
            return itemPropValue;
        }

        public void setItemPropValue(Set<ItemPropValueModel> itemPropValue) {
            this.itemPropValue = itemPropValue;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public ItemStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemStatusEnum status) {
        this.status = status;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Set<ItemProps> getProps() {
        return props;
    }

    public void setProps(Set<ItemProps> props) {
        this.props = props;
    }

    public Set<SpecDef> getSpecs() {
        return specs;
    }

    public void setSpecs(Set<SpecDef> specs) {
        this.specs = specs;
    }

    public Set<SkuMiniInfo> getSkuList() {
        return skuList;
    }

    public void setSkuList(Set<SkuMiniInfo> skuList) {
        this.skuList = skuList;
    }

    public LinkedHashSet<String> getImgs() {
        return imgs;
    }

    public void setImgs(LinkedHashSet<String> imgs) {
        this.imgs = imgs;
    }
}
