package net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderItemInfo {

    private String skuId;
    private String skuImg;
    private String skuTitle;
    private String itemTitle;
    private Integer skuPrice;
    private Integer labelPrice;
    private Integer num;
    private String code;
    private Integer adjustPrice;
    private Integer realPayPrice;
    private Integer allRealPayPrice;
    private List<SpecInfo> specInfos = new ArrayList<SpecInfo>();

    private String refundId;
    /**
     * 仅仅发生退款时有效
     */
    private String refundStatus;

    /**
     *
     */
    private String refundStatusDesp;


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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
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

    public Integer getLabelPrice() {
        return labelPrice;
    }

    public void setLabelPrice(Integer labelPrice) {
        this.labelPrice = labelPrice;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatusDesp() {
        return refundStatusDesp;
    }

    public void setRefundStatusDesp(String refundStatusDesp) {
        this.refundStatusDesp = refundStatusDesp;
    }

    public Integer getAdjustPrice() {
        return adjustPrice;
    }

    public void setAdjustPrice(Integer adjustPrice) {
        this.adjustPrice = adjustPrice;
    }

    public Integer getRealPayPrice() {
        return realPayPrice;
    }

    public void setRealPayPrice(Integer realPayPrice) {
        this.realPayPrice = realPayPrice;
    }

    public Integer getAllRealPayPrice() {
        return allRealPayPrice;
    }

    public void setAllRealPayPrice(Integer allRealPayPrice) {
        this.allRealPayPrice = allRealPayPrice;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }
}
