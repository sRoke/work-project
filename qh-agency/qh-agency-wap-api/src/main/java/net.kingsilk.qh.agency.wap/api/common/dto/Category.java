package net.kingsilk.qh.agency.wap.api.common.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类。
 * <p>
 * 确切讲，这里属于展示给用户看的菜单。
 */
public class Category extends Base {

    /**
     * 所属公司。
     */
    private String brandId;


    /**
     * 父分类。
     */
    private Category parent;

    /**
     * 类别名称
     */
    private String name;

    /**
     * 图片网址
     */
    private String img;

    /**
     * 前端展示用的图标名称
     */
    private String icon;

    /**
     * 顺序。 数值越小，排序越靠前/上。
     */
    private Integer order = 0;

    /**
     * 是否已经禁用。
     * <p>
     * true - 已经禁用。
     */
    private boolean disabled;

    /**
     * 描述
     */
    private String desp;

    /**
     * 该分类下包含的商品列表。
     */
    List<Item> items = new ArrayList<>();


    // --------------------------------------- getter && setter


    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
