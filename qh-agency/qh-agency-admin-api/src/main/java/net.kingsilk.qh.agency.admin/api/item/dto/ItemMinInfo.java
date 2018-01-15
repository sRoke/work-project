package net.kingsilk.qh.agency.admin.api.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import java.util.Set;

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
    private String imgs;
    /**
     * 名称
     */
    @ApiParam(value = "名称")
    private String title;
    /**
     * 品牌
     */
    @ApiParam(value = "品牌")
    private String brand;
    /**
     * 分类标签id
     */
    @ApiParam(value = "分类标签id")
    private String categoryId;
    /**
     * 分类标签名称
     */
    @ApiParam(value = "分类标签名称")
    private java.util.Set<String> categoryName = new java.util.HashSet<>();
    @ApiParam(value = "状态")
    private String statusCode;
    /**
     * 状态
     */
    @ApiParam(value = "状态描述")
    private String statusDesp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Set<String> getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(Set<String> categoryName) {
        this.categoryName = categoryName;
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
}
