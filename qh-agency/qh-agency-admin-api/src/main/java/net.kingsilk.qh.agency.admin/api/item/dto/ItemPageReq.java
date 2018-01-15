package net.kingsilk.qh.agency.admin.api.item.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.common.dto.BasePageReq;
import net.kingsilk.qh.agency.admin.api.common.dto.BasePageResp;

import javax.ws.rs.QueryParam;

/**
 *
 */
@ApiModel(value = "商品分类分页请求信息")
public class ItemPageReq extends BasePageReq{

    @ApiParam(value = "商品名称")
    @QueryParam(value = "title")
    private java.lang.String title;
    @ApiParam(value = "状态")
    @QueryParam(value = "status")
    private java.lang.String status;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
