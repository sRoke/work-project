package net.kingsilk.qh.agency.api.brandApp.category.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 *
 */
public class CategoryInfoResp {
    /**
     * 分类id
     */
    @ApiParam(value = "分类id", defaultValue = "true")
    @ApiModelProperty(value = "分类id")
    private String id;


    /**
     * 所属公司。
     */
    @ApiParam(value = "所属公司", defaultValue = "true")
    @ApiModelProperty(value = "所属公司")
    private String brandAppId;

    /**
     * 父分类。
     */
    @ApiParam(value = "父分类", defaultValue = "true")
    @ApiModelProperty(value = "父分类")
    private String parentId;

    /**
     * 类别名称
     */
    @ApiParam(value = "类别名称", defaultValue = "true")
    @ApiModelProperty(value = "类别名称")
    private String name;

    /**
     * 类别排序
     */
    @ApiParam(value = "类别排序", defaultValue = "true")
    @ApiModelProperty(value = "类别排序")
    private Integer order;

    /**
     * 描述
     */
    @ApiParam(value = "描述", defaultValue = "true")
    @ApiModelProperty(value = "描述")
    private String desp;

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
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

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
