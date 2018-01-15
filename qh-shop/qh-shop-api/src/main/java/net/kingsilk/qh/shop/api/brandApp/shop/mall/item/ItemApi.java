package net.kingsilk.qh.shop.api.brandApp.shop.mall.item;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemMinInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.item.dto.ItemPageReq;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.item.dto.ItemInfoModel;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Api(
        tags = "ItemApi",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "顾客访问的商品列表"
)
@Path("/brandApp/{brandAppId}/shop/{shopId}/mall/item")
public interface ItemApi {

    @ApiOperation(
            value = "商品信息详情",
            nickname = "商品信息详情",
            notes = "商品信息详情"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")
    UniResp<ItemInfoResp> info(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "商品ID")
            @PathParam(value = "id") String id);

    @ApiOperation(
            value = "商品分页信息详情",
            nickname = "商品分页信息详情",
            notes = "商品分页信息详情"
    )

    @Path("")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")  //todo
    UniResp<UniPageResp<ItemMinInfo>> page(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @BeanParam ItemPageReq itemPageReq);

    @Path("/search")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<ItemMinInfo>> search(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @BeanParam ItemPageReq itemPageReq);
}
