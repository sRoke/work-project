package net.kingsilk.qh.agency.admin.api.itemProp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.UniResp;
import net.kingsilk.qh.agency.admin.api.itemProp.dto.*;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api(
        tags = "itemProp",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "商品属性相关API"
)
@Path("/itemProp")
@Component
public interface ItemPropApi {
    @ApiOperation(
            value = "商品属性分页信息",
            nickname = "商品属性分页信息",
            notes = "商品属性分页信息"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemPropPageResp> page(ItemPropPageReq itemPropPageReq);

    @ApiOperation(
            value = "保存商品属性信息",
            nickname = "保存商品属性信息",
            notes = "保存商品属性信息"
    )
    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(ItemPropSaveReq itemPropSaveReq);

    @ApiOperation(
            value = "删除商品属性信息",
            nickname = "删除商品属性信息",
            notes = "删除商品属性信息"
    )
    @Path("/delete")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delete(@ApiParam(value = "商品属性的ID", required = true)
                           @PathParam(value = "id")
                                   String id);

    @ApiOperation(
            value = "商品属性信息详情",
            nickname = "商品属性信息详情",
            notes = "商品属性信息详情"
    )
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemPropInfoResp> info(@ApiParam(value = "商品属性的ID", required = true)
                                   @PathParam(value = "id")
                                           String id);

    @ApiOperation(
            value = "获取模糊匹配用户列表",
            nickname = "获取模糊匹配用户列表",
            notes = "获取模糊匹配用户列表"
    )
    @Path("/itemPropList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemPropListResp> list(@BeanParam ItemPropListReq req);

    @ApiOperation(
            value = "获取模糊匹配用户列表",
            nickname = "获取模糊匹配用户列表",
            notes = "获取模糊匹配用户列表"
    )
    @Path("/itemPropListItem")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemPropValueListResp> itemPropListItem( ItemPropListReq req);

}
