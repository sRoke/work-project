package net.kingsilk.qh.shop.api.brandApp.shop.mall.cart;

import io.swagger.annotations.*;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.cart.dto.CartItemInfo;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.cart.dto.CartNumResp;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Api(
        tags = "cart",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "购物车相关api")
@Singleton
@Path("/brandApp/{brandAppId}/shop/{shopId}/cart")
public interface CartApi {

    @ApiOperation(
            value = "加入购物车",
            nickname = "加入购物车",
            notes = "加入购物车"
    )
    @PUT
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<String> add(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "加入购物车的SKU的ID", required = true)
            @QueryParam(value = "skuId") String skuId,
            @ApiParam(value = "加入购物车的购物车的ID", required = false)
            @QueryParam(value = "cartId") String cartId,
            @ApiParam(value = "加入购物车的SKU的数量", required = true)
            @QueryParam(value = "num") int num,
            @ApiParam(value = "购物车类型", required = true)
            @QueryParam(value = "type") String type
    );

    @ApiOperation(
            value = "加入购物车",
            nickname = "加入购物车",
            notes = "加入购物车"
    )
    @PUT
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<String> update(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "加入购物车的SKU的ID", required = true)
            @QueryParam(value = "skuId") String skuId,
            @ApiParam(value = "加入购物车的购物车的ID", required = false)
            @QueryParam(value = "cartId") String cartId,
            @ApiParam(value = "加入购物车的SKU的数量", required = true)
            @QueryParam(value = "num") int num,
            @ApiParam(value = "购物车类型", required = true)
            @QueryParam(value = "type") String type
    );

    //--------------------------------购物车商品列表---------------------------------------//
    @ApiOperation(
            value = "购物车商品列表",
            nickname = "购物车商品列表",
            notes = "购物车商品列表"
    )
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<List<CartItemInfo>> list(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "加入购物车的购物车的ID", required = false)
            @QueryParam(value = "cartId") String cartId,
            @ApiParam(value = "购物车类型", required = true)
            @QueryParam(value = "type") String type
    );

    //--------------------------------购物车商品数量---------------------------------------//
    @ApiOperation(
            value = "购物车商品数量",
            nickname = "购物车商品数量",
            notes = "购物车商品数量"
    )
    @Path("/num")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<CartNumResp> num(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "加入购物车的购物车的ID", required = false)
            @QueryParam(value = "cartId") String cartId,
            @ApiParam(value = "购物车类型", required = true)
            @QueryParam(value = "type") String type
    );

    //--------------------------------删除购物车---------------------------------------//
    @ApiOperation(
            value = "清除购物车",
            nickname = "清除购物车",
            notes = "清除购物车"
    )
    @Path("/removeCart")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<String> removeCart(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "加入购物车的购物车的ID", required = false)
            @QueryParam(value = "cartId") String cartId,
            @ApiParam(value = "购物车类型", required = true)
            @QueryParam(value = "type") String type,
            @ApiParam(value = "购物车ID", required = true)
            @QueryParam(value = "cartItems") List<String> cartItems);

    //--------------------------------清除购物车---------------------------------------//
    @ApiOperation(
            value = "清除购物车",
            nickname = "清除购物车",
            notes = "清除购物车"
    )
    @Path("/clearCart")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<String> clearCart(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "加入购物车的购物车的ID", required = false)
            @QueryParam(value = "cartId") String cartId,
            @ApiParam(value = "购物车类型", required = true)
            @QueryParam(value = "type") String type
    );

}
