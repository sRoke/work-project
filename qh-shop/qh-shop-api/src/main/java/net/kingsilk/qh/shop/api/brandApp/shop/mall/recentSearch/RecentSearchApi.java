package net.kingsilk.qh.shop.api.brandApp.shop.mall.recentSearch;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.recentSearch.dto.RecentSearchResp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 最近搜索api
 */
@Path("/brandApp/{brandAppId}/shop/{shopId}/mall/recentSearch")
public interface RecentSearchApi {
    @ApiOperation(
            value = "商品信息详情",
            nickname = "商品信息详情",
            notes = "商品信息详情"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")
    UniResp<RecentSearchResp> page(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId);


    @ApiOperation(
            value = "清空最近删除",
            nickname = "清空最近删除",
            notes = "清空最近删除"
    )
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> delete(
            @ApiParam(value = "brandAppId")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId);

}
