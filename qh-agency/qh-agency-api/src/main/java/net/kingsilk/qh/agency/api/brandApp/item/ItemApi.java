package net.kingsilk.qh.agency.api.brandApp.item;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.item.dto.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */

@Api(
        tags = "item",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "商品相关API"
)
@Path("/brandApp/{brandAppId}/item")
@Component
public interface ItemApi {
    //--------------------------------保存商品信息---------------------------------------//
    @ApiOperation(
            value = "保存商品信息",
            nickname = "保存商品信息",
            notes = "保存商品信息"
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ITEM_C')")
    UniResp<String> save(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            ItemSaveReq itemSaveReq);

    //--------------------------------商品信息详情---------------------------------------//
    @ApiOperation(
            value = "商品信息详情",
            nickname = "商品信息详情",
            notes = "商品信息详情"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ITEM_R')")
    UniResp<ItemInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "商品ID")
            @PathParam(value = "id") String id);

    //--------------------------------商品分页信息详情---------------------------------------//
    @ApiOperation(
            value = "商品分页信息详情",
            nickname = "商品分页信息详情",
            notes = "商品分页信息详情"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ITEM_R')")
    UniResp<UniPageResp<ItemMinInfo>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @BeanParam ItemPageReq itemPageReq);

    //--------------------------------删除商品---------------------------------------//
    @ApiOperation(
            value = "删除商品",
            nickname = "删除商品",
            notes = "删除商品"
    )
    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ITEM_D')")
    UniResp<String> delete(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "商品ID")
            @PathParam(value = "id") String id);

    //--------------------------------商品状态更改---------------------------------------//
    @ApiOperation(
            value = "商品状态更改",
            nickname = "商品状态更改",
            notes = "商品状态更改"
    )
    @Path("/{id}/changeStatus")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ITEM_U')")
    UniResp<String> changeStatus(

            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,

            @ApiParam(value = "商品ID")
            @PathParam(value = "id")
                    String id,

            @ApiParam(value = "商品状态")
            @QueryParam(value = "status")
                    String status
    );

    //--------------------------------更新商品信息---------------------------------------//
    @ApiOperation(
            value = "更新商品信息",
            nickname = "更新商品信息",
            notes = "更新商品信息"
    )
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ITEM_U')")
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "商品ID")
            @PathParam(value = "id") String id,
            ItemSaveReq itemSaveReq);

    @ApiOperation(
            value = "商品详情",
            nickname = "商品详情",
            notes = "商品详情"
    )
    @Path("/{id}/detail")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemInfoModel> detail(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "商品ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "查看类型类型", required = true)
            @QueryParam(value = "type") String type
    );

}
