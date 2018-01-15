package net.kingsilk.qh.agency.domain;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类。
 * <p>
 * 确切讲，这里属于展示给用户看的菜单。
 */
@Document
public class Category extends Base {

    /**
     * 所属品牌商ID。
     */
    @Indexed
    private String brandAppId;

    /**
     * 父分类。
     */
    @DBRef
    private Category parent;

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
     * true - 已经禁用。
     */
    private boolean disabled;

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
}
