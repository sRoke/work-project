package net.kingsilk.qh.agency.api.brandApp.partner.cart;

import io.swagger.annotations.*;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.partner.cart.dto.CartItemInfo;
import net.kingsilk.qh.agency.api.brandApp.partner.cart.dto.CartNumResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Api(
        tags = "cart",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "购物车相关api")
@Path("/brandApp/{brandAppId}/partner/{partnerId}/cart")
@Component
public interface CartApi {
    //--------------------------------加入购物车---------------------------------------//
    @ApiOperation(
            value = "加入购物车",
            nickname = "加入购物车",
            notes = "加入购物车"
    )
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<String> add(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "加入购物车的SKU的ID", required = true)
            @QueryParam(value = "skuId") String skuId,
            @ApiParam(value = "加入购物车的SKU的数量", required = true)
            @QueryParam(value = "num") int num,
            @ApiParam(value = "购物车类型", required = true)
            @QueryParam(value = "type") String type
    );

    //--------------------------------修改数量---------------------------------------//
    @ApiOperation(
            value = "修改数量",
            nickname = "修改数量",
            notes = "修改数量"
    )
    @Path("/setNum")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> setNum(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "加入购物车的SKU的ID", required = true)
            @QueryParam(value = "skuId") String skuId,
            @ApiParam(value = "加入购物车的SKU的数量", required = true)
            @QueryParam(value = "num") int num,
            @ApiParam(value = "购物车类型", required = true)
            @QueryParam(value = "type") String type);

    //--------------------------------购物车商品列表---------------------------------------//
    @ApiOperation(
            value = "购物车商品列表",
            nickname = "购物车商品列表",
            notes = "购物车商品列表"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<UniPageResp<CartItemInfo>> list(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
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
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "购物车类型", required = true)
            @QueryParam(value = "type") String type
    );

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
    UniResp<CartNumResp> clearCart(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
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
    UniResp<CartNumResp> removeCart(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "购物车类型", required = true)
            @QueryParam(value = "type") String type,
            @ApiParam(value = "购物车ID", required = true)
            @QueryParam(value = "cartItems") List<String> cartItems);

}
