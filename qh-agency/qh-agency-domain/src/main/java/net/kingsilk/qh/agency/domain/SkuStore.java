package net.kingsilk.qh.agency.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 各渠道商各sku库存表
 */
@Document
public class SkuStore extends Base {

    /**
     * 所属品牌
     */
    private String brandAppId;

    /**
     * 所属渠道商
     */
    @DBRef
    private Partner partner;

    /**
     * SKU
     */
    @DBRef
    private Sku sku;

    /**
     * 库存量
     */
    private Integer num;

    /**
     * 销量
     */
    private Integer salesVolume = 0;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }
}
