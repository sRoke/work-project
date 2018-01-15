package net.kingsilk.qh.agency.wap.api.item.dto;

import java.math.BigDecimal;
import java.util.Set;

/**
 * sku精简详情
 */
public class SkuMiniInfoModel {
//    public void convert(Sku sku, final String[] tags) {
//
//        TagPrice minTag = sku.tagPrices.invokeMethod("findAll", new Object[]{new Closure(this, this) {
//            public Object doCall(TagPrice it) {
//                return tags.invokeMethod("contains", new Object[]{it.tag.code});
//            }
//
//        }}).invokeMethod("min", new Object[]{new Closure(this, this) {
//            public Object doCall(TagPrice it) {
//                return it.price;
//            }
//
//        }});
//        this.price = ((int) (minTag.price));
//        this.curTag = ((String) (minTag.tag.code));
//
////        List<Sku.TagPrice> curTaglist = []
////        Set<Sku.TagPrice> skuTagPrices = sku.tagPrices
////        skuTagPrices.each { Sku.TagPrice it ->
////            if (tags.contains(it.tag.code)) {
////                curTaglist.add(it)
////            }
////        }
////
////        //TODO 以下方法以后再研究
////        Sku.TagPrice curTag = curTaglist.min { Sku.TagPrice it ->
////            it.price
////        }
////        this.price = curTag.price
////        this.curTag = curTag.tag
////
////        int minPrice = Integer.MAX_VALUE;
////        String minPriceTag = "null"
////        curTaglist.each { Sku.TagPrice it ->
////            if (it.price < minPrice) {
////                minPrice = it.price
////                minPriceTag = it.tag.code
////            }
////        }
////        this.price = minPrice
////        this.curTag = minPriceTag
//
//        this.skuId = ((String) (sku.id));
//        //this.seq = sku.seq
//        final Object item = sku.item;
//        this.title = ((String) ((item == null ? null : item.title)));
////        this.storage = sku.storage
//        final Object item1 = sku.item;
//        this.imgs = ((Set<String>) ((item1 == null ? null : item1.imgs)));
//        //this.description
//    }


    /**
     * id
     */
    private String skuId;
    /**
     * 标题
     */
    private String title;
    /**
     * 价格，根据用户身份取不同的值
     */
    private BigDecimal price;
    /**
     * 当前价格类型
     */
    private String curTag;
    /**
     * 库存
     */
    private int storage;
    /**
     * 图片
     */
    private Set<String> imgs;


    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurTag() {
        return curTag;
    }

    public void setCurTag(String curTag) {
        this.curTag = curTag;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public Set<String> getImgs() {
        return imgs;
    }

    public void setImgs(Set<String> imgs) {
        this.imgs = imgs;
    }
}
