package net.kingsilk.qh.shop.api.brandApp.shop.mall.refund.dto;

import net.kingsilk.qh.shop.api.common.dto.SkuInfoModel;

import java.util.Map;

public class RefundSkuInfo {
    private SkuInfoModel skuInfo;

    private Map<String, String> refundTypeEnums;

    private String phone;

    public SkuInfoModel getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(SkuInfoModel skuInfo) {
        this.skuInfo = skuInfo;
    }

    public Map<String, String> getRefundTypeEnums() {
        return refundTypeEnums;
    }

    public void setRefundTypeEnums(Map<String, String> refundTypeEnums) {
        this.refundTypeEnums = refundTypeEnums;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
