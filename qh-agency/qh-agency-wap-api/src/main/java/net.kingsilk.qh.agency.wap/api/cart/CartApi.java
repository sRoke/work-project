package net.kingsilk.qh.agency.wap.api.cart;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.kingsilk.qh.agency.wap.api.UniResp;
import net.kingsilk.qh.agency.wap.api.cart.dto.CartAddReq;
import net.kingsilk.qh.agency.wap.api.cart.dto.CartListResp;
import net.kingsilk.qh.agency.wap.api.cart.dto.CartNumResp;
import net.kingsilk.qh.agency.wap.api.cart.dto.CartSetNumReq;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Api(
        tags = "cart",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "购物车相关api")
@Path("/cart")
@Component
public interface CartApi {

    @ApiOperation(
            value = "加入购物车",
            nickname = "加入购物车",
            notes = "加入购物车"
    )
    @Path("add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<String> add(CartAddReq req);

    @ApiOperation(
            value = "修改数量",
            nickname = "修改数量",
            notes = "修改数量"
    )
    @Path("setNum")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> setNum(CartSetNumReq req);

    @ApiOperation(
            value = "购物车商品列表",
            nickname = "购物车商品列表",
            notes = "购物车商品列表"
    )
    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<CartListResp> list();


    @ApiOperation(
            value = "购物车商品数量",
            nickname = "购物车商品数量",
            notes = "购物车商品数量"
    )
    @Path("num")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    UniResp<CartNumResp> num();


}
