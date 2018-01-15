package net.kingsilk.qh.shop.api.brandApp.shop.mall.home;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Api
@Path("/brandApp/{brandAppId}/shop/{shopId}/home")
@Singleton
public interface HomeApi {


    @ApiOperation(
            value = "门店名字"
    )
    @Path("/getName")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<String> get(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "shopId") String shopId
    );

    @ApiOperation(
            value = "门店详情",
            nickname = "门店详情",
            notes = "门店详情"
    )
    @Path("/getNum")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Map<String, Long>> getShopOrderNum(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "shopId") String shopId
    );
}