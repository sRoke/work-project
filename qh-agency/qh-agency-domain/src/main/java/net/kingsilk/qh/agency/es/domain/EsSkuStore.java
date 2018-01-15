package net.kingsilk.qh.agency.es.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Document(indexName = "qh-agency", type = "esSkuStore", refreshInterval = "-1")
public class EsSkuStore extends Base {

    private String skuStoreId;
    /**
     * 所属品牌
     */
    private String brandAppId;

    /**
     * 渠道商id
     */
    private String partnerId;

    /**
     * 库存量
     */
    private Integer num;

    /**
     * 库存量
     */
    private Integer salesVolume;


    /**
     * 渠道商负责人的用户ID。
     */
    private String userId;

    /**
     * 渠道商类型
     */
    private String partnerTypeEnum;

    /**
     * 上级渠道商Id
     */
    private String parentPartnerId;

    /**
     * 店铺名称
     */
    private String shopName;


    /**
     * 商品ID
     */
    private String itemId;

    /**
     * skuID
     */
    private String skuId;

    /**
     * Sku规格列表。
     */
    private Map<String, String> specs = new HashMap<>();

    /**
     * 吊牌价 (单位:分)
     */
    private Integer labelPrice = 0;

    /**
     * 状态
     */
    private String status;

    /**
     * 销售价
     */
    private Integer salePrice;

    /**
     * 自定义编码
     */
    private String code;

    /**
     * 根据不同标签给不同打价格
     */
    private Map<String, Integer> tagPrices = new HashMap<>();

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

    private Integer skuMinSalePrice;
//
//    /**
//     * 图文详情
//     */
//    private String detail;
//
//    /**
//     * 商品单位
//     */
//    private String itemUnit;
//
//    /**
//     * 上架日期
//     */
//    private Date onSaleTime;


    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPartnerTypeEnum() {
        return partnerTypeEnum;
    }

    public void setPartnerTypeEnum(String partnerTypeEnum) {
        this.partnerTypeEnum = partnerTypeEnum;
    }

    public String getParentPartnerId() {
        return parentPartnerId;
    }

    public void setParentPartnerId(String parentPartnerId) {
        this.parentPartnerId = parentPartnerId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getLabelPrice() {
        return labelPrice;
    }

    public void setLabelPrice(Integer labelPrice) {
        this.labelPrice = labelPrice;
    }

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Map<String, Integer> getTagPrices() {
        return tagPrices;
    }

    public void setTagPrices(Map<String, Integer> tagPrices) {
        this.tagPrices = tagPrices;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSkuMinSalePrice() {
        return skuMinSalePrice;
    }

    public void setSkuMinSalePrice(Integer skuMinSalePrice) {
        this.skuMinSalePrice = skuMinSalePrice;
    }

    public String getSkuStoreId() {
        return skuStoreId;
    }

    public void setSkuStoreId(String skuStoreId) {
        this.skuStoreId = skuStoreId;
    }

    public Map<String, String> getSpecs() {
        return specs;
    }

    public void setSpecs(Map<String, String> specs) {
        this.specs = specs;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }
}
