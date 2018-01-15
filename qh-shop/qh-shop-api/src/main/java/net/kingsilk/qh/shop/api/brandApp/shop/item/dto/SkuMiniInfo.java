package net.kingsilk.qh.shop.api.brandApp.shop.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
public class SkuMiniInfo {

    @ApiModelProperty(value = "id")
    private String id;

    private Integer version;
    /**
     * 主图
     */
    @ApiParam(value = "主图")
    private String imgs;


    @ApiParam(value = "标题")
    private String title;

    @ApiModelProperty(value = "该SKU包含了哪些商品规格及其值")
    private List<SkuSpec> specList = new ArrayList<>();

    private String specTitle;

    private String specValue;

    /**
     * 吊牌价 (单位:分)
     */
    private Integer labelPrice = 0;

    /**
     * 促销价
     */
    private Integer salePrice = 0;

    private Boolean check;

    /**
     * 采购价
     */
    private Integer buyPrice = 0;

    @ApiModelProperty(value = "库存")
    private Integer storage;
    @ApiModelProperty(value = "状态")
    private Boolean enable = true;



    /**
     * 商品编码
     */
    private String code;

    @ApiModel(value = "ItemSaveReq_Sku_Spec")
    public static class SkuSpec {

        @ApiModelProperty(value = "商品规格id")
        private String itemPropId;

        @ApiModelProperty(value = "商品规格")
        private String itemPropName;

        @ApiModelProperty(value = "商品规格候选值id")
        private String itemPropValueId;

        @ApiModelProperty(value = "商品规格候选值")
        private String itemPropValueName;

        public String getItemPropId() {
            return itemPropId;
        }

        public void setItemPropId(String itemPropId) {
            this.itemPropId = itemPropId;
        }

        public String getItemPropName() {
            return itemPropName;
        }

        public void setItemPropName(String itemPropName) {
            this.itemPropName = itemPropName;
        }

        public String getItemPropValueId() {
            return itemPropValueId;
        }

        public void setItemPropValueId(String itemPropValueId) {
            this.itemPropValueId = itemPropValueId;
        }

        public String getItemPropValueName() {
            return itemPropValueName;
        }

        public void setItemPropValueName(String itemPropValueName) {
            this.itemPropValueName = itemPropValueName;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SkuSpec> getSpecList() {
        return specList;
    }

    public void setSpecList(List<SkuSpec> specList) {
        this.specList = specList;
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Integer buyPrice) {
        this.buyPrice = buyPrice;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getSpecTitle() {
        return specTitle;
    }

    public void setSpecTitle(String specTitle) {
        this.specTitle = specTitle;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
