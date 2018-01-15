package net.kingsilk.qh.agency.admin.controller.item

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import net.kingsilk.qh.agency.domain.Base

/**
 * Created by tpx on 17-3-17.
 */
@ApiModel(value = "ItemSaveSku")
class ItemSaveSku extends Base {

    String id;
    /**
     * 所属商品
     */

    String itemId;

    @ApiModelProperty(value = "加盟价")
    Integer leaguePrice
    @ApiModelProperty(value = "代理价")
    Integer agencyPrice
    @ApiModelProperty(value = "该SKU包含了哪些商品规格及其值")
    Set<SkuSpec> specList
    /**
     * 价格 (单位:分)
     */
    Integer price = 0;

    /**
     * 库存量
     */
    Integer storage = 0;

    /**
     * 上架日期
     */
    Date onSaleTime;


    @ApiModel(value = "ItemSaveReq_Sku_Spec")
    static class SkuSpec {
        @ApiModelProperty(value = "前端生成的 ObjectId")
        String id

        @ApiModelProperty(value = "商品规格的Id")
        String itemPropId

        @ApiModelProperty(value = "商品规格候选值的ID")
        String itemPropValueId
    }

//    /**
//     * 该 SKU 的规格信息
//     */
//    Set<ItemInfoResp.ItemSpecDef> specs = new LinkedHashSet<>();

//    /**
//     * 根据不同标签给不同打价格。
//     * <p>
//     * 标签可以随意定。当前是与 PartnerStaff 中的 tags 进行匹配。
//     * 如果匹配到多个标签，则使用最低价。
//     */
//    Set<Sku.TagPrice> tagPrices = new LinkedHashSet<>();

    // ----------------------------- 以下为可以被 SKU 覆盖的属性。

//    /**
//     * 使用到的商品属性、以及商品属性值。
//     * <p>
//     * XXX : 注意：该值如果被SKU覆盖，是需要将 item 中的该字段进行合并，但这里的优先级更高。
//     */
//    Set<ItemInfoResp.ItemUsedItemProp> props = new LinkedHashSet<>();

//    /**
//     * 标题
//     */
//    String title;
//
//    /**
//     * 描述 (标题下面较长的文本)
//     */
//    String desp;
//
//    /**
//     * 图片列表，第一张图片为主图 (请注意去除重复)
//     */
//    Set<String> imgs = new LinkedHashSet<>();
//
//    /**
//     * 标签。由店铺任意指定
//     */
//    Set<String> tags;
//
//    /**
//     * 图文详情
//     */
//    String detail;
//
//    /**
//     * 商品单位
//     */
//    String itemUnit;

//    Sku convertSkuReqToSku(Item item, Sku sku) {
//        sku.setItem(item);
//        sku.setLastModifiedDate(new Date());
//        if (imgs == null || imgs.size() == 0) {
//            sku.setImgs(item.getImgs());
//        } else {
//            sku.setImgs(imgs);
//        }
//        if (title == null) {
//            sku.setTitle(item.getTitle());
//        } else {
//            sku.setTitle(title);
//        }
//        if (desp == null) {
//            sku.setDesp(item.getDesp());
//        } else {
//            sku.setDesp(desp);
//        }
//        if (detail == null) {
//            sku.setDetail(item.getDetail());
//        } else {
//            sku.setDetail(detail);
//        }
//        sku
////        sku.setTagPrices(tagPrices);
//        sku.setPrice(price);
//        sku.setStorage(storage);
//        return sku;
//    }

//    static ItemSaveSku convertSkuToResp(Item item, Sku sku) {
//        ItemSaveSku itemSaveSku = new ItemSaveSku();
//        itemSaveSku.setTitle(sku.getTitle());
//        itemSaveSku.setDetail(sku.getDetail());
//        itemSaveSku.setDesp(sku.getDesp());
//        itemSaveSku.setId(sku.getId());
//        itemSaveSku.setPrice(sku.getPrice());
//        itemSaveSku.setTagPrices(sku.getTagPrices());
//        itemSaveSku.setTags(sku.getTags());
//        itemSaveSku.setStorage(sku.getStorage());
//        itemSaveSku.setItemUnit(sku.getItemUnit());
//        itemSaveSku.setItemId(item.getId());
//        itemSaveSku.setImgs(sku.getImgs())
//        itemSaveSku.setSpecs(ItemInfoResp.convertSkuSpecsToResp(sku.getSpecs()));
//        itemSaveSku.setProps(ItemInfoResp.convertItemPropsToResp(sku.getProps()));
//        return itemSaveSku;
//    }
}
