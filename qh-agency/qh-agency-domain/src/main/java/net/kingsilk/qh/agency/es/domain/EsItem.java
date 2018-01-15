package net.kingsilk.qh.agency.es.domain;


import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.*;

/**
 *
 */
@Document(indexName = "qh-agency", type = "esItem", refreshInterval = "-1")
public class EsItem extends Base {


    private String itemId;
    /**
     * 所属品牌。
     */
    private String brandAppId;


    /**
     * 自定义编码
     */
    private String code;

    /**
     * 状态
     */
    private String statusCode;
    /**
     * 状态
     */
    private String statusDesp;


    /**
     * 商品规格列表。
     */
    private Map<String, List<String>> itemSpecs = new HashMap<>();


    // ----------------------------- 以下为可以被 SKU 覆盖的属性。
    /**
     * 标题
     */
    private String title;

    /**
     * 描述 (标题下面较长的文本)
     */
    private String desp;

    /**
     * 图片列表，第一张图片为主图 (请注意去除重复)
     */
    private ArrayList<String> imgs = new ArrayList<>();


    /**
     * 标签。由店铺任意指定
     */
    private Set<String> tags;

    /**
     * 图文详情
     */
    private String detail;

    /**
     * 商品单位
     */
    private String itemUnit;

    /**
     * 上架日期
     */
    @Field(type = FieldType.Date)
    private Date onSaleTime;

    private Integer skuMinSalePrice;

    private Map<String, Integer> tagPrice;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Map<String, List<String>> getItemSpecs() {
        return itemSpecs;
    }

    public void setItemSpecs(Map<String, List<String>> itemSpecs) {
        this.itemSpecs = itemSpecs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        this.imgs = imgs;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public Date getOnSaleTime() {
        return onSaleTime;
    }

    public void setOnSaleTime(Date onSaleTime) {
        this.onSaleTime = onSaleTime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getSkuMinSalePrice() {
        return skuMinSalePrice;
    }

    public void setSkuMinSalePrice(Integer skuMinSalePrice) {
        this.skuMinSalePrice = skuMinSalePrice;
    }

    public Map<String, Integer> getTagPrice() {
        return tagPrice;
    }

    public void setTagPrice(Map<String, Integer> tagPrice) {
        this.tagPrice = tagPrice;
    }
}
