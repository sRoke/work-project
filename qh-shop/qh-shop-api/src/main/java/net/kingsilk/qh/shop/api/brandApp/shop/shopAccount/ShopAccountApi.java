package net.kingsilk.qh.shop.api.brandApp.shop.shopAccount;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageReq;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopAccount.dto.ShopAccountInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopAccount.dto.ShopAccountPageResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Api(
        tags = "shop",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "店铺账户相关API"
)
@Component
@Path("/brandApp/{brandAppId}/shop/{shopId}/shopAccount")
public interface ShopAccountApi {

    @ApiOperation(
            value = "店铺账户信息",
            nickname = "店铺账户信息",
            notes = "店铺账户信息"
    )
    @Path("")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<ShopAccountInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "shopId") String shopId
    );


    @ApiOperation(
            value = "店铺账户信息",
            nickname = "店铺账户信息",
            notes = "店铺账户信息"
    )
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<ShopAccountPageResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "shopId") String shopId,
            @BeanParam UniPageReq req
    );

    @ApiOperation(
            value = "店铺账户信息",
            nickname = "店铺账户信息",
            notes = "店铺账户信息"
    )
    @Path("/transfer")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Boolean> transfer(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "品牌商ID")
            @QueryParam(value = "money") Integer money,
            @ApiParam(value = "品牌商ID")
            @QueryParam(value = "orderId") String orderId
    );

}
