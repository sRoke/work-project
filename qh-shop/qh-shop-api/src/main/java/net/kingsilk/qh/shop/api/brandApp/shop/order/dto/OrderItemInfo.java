package net.kingsilk.qh.shop.api.brandApp.shop.order.dto;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class OrderItemInfo {

    private LinkedHashSet<String> skuImg;
    private String skuTitle;
    private String itemTitle;
    private Integer skuPrice;
    private Integer adjustPrice;
    private Integer realPayPrice;
    private Integer num;
    private String code;
    private String skuId;
    private Integer labelPrice;
    private List<SpecInfo> specInfos = new ArrayList<SpecInfo>();

    /**
     * 仅仅发生退款时有效
     */
    private String refundStatus;

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

    public LinkedHashSet<String> getSkuImg() {
        return skuImg;
    }

    public void setSkuImg(LinkedHashSet<String> skuImg) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<SpecInfo> getSpecInfos() {
        return specInfos;
    }

    public void setSpecInfos(List<SpecInfo> specInfos) {
        this.specInfos = specInfos;
    }

    public Integer getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(Integer adjustPrice) {
        this.adjustPrice = adjustPrice;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getLabelPrice() {
        return labelPrice;
    }

    public void setLabelPrice(Integer labelPrice) {
        this.labelPrice = labelPrice;
    }

    public Integer getRealPayPrice() {
        return realPayPrice;
    }

    public void setRealPayPrice(Integer realPayPrice) {
        this.realPayPrice = realPayPrice;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }
}
