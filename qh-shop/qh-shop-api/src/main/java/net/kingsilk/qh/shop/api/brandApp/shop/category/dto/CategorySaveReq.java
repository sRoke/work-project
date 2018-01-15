package net.kingsilk.qh.shop.api.brandApp.shop.category.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "CategorySaveReq")
public class CategorySaveReq {

    @ApiParam(value = "分类名称")
    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiParam(value = "描述")
    @ApiModelProperty(value = "描述")
    private String desp;


    @ApiParam(value = "排序")
    @ApiModelProperty(value = "排序")
    private Integer order;

    @ApiParam(value = "父级分类Id")
    @ApiModelProperty(value = "父级分类id")
    private String parentId;

    @ApiParam(value = "是否禁用")
    @ApiModelProperty(value = "是否禁用")
    private boolean enable;


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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

