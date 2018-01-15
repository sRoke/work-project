package net.kingsilk.qh.shop.api.brandApp.shop.mall.refund;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.refund.dto.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/brandApp/{brandAppId}/shop/{shopId}/mall/refund")
public interface RefundApi {

    //-------------------------售后订单信息---------------------------------//
    @ApiOperation(
            value = "售后订单信息",
            nickname = "售后订单信息",
            notes = "售后信息"
    )
    @Path("/sku/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RefundSkuInfo> skuInfo(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "skuID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "orderID")
            @QueryParam(value = "orderId") String orderId
    );

    //-------------------------售后订单信息---------------------------------//
    @ApiOperation(
            value = "售后订单信息",
            nickname = "售后订单信息",
            notes = "售后信息"
    )
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RefundInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id);


    /**
     * 从购物车提交、或者直接购买
     */
    @ApiOperation(
            value = "生成退款订单",
            nickname = "生成退款订单",
            notes = "生成退款订单"
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> generateRefund(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            List<OrderCheckReq> req);


    //-------------------------售后分页信息---------------------------------//
    @ApiOperation(
            value = "售后分页信息",
            nickname = "售后分页信息",
            notes = "售后分页信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<RefundInfoResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @BeanParam RefundPageReq refundPageReq);


    @ApiOperation(
            value = "申请退款用户物流信息",
            nickname = "申请退款用户物流信息",
            notes = "申请退款用户物流信息"
    )
    @Path("/{id}/refundLogistics")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> refundLogistics(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "订单id")
            @PathParam(value = "id") String id,
            RefundLogisticsReq req);


}
