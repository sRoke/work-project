package net.kingsilk.qh.shop.api.brandApp.shop.mall.pay;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Api(
        tags = "pay",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "支付相关接口"
)
@Path("/brandApp/{brandAppId}/shop/{shopId}/pay")
@Singleton
public interface PayApi {


    @ApiOperation(
            value = "对订单进行支付",
            nickname = "对订单进行支付",
            notes = "对订单进行支付"
    )
    @Path("/{orderId}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> pay(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "orderId") String orderId,
            @QueryParam(value = "memo") String memo);
}
