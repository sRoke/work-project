package net.kingsilk.qh.agency.api.brandApp.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropModel;
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropValueModel;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class SkuMiniInfo {

    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 主图
     */
    @ApiParam(value = "主图")
    private String imgs;


    @ApiParam(value = "标题")
    private String title;

    @ApiModelProperty(value = "该SKU包含了哪些商品规格及其值")
    private Set<SkuSpec> specList = new HashSet<>();


    @ApiModelProperty(value = "加盟价")
    private Integer leaguePrice;
    @ApiModelProperty(value = "总代价")
    private Integer generalAgencyPrice;
    @ApiModelProperty(value = "市代价")
    private Integer regionalAgencyPrice;

//    @ApiModelProperty(value = "渠道商价格")
//    private Integer price;

    @ApiModelProperty(value = "吊牌价")
    private Integer labelPrice;
    @ApiModelProperty(value = "销售价")
    private Integer salePrice;

    @ApiModelProperty(value = "库存")
    private Integer storage;
    @ApiModelProperty(value = "状态")
    private String status;

    /**
     * 商品编码
     */
    private String code;

    @ApiModel(value = "ItemSaveReq_Sku_Spec")
    public static class SkuSpec {

        @ApiModelProperty(value = "前端生成的 ObjectId")
        private String id;
        @ApiModelProperty(value = "商品规格")
        private ItemPropModel itemProp;
        @ApiModelProperty(value = "商品规格候选值")
        private ItemPropValueModel itemPropValue;
        @ApiModelProperty(value = "商品规格id")
        private String itemPropId;
        @ApiModelProperty(value = "商品规格候选值id")
        private String itemPropValueId;

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

    public Integer getGeneralAgencyPrice() {
        return generalAgencyPrice;
    }

    public void setGeneralAgencyPrice(Integer generalAgencyPrice) {
        this.generalAgencyPrice = generalAgencyPrice;
    }

    public Integer getRegionalAgencyPrice() {
        return regionalAgencyPrice;
    }

    public void setRegionalAgencyPrice(Integer regionalAgencyPrice) {
        this.regionalAgencyPrice = regionalAgencyPrice;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
