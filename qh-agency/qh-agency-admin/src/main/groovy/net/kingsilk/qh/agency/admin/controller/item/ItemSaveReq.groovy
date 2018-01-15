package net.kingsilk.qh.agency.admin.controller.item

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import net.kingsilk.qh.agency.core.ItemStatusEnum

/**
 * Created by tpx on 17-3-17.
 */
@ApiModel(value = "ItemSaveReq")
class ItemSaveReq {
    @ApiModelProperty(value = "商品ID")
    String id;

    @ApiModelProperty(value = "商品图文描述")
    String detail;

    @ApiModelProperty(value = "商品编码")
    String code;

    @ApiModelProperty(value = "商品标题")
    String title;

    @ApiModelProperty(value = "描述（标题下面较长的文本）")
    String desp;


    @ApiModelProperty(value = "商品状态")
    ItemStatusEnum status;

    /**
     * 商品单位
     */
    @ApiModelProperty(value = "商品单位")
    String itemUnit;

    /**
     * 标签。由店铺任意指定
     */
    @ApiModelProperty(value = "标签。由店铺任意指定")
    Set<String> tags=new HashSet<>();

    @ApiModelProperty(value = "商品属性")
    Set<ItemProps> props; // TODO 仿照 ItemSaveReq.Spec 进行修改

    @ApiModelProperty(value = "商品规格以及候选值")
    Set<SpecDef> specs;

    @ApiModelProperty(value = "商品sku")
    Set<ItemSku> skuList;

    @ApiModelProperty(value = "商品图片")
    Set<String> imgs = new HashSet<>()

    // 对应 Item.SpecDef
    @ApiModel(value = "ItemSaveReq_Props")
    static class ItemProps  {

        @ApiModelProperty(value = "前端生成的 ObjectId")
        String id

        @ApiModelProperty(value = "商品属性的Id")
        String itemPropId

        @ApiModelProperty(value = "商品属性候选值的ID列表")
        String itemPropValueId
    }

    @ApiModel(value = "ItemSaveReq_Spec")
    static class SpecDef {

        @ApiModelProperty(value = "前端生成的 ObjectId")
        String id

        @ApiModelProperty(value = "商品规格的Id")
        String itemPropId

        @ApiModelProperty(value = "商品规格候选值的ID列表")
        Set<String> itemPropValueIds
    }

    @ApiModel(value = "ItemSaveReq_Sku")
    static class ItemSku {

        @ApiModelProperty(value = "前端生成的 ObjectId")
        String id

        @ApiModelProperty(value = "该SKU包含了哪些商品规格及其值")
        Set<SkuSpec> specList
        @ApiModelProperty(value = "加盟价")
        Double leaguePrice
        @ApiModelProperty(value = "代理价")
        Double agencyPrice
        @ApiModelProperty(value = "原价")
        Double price
        @ApiModelProperty(value = "库存")
        Integer storage
        @ApiModelProperty(value = "状态")
        String status

        @ApiModel(value = "ItemSaveReq_Sku_Spec")
        static class SkuSpec {
            @ApiModelProperty(value = "前端生成的 ObjectId")
            String id

            @ApiModelProperty(value = "商品规格的Id")
            String itemPropId

            @ApiModelProperty(value = "商品规格候选值的ID")
            String itemPropValueId
        }
    }


}

