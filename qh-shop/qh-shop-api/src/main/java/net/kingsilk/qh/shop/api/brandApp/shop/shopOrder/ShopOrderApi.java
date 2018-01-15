package net.kingsilk.qh.shop.api.brandApp.shop.shopOrder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.notify.dto.NotifyQhPayReq;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Api(
        tags = "staff",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "门店购买API"
)
@Path("/brandApp/{brandAppId}/shopOrder")
@Component
public interface ShopOrderApi {

    @ApiOperation(
            value = "创建订单",
            nickname = "创建订单",
            notes = "创建订单")
    @Path("/buy")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> buy(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @QueryParam(value = "shopId") String shopId,
            @QueryParam(value = "duration") Integer duration);

    @ApiOperation(
            value = "提交订单",
            nickname = "提交订单",
            notes = "提交订单")
    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> create(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId);


    @ApiOperation(
            value = "对订单进行支付",
            nickname = "对订单进行支付",
            notes = "对订单进行支付"
    )
    @Path("/notify")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    boolean notify(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId")
                    String shopId,
            NotifyQhPayReq req);

    @ApiOperation(
            value = "对订单进行支付",
            nickname = "对订单进行支付",
            notes = "对订单进行支付"
    )
    @Path("/notify/{shopId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    boolean notifyAgain(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId")
                    String shopId,
            NotifyQhPayReq req);
}
