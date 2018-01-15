package net.kingsilk.qh.shop.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 分类。
 * <p>
 * 确切讲，这里属于展示给用户看的菜单。
 */
@Document
public class Category extends Base {

    /**
     * 应用的Id。
     */
    private String brandAppId;

    /**
     * 门店的ID
     */
    private String shopId;
    /**
     * 父分类Id。
     */
    private String parentId;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 顺序。 数值越小，排序越靠前/上。
     */
    private Integer order = 0;

    /**
     * 是否已经禁用。
     * <p>
     * true - 已经启用。
     */
    private boolean enable =true;

    /**
     * 描述
     */
    private String desp;

    // --------------------------------------- getter && setter

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }
}
