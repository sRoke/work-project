package net.kingsilk.qh.shop.api.brandApp.shop.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.shop.api.common.dto.ItemPropModel;
import net.kingsilk.qh.shop.api.common.dto.ItemPropValueModel;
import net.kingsilk.qh.shop.core.ItemStatusEnum;

import java.util.*;

/**
 *
 */
@ApiModel(value = "ItemInfoResp")
public class ItemInfoResp {

    @ApiModelProperty(value = "商品ID")
    private String id;

    @ApiModelProperty(value = "商品图文描述")
    private String detail;

    @ApiModelProperty(value = "商品编码")
    private String code;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "描述（标题下面较长的文本）")
    private String desp;

    @ApiModelProperty(value = "商品状态")
    private ItemStatusEnum status;
//    /**
//     * 商品单位
//     */
//    @ApiModelProperty(value = "商品单位")
//    private String itemUnit;
    /**
     * 标签。由店铺任意指定
     */
    @ApiModelProperty(value = "标签。由店铺任意指定")
    private Map<String, Set<String>> category = new HashMap<>();
    private Set<String> categorys = new HashSet<>();
    @ApiModelProperty(value = "商品属性")

    private List<SpecDef> specs = new ArrayList<>();
    @ApiModelProperty(value = "商品sku")

    private List<SkuMiniInfo> skuList = new ArrayList<>();
    @ApiModelProperty(value = "商品图片")

    private Set<String> imgs = new HashSet<>();

    /**
     * 运费
     */
    private Integer freight;

    @ApiModel(value = "ItemSaveReq_Props")
    public static class ItemProps {

        @ApiModelProperty(value = "前端生成的 ObjectId")
        private String id;
        @ApiModelProperty(value = "商品属性")
        private ItemPropModel itemProp;
        @ApiModelProperty(value = "商品属性候选值列表")
        private Set<ItemPropValueModel> itemPropValueList;
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

        @ApiModelProperty(value = "商品规格")
        private ItemPropModel itemProp;

        @ApiModelProperty(value = "商品规格候选值的列表")
        private List<ItemPropValueModel> itemPropValueList = new ArrayList<>();

//        @ApiModelProperty(value = "商品规格候选值的列表")
//        private Set<ItemPropValueModel> itemPropValue = new java.util.HashSet<ItemPropValueModel>();

        public ItemPropModel getItemProp() {
            return itemProp;
        }

        public void setItemProp(ItemPropModel itemProp) {
            this.itemProp = itemProp;
        }

        public List<ItemPropValueModel> getItemPropValueList() {
            return itemPropValueList;
        }

        public void setItemPropValueList(List<ItemPropValueModel> itemPropValueList) {
            this.itemPropValueList = itemPropValueList;
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
//
//    public String getItemUnit() {
//        return itemUnit;
//    }
//
//    public void setItemUnit(String itemUnit) {
//        this.itemUnit = itemUnit;
//    }

    public Map<String, Set<String>> getCategory() {
        return category;
    }

    public void setCategory(Map<String, Set<String>> category) {
        this.category = category;
    }

    public Set<String> getCategorys() {
        return categorys;
    }

    public void setCategorys(Set<String> categorys) {
        this.categorys = categorys;
    }

    public List<SpecDef> getSpecs() {
        return specs;
    }

    public void setSpecs(List<SpecDef> specs) {
        this.specs = specs;
    }

    public List<SkuMiniInfo> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SkuMiniInfo> skuList) {
        this.skuList = skuList;
    }

    public Set<String> getImgs() {
        return imgs;
    }

    public void setImgs(Set<String> imgs) {
        this.imgs = imgs;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }
}
