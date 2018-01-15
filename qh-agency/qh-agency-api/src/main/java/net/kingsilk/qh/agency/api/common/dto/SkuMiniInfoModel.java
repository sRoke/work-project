package net.kingsilk.qh.agency.api.common.dto;

import java.util.ArrayList;

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
    private Integer price;

    //"加盟价"
    private Integer leaguePrice;
    //总代价
    private Integer generalAgencyPrice;
    // "市代价"
    private Integer regionalAgencyPrice;
    /**
     * 吊牌价 (单位:分)
     */
    private Integer labelPrice;

    /**
     * 销售价
     */
    private Integer salePrice;

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
    private ArrayList<String> imgs;

    public Integer getLabelPrice() {
        return labelPrice;
    }

    public void setLabelPrice(Integer labelPrice) {
        this.labelPrice = labelPrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }
}
