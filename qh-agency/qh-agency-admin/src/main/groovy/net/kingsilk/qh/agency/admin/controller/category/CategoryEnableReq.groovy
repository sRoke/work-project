package net.kingsilk.qh.agency.admin.controller.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "CategoryEnableReq")
public class CategoryEnableReq {

    @ApiParam(required = true, value = "商品分类的ID")
    private String id;

    @ApiParam(required = true, value = "false:正常;true:禁用")
    private boolean disabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
