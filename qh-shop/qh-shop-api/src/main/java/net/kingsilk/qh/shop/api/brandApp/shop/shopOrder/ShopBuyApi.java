package net.kingsilk.qh.shop.api.brandApp.shop.shopOrder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopOrder.dto.ShopOrderResp;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(
        tags = "staff",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "门店购买API"
)
@Path("/brandApp/{brandAppId}/shop/{shopId}/shopOrder")
@Component
@Singleton
public interface ShopBuyApi {

    @ApiOperation(
            value = "创建订单",
            nickname = "创建订单",
            notes = "创建订单")
    @Path("/buy")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> buyAgain(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @PathParam(value = "shopId") String shopId,
            @QueryParam(value = "duration") Integer duration);

    @ApiOperation(
            value = "创建订单",
            nickname = "创建订单",
            notes = "创建订单")
    @Path("/log")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<List<ShopOrderResp>> log(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @PathParam(value = "shopId") String shopId);

}
