package net.kingsilk.qh.shop.api.brandApp.shop.supplier.dto;

import java.util.Date;

/**
 * 供应商
 */
public class SupplierModel {


    private String supplierId;
    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;

    /**
     * 是否禁用
     */
    private Boolean enable;

    /**
     * 供应商名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 详细地址
     */
    private String addrDesp;

    /**
     * 所属地区id
     */
    private String adcId;

    /**
     * 所属地区
     */
    private String adc;

    /**
     * 备注
     */
    private String memo;

    /**
     *
     */
    private Date dateCreated;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddrDesp() {
        return addrDesp;
    }

    public void setAddrDesp(String addrDesp) {
        this.addrDesp = addrDesp;
    }

    public String getAdcId() {
        return adcId;
    }

    public void setAdcId(String adcId) {
        this.adcId = adcId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAdc() {
        return adc;
    }

    public void setAdc(String adc) {
        this.adc = adc;
    }
}
