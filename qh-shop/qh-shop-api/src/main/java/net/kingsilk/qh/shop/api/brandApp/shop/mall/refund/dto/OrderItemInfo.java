package net.kingsilk.qh.shop.api.brandApp.shop.mall.refund.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class OrderItemInfo {

    private String skuImg;
    private String skuTitle;
    private String itemTitle;
    private Integer skuPrice;
    private Integer num;
    private String code;
    private List<SpecInfo> specInfos = new ArrayList<SpecInfo>();

    public static class SpecInfo {

        private String propName;
        private String propValue;

        public String getPropName() {
            return propName;
        }

        public void setPropName(String propName) {
            this.propName = propName;
        }

        public String getPropValue() {
            return propValue;
        }

        public void setPropValue(String propValue) {
            this.propValue = propValue;
        }
    }

    public String getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(String skuImg) {
        this.skuImg = skuImg;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public Integer getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(Integer skuPrice) {
        this.skuPrice = skuPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<SpecInfo> getSpecInfos() {
        return specInfos;
    }

    public void setSpecInfos(List<SpecInfo> specInfos) {
        this.specInfos = specInfos;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}