package net.kingsilk.qh.agency.admin.api.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.agency.admin.api.common.dto.ItemProp;
import net.kingsilk.qh.agency.admin.api.common.dto.ItemPropValue;
import net.kingsilk.qh.agency.core.ItemStatusEnum;

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

    private java.util.Set<ItemInfoResp.ItemSku> skuList = new java.util.HashSet<>();
    @ApiModelProperty(value = "商品图片")

    private java.util.Set<java.lang.String> imgs = new java.util.HashSet<java.lang.String>();

    @ApiModel(value = "ItemSaveReq_Props")
    public static class ItemProps {

        @ApiModelProperty(value = "前端生成的 ObjectId")
        private java.lang.String id;
        @ApiModelProperty(value = "商品属性")
        private ItemProp itemProp;
        @ApiModelProperty(value = "商品属性候选值列表")
        private java.util.Set<ItemPropValue> itemPropValueList;
        @ApiModelProperty(value = "商品属性候选值")
        private ItemPropValue itemPropValue;

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

        public Set<ItemPropValue> getItemPropValueList() {
            return itemPropValueList;
        }

        public void setItemPropValueList(Set<ItemPropValue> itemPropValueList) {
            this.itemPropValueList = itemPropValueList;
        }

        public ItemPropValue getItemPropValue() {
            return itemPropValue;
        }

        public void setItemPropValue(ItemPropValue itemPropValue) {
            this.itemPropValue = itemPropValue;
        }
    }

    @ApiModel(value = "ItemSaveReq_Spec")
    public static class SpecDef {

        @ApiModelProperty(value = "前端生成的 ObjectId")
        private java.lang.String id;
        @ApiModelProperty(value = "商品规格候选值的列表列表")
        private java.util.Set<ItemPropValue> itemPropValueList;
        @ApiModelProperty(value = "商品规格")
        private ItemProp itemProp;
        @ApiModelProperty(value = "商品规格候选值的列表")
        private java.util.Set<ItemPropValue> itemPropValue = new java.util.HashSet<ItemPropValue>();

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Set<ItemPropValue> getItemPropValueList() {
            return itemPropValueList;
        }

        public void setItemPropValueList(Set<ItemPropValue> itemPropValueList) {
            this.itemPropValueList = itemPropValueList;
        }

        public ItemProp getItemProp() {
            return itemProp;
        }

        public void setItemProp(ItemProp itemProp) {
            this.itemProp = itemProp;
        }

        public Set<ItemPropValue> getItemPropValue() {
            return itemPropValue;
        }

        public void setItemPropValue(Set<ItemPropValue> itemPropValue) {
            this.itemPropValue = itemPropValue;
        }
    }

    @ApiModel(value = "ItemSaveReq_Sku")
    public static class ItemSku {

        @ApiModelProperty(value = "前端生成的 ObjectId")
        private java.lang.String id;
        @ApiModelProperty(value = "该SKU包含了哪些商品规格及其值")
        private java.util.Set<ItemInfoResp.ItemSku.SkuSpec> specList = new java.util.HashSet<>();
        @ApiModelProperty(value = "加盟价")
        private java.lang.Integer leaguePrice;
        @ApiModelProperty(value = "代理价")
        private java.lang.Integer agencyPrice;
        @ApiModelProperty(value = "原价")
        private java.lang.Integer price;
        @ApiModelProperty(value = "库存")
        private java.lang.Integer storage;
        @ApiModelProperty(value = "状态")
        private java.lang.String status;

        @ApiModel(value = "ItemSaveReq_Sku_Spec")
        public static class SkuSpec {

            @ApiModelProperty(value = "前端生成的 ObjectId")
            private java.lang.String id;
            @ApiModelProperty(value = "商品规格")
            private ItemProp itemProp;
            @ApiModelProperty(value = "商品规格候选值")
            private ItemPropValue itemPropValue;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Set<SkuSpec> getSpecList() {
            return specList;
        }

        public void setSpecList(Set<SkuSpec> specList) {
            this.specList = specList;
        }

        public Integer getLeaguePrice() {
            return leaguePrice;
        }

        public void setLeaguePrice(Integer leaguePrice) {
            this.leaguePrice = leaguePrice;
        }

        public Integer getAgencyPrice() {
            return agencyPrice;
        }

        public void setAgencyPrice(Integer agencyPrice) {
            this.agencyPrice = agencyPrice;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getStorage() {
            return storage;
        }

        public void setStorage(Integer storage) {
            this.storage = storage;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

    public Set<ItemSku> getSkuList() {
        return skuList;
    }

    public void setSkuList(Set<ItemSku> skuList) {
        this.skuList = skuList;
    }

    public Set<String> getImgs() {
        return imgs;
    }

    public void setImgs(Set<String> imgs) {
        this.imgs = imgs;
    }
}
