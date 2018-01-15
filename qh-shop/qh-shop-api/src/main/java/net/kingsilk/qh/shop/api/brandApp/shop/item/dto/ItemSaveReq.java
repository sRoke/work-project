package net.kingsilk.qh.shop.api.brandApp.shop.item.dto;

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
    @QueryParam(value = "categorys")
    private Set<String> categorys = new HashSet<String>();

    @ApiModelProperty(value = "商品规格以及候选值")
    @QueryParam(value = "specs")
    private Set<SpecDef> specs;

    @ApiModelProperty(value = "商品sku")
    @QueryParam(value = "skuList")
    private Set<SkuMiniInfo> skuList;

    @ApiModelProperty(value = "商品图片")
    @QueryParam(value = "imgs")
    private LinkedHashSet<String> imgs = new LinkedHashSet<String>();

    /**
     * 运费
     */
    @ApiModelProperty(value = "运费")
    @QueryParam(value = "freight")
    private Integer freight;

    @ApiModel(value = "ItemSaveReq_Spec")
    public static class SpecDef {

        @ApiModelProperty(value = "商品规格的Id")
        @QueryParam(value = "itemPropId")
        private String itemPropId;
        @ApiModelProperty(value = "商品规格候选值的ID列表")
        @QueryParam(value = "itemPropValueIds")
        private Set<String> itemPropValueIds;

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

    public Set<String> getCategorys() {
        return categorys;
    }

    public void setCategorys(Set<String> categorys) {
        this.categorys = categorys;
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

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }
}
