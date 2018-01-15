package net.kingsilk.qh.shop.api.controller.order.dto;

import java.util.ArrayList;

/**
 * sku精简详情
 */
public class SkuMiniInfo {

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
