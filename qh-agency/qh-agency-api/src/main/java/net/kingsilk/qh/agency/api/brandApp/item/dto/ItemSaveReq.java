package net.kingsilk.qh.agency.api.brandApp.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.ws.rs.QueryParam;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 */
@ApiModel(value = "商品保存请求")
public class ItemSaveReq {

    @ApiModelProperty(value = "商品ID")
    @QueryParam(value = "id")
    private String id;
    @ApiModelProperty(value = "商品图文描述")
    @QueryParam(value = "detail")
    private String detail;
    @ApiModelProperty(value = "商品编码")
    @QueryParam(value = "code")
    private String code;
    @ApiModelProperty(value = "商品标题")
    @QueryParam(value = "title")
    private String title;
    @ApiModelProperty(value = "描述（标题下面较长的文本）")
    @QueryParam(value = "desp")
    private String desp;
    @ApiModelProperty(value = "商品状态")
    @QueryParam(value = "status")
    private String status;
    /**
     * 标签。由店铺任意指定
     */
    @ApiModelProperty(value = "标签。由店铺任意指定")
    @QueryParam(value = "tags")
    private java.util.Set<String> tags = new HashSet<String>();
    @ApiModelProperty(value = "商品规格以及候选值")
    @QueryParam(value = "specs")
    private java.util.Set<ItemSaveReq.SpecDef> specs;
    @ApiModelProperty(value = "商品sku")
    @QueryParam(value = "skuList")
    private java.util.Set<SkuMiniInfo> skuList;
    @ApiModelProperty(value = "商品图片")
    @QueryParam(value = "imgs")
    private java.util.LinkedHashSet<String> imgs = new java.util.LinkedHashSet<String>();

    @ApiModel(value = "ItemSaveReq_Spec")
    public static class SpecDef {

        @ApiModelProperty(value = "前端生成的 ObjectId")
        @QueryParam(value = "id")
        private String id;
        @ApiModelProperty(value = "商品规格的Id")
        @QueryParam(value = "itemPropId")
        private String itemPropId;
        @ApiModelProperty(value = "商品规格候选值的ID列表")
        @QueryParam(value = "itemPropValueIds")
        private java.util.Set<String> itemPropValueIds;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

//    @ApiModel(value = "ItemSaveReq_Sku")
//    public static class ItemSku {
//
//        @ApiModelProperty(value = "前端生成的 ObjectId")
//        @QueryParam(value = "id")
//        private String id;
//        @ApiModelProperty(value = "该SKU包含了哪些商品规格及其值")
//        @QueryParam(value = "specList")
//        private java.util.Set<ItemSaveReq.ItemSku.SkuSpec> specList;
//        @ApiModelProperty(value = "加盟价")
//        @QueryParam(value = "leaguePrice")
//        private Double leaguePrice;
//        @ApiModelProperty(value = "代理价")
//        @QueryParam(value = "agencyPrice")
//        private Double agencyPrice;
//        @ApiModelProperty(value = "原价")
//        @QueryParam(value = "price")
//        private Double price;
//        @ApiModelProperty(value = "库存")
//        @QueryParam(value = "storage")
//        private Integer storage;
//        @ApiModelProperty(value = "状态")
//        @QueryParam(value = "status")
//        private String status;
//
//        @ApiModel(value = "ItemSaveReq_Sku_Spec")
//        public static class SkuSpec {
//
//            @ApiModelProperty(value = "前端生成的 ObjectId")
//            @QueryParam(value = "id")
//            private String id;
//            @ApiModelProperty(value = "商品规格的Id")
//            @QueryParam(value = "itemPropId")
//            private String itemPropId;
//            @ApiModelProperty(value = "商品规格候选值的ID")
//            @QueryParam(value = "itemPropValueId")
//            private String itemPropValueId;
//
//            public String getId() {
//                return id;
//            }
//
//            public void setId(String id) {
//                this.id = id;
//            }
//
//            public String getItemPropId() {
//                return itemPropId;
//            }
//
//            public void setItemPropId(String itemPropId) {
//                this.itemPropId = itemPropId;
//            }
//
//            public String getItemPropValueId() {
//                return itemPropValueId;
//            }
//
//            public void setItemPropValueId(String itemPropValueId) {
//                this.itemPropValueId = itemPropValueId;
//            }
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public Set<SkuSpec> getSpecList() {
//            return specList;
//        }
//
//        public void setSpecList(Set<SkuSpec> specList) {
//            this.specList = specList;
//        }
//
//        public Double getLeaguePrice() {
//            return leaguePrice;
//        }
//
//        public void setLeaguePrice(Double leaguePrice) {
//            this.leaguePrice = leaguePrice;
//        }
//
//        public Double getAgencyPrice() {
//            return agencyPrice;
//        }
//
//        public void setAgencyPrice(Double agencyPrice) {
//            this.agencyPrice = agencyPrice;
//        }
//
//        public Double getPrice() {
//            return price;
//        }
//
//        public void setPrice(Double price) {
//            this.price = price;
//        }
//
//        public Integer getStorage() {
//            return storage;
//        }
//
//        public void setStorage(Integer storage) {
//            this.storage = storage;
//        }
//
//        public String getStatus() {
//            return status;
//        }
//
//        public void setStatus(String status) {
//            this.status = status;
//        }
//    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
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
