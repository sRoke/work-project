package net.kingsilk.qh.agency.api.brandApp.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.ws.rs.QueryParam;

/**
 *
 */
@Deprecated
@ApiModel(value = "sku保存请求")
public class ItemSaveSku {
    @ApiModelProperty(value = "id")
    @QueryParam(value = "id")
    private java.lang.String id;
    /**
     * 所属商品
     */
    @ApiModelProperty(value = "所属商品")
    @QueryParam(value = "itemId")
    private java.lang.String itemId;
    @ApiModelProperty(value = "加盟价")
    @QueryParam(value = "leaguePrice")
    private java.lang.Integer leaguePrice;
    @ApiModelProperty(value = "代理价")
    @QueryParam(value = "agencyPrice")
    private java.lang.Integer agencyPrice;
    @ApiModelProperty(value = "该SKU包含了哪些商品规格及其值")
    @QueryParam(value = "specList")
    private java.util.Set<ItemSaveSku.SkuSpec> specList;
    /**
     * 价格 (单位:分)
     */
    @ApiModelProperty(value = "价格")
    @QueryParam(value = "price")
    private java.lang.Integer price = 0;
    /**
     * 库存量
     */
    @ApiModelProperty(value = "库存量")
    @QueryParam(value = "storage")
    private java.lang.Integer storage = 0;
    /**
     * 上架日期
     */
    @ApiModelProperty(value = "上架日期")
    @QueryParam(value = "onSaleTime")
    private java.util.Date onSaleTime;

    @ApiModel(value = "ItemSaveReq_Sku_Spec")
    public static class SkuSpec {

        @ApiModelProperty(value = "前端生成的 ObjectId")
        @QueryParam(value = "id")
        private java.lang.String id;
        @ApiModelProperty(value = "商品规格的Id")
        @QueryParam(value = "itemPropId")
        private java.lang.String itemPropId;
        @ApiModelProperty(value = "商品规格候选值的ID")
        @QueryParam(value = "itemPropValueId")
        private java.lang.String itemPropValueId;
    }
}
