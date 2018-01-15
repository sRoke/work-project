package net.kingsilk.qh.agency.wap.controller.item.model

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.domain.Sku

/**
 * sku精简详情
 */
@CompileStatic
class SkuMiniInfoModel {
    /**
     * id
     */
    String skuId;

    /**
     * 编号
     */
    //Long seq;

    /**
     * 标题
     */
    String title;

    /**
     * 价格，根据用户身份取不同的值
     */
    int price;

    /**
     * 当前价格类型
     */
    String curTag;

    /**
     * 库存
     */
    int storage;

    /**
     * 描述
     */
    //String description

    /**
     * 图片
     */
    Set<String> imgs

    //@CompileStatic(value = TypeCheckingMode.SKIP)
    void convert(Sku sku, String[] tags) {

        Sku.TagPrice minTag = sku.tagPrices.findAll { Sku.TagPrice it ->
            return tags.contains(it.tag.code)
        }.min { Sku.TagPrice it ->
            it.price
        }
        this.price = minTag.price
        this.curTag = minTag.tag.code

//        List<Sku.TagPrice> curTaglist = []
//        Set<Sku.TagPrice> skuTagPrices = sku.tagPrices
//        skuTagPrices.each { Sku.TagPrice it ->
//            if (tags.contains(it.tag.code)) {
//                curTaglist.add(it)
//            }
//        }
//
//        //TODO 以下方法以后再研究
//        Sku.TagPrice curTag = curTaglist.min { Sku.TagPrice it ->
//            it.price
//        }
//        this.price = curTag.price
//        this.curTag = curTag.tag
//
//        int minPrice = Integer.MAX_VALUE;
//        String minPriceTag = "null"
//        curTaglist.each { Sku.TagPrice it ->
//            if (it.price < minPrice) {
//                minPrice = it.price
//                minPriceTag = it.tag.code
//            }
//        }
//        this.price = minPrice
//        this.curTag = minPriceTag

        this.skuId = sku.id
        //this.seq = sku.seq
        this.title = sku.item?.title
//        this.storage = sku.storage
        this.imgs = sku.item?.imgs
        //this.description
    }
}
