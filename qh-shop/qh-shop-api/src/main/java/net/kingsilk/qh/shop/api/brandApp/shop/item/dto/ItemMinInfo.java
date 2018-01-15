package net.kingsilk.qh.shop.api.brandApp.shop.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
@ApiModel(value = "商品部分信息")
public class ItemMinInfo {
    @ApiParam(value = "id")
    private String id;
    /**
     * 主图
     */
    @ApiParam(value = "主图")
    private LinkedHashSet<String> imgs;
    /**
     * 名称
     */
    @ApiParam(value = "名称")
    private String title;

    @ApiParam(value = "商品描述")
    private String desp;
    /**
     * 品牌
     */
    @ApiParam(value = "品牌")
    private String brandAppId;

    @ApiParam(value = "商品金额")
    private Integer price;
    @ApiParam(value = "用户标签")
    private String partnerType;
    /**
     * 分类标签id
     */
    @ApiParam(value = "分类标签id")
    private String categoryId;


    /**
     * 分类标签名称
     */
    @ApiParam(value = "分类标签名称")
    private Set<String> categorys = new java.util.HashSet<>();

    private Set<String> categoryNames = new java.util.HashSet<>();
    @ApiParam(value = "状态")
    private String statusCode;
    /**
     * 状态
     */
    @ApiParam(value = "状态描述")
    private String statusDesp;

    @ApiParam(value = "商品标签")
    private Set<String> tags;

    @ApiParam(value = "商品创建时间")
    private String dateCreated;

    private Integer skuMinSalePrice;

    private Integer salePrice;

    private Integer buyPrice;

    private Map<String, String> specs = new HashMap<>();


    private Integer store;

    private Integer salesVolume;

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LinkedHashSet<String> getImgs() {
        return imgs;
    }

    public void setImgs(LinkedHashSet<String> imgs) {
        this.imgs = imgs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Set<String> getCategorys() {
        return categorys;
    }

    public void setCategorys(Set<String> categorys) {
        this.categorys = categorys;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDesp() {
        return statusDesp;
    }

    public void setStatusDesp(String statusDesp) {
        this.statusDesp = statusDesp;
    }

    public Integer getSkuMinSalePrice() {
        return skuMinSalePrice;
    }

    public void setSkuMinSalePrice(Integer skuMinSalePrice) {
        this.skuMinSalePrice = skuMinSalePrice;
    }

    public Map<String, String> getSpecs() {
        return specs;
    }

    public void setSpecs(Map<String, String> specs) {
        this.specs = specs;
    }

    public Integer getStore() {
        return store;
    }

    public void setStore(Integer store) {
        this.store = store;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateCreated = sdf.format(dateCreated);
    }

    public Integer getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Integer buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    public Set<String> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(Set<String> categoryNames) {
        this.categoryNames = categoryNames;
    }
}
