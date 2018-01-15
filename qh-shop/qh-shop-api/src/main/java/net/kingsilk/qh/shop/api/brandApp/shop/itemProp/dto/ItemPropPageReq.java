package net.kingsilk.qh.shop.api.brandApp.shop.itemProp.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageReq;

import javax.ws.rs.QueryParam;

/**
 *
 */
@ApiModel(value = "商品属性分页请求信息")
public class ItemPropPageReq extends UniPageReq {


    @ApiParam(value = "属性名称")
    @QueryParam(value = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
