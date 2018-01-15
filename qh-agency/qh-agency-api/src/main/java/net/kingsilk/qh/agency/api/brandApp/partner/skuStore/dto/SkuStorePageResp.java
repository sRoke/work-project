package net.kingsilk.qh.agency.api.brandApp.partner.skuStore.dto;

import net.kingsilk.qh.agency.api.common.dto.SkuInfoModel;

import java.util.Date;

/**
 *
 */
public class SkuStorePageResp {

    /**
     * 库存ID
     */
    private String skuStoreId;
    /**
     * 库存SKU
     */
    private SkuInfoModel skuInfo;

    /**
     * 库存Sku的数量
     */
    private int num;

    /**
     *库存创建时间
     */
    private Date dateCreated;

    /***
     * 销量
     */
    private int salesNum;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getSkuStoreId() {
        return skuStoreId;
    }

    public void setSkuStoreId(String skuStoreId) {
        this.skuStoreId = skuStoreId;
    }

    public SkuInfoModel getSkuInfo() {
        return skuInfo;
    }

    public void setSkuInfo(SkuInfoModel skuInfo) {
        this.skuInfo = skuInfo;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(int salesNum) {
        this.salesNum = salesNum;
    }
}
