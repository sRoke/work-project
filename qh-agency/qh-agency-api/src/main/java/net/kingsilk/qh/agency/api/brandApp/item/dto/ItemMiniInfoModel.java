package net.kingsilk.qh.agency.api.brandApp.item.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

/**
 * 精简版商品详情
 */
public class ItemMiniInfoModel {

    @ApiModelProperty(value = "商品id")
    private String itemId;
    @ApiModelProperty(value = "商品标题")
    private String title;
    @ApiModelProperty(value = "商品金额")
    private Integer price;
    @ApiModelProperty(value = "用户标签")
    private String partnerType;
    @ApiModelProperty(value = "单位")
    private String itemUnit;
    @ApiModelProperty(value = "商品描述")
    private String desp;
    @ApiModelProperty(value = "商品图片")
    private Set<String> imgs;
    @ApiModelProperty(value = "商品标签")
    private Set<String> tags;

    private Integer skuMinSalePrice;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public String getPartnerType() {
        return partnerType;
    }

    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public Set<String> getImgs() {
        return imgs;
    }

    public void setImgs(Set<String> imgs) {
        this.imgs = imgs;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Integer getSkuMinSalePrice() {
        return skuMinSalePrice;
    }

    public void setSkuMinSalePrice(Integer skuMinSalePrice) {
        this.skuMinSalePrice = skuMinSalePrice;
    }
}
