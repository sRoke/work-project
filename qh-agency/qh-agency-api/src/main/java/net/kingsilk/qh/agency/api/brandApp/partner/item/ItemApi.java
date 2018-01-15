package net.kingsilk.qh.agency.api.brandApp.partner.item;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.item.dto.*;
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
@Path("/brandApp/{brandAppId}/partner/{partnerId}/item")
@Component
public interface ItemApi {

    //----------------------------搜索商品-------------------------------//
    @ApiOperation(
            value = "搜索商品",
            nickname = "搜索商品",
            notes = "搜索商品"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<ItemMinInfo>> searchItem(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam ItemSearchReq req);

    @ApiOperation(
            value = "商品详情",
            nickname = "商品详情",
            notes = "商品详情"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ItemInfoModel> detail(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "商品ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "查看类型类型", required = true)
            @QueryParam(value = "type") String type
    );
}
