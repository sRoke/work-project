package net.kingsilk.qh.agency.api.brandApp.itemProp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageReq;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropModel;
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropPageReq;
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropSaveReq;
import net.kingsilk.qh.agency.api.brandApp.itemProp.dto.ItemPropValueModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api(
        tags = "itemProp",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "商品属性相关API"
)
@Path("/brandApp/{brandAppId}/itemProp")
@Component
public interface ItemPropApi {
    //--------------------------------商品属性分页信息---------------------------------------//
    @ApiOperation(
            value = "商品属性分页信息",
            nickname = "商品属性分页信息",
            notes = "商品属性分页信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ITEM_PROP_R')")
    UniResp<UniPageResp<ItemPropModel>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @BeanParam ItemPropPageReq itemPropPageReq);

    //--------------------------------保存商品属性信息---------------------------------------//
    @ApiOperation(
            value = "保存商品属性信息",
            nickname = "保存商品属性信息",
            notes = "保存商品属性信息"
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ITEM_PROP_C')")
    UniResp<String> save(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            ItemPropSaveReq itemPropSaveReq);

    //--------------------------------更新商品属性信息---------------------------------------//
    @ApiOperation(
            value = "更新商品属性信息",
            nickname = "更新商品属性信息",
            notes = "更新商品属性信息"
    )

    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ITEM_PROP_U')")
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @PathParam(value = "id") String id,
            ItemPropSaveReq itemPropSaveReq);

    //--------------------------------删除商品属性信息---------------------------------------//
    @ApiOperation(
            value = "删除商品属性信息",
            nickname = "删除商品属性信息",
            notes = "删除商品属性信息"
    )
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ITEM_PROP_D')")
    UniResp<String> delete(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "商品属性的ID", required = true)
            @PathParam(value = "id") String id);

    //--------------------------------商品属性信息详情---------------------------------------//
    @ApiOperation(
            value = "商品属性信息详情",
            nickname = "商品属性信息详情",
            notes = "商品属性信息详情"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ITEM_PROP_R')")
    UniResp<ItemPropModel> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "商品属性的ID", required = true)
            @PathParam(value = "id") String id);

    //----------------------------获取规格列表-------------------------------------------//
    @ApiOperation(
            value = "获取规格列表",
            nickname = "获取规格列表",
            notes = "获取规格列表"
    )
    @Path("/itemPropList")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<ItemPropModel>> list(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "要检索的关键字")
            @QueryParam(value = "itemPropKeyword") String itemPropKeyword,
            @BeanParam UniPageReq uniPageReq);

    @ApiOperation(
            value = "添加属性值",
            nickname = "添加属性值",
            notes = "添加属性值"
    )
    @Path("/{id}/propValue")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemPropValueModel> addPropValue(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "属性值")
            @QueryParam(value = "name") String name,
            @ApiParam(value = "商品ID")
            @PathParam(value = "id") String id);

    //-----------------------------获取规格值列表------------------------------------------//
    @ApiOperation(
            value = "获取规格值列表",
            nickname = "获取规格值列表",
            notes = "获取规格值列表"
    )
    @Path("/itemPropListItem")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<ItemPropValueModel>> itemPropListItem(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "要检索的关键字")
            @QueryParam(value = "itemPropKeyword") String itemPropKeyword,
            @BeanParam UniPageReq uniPageReq);

}
