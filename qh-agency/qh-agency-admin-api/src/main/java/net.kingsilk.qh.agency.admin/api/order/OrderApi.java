package net.kingsilk.qh.agency.admin.api.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.UniResp;
import net.kingsilk.qh.agency.admin.api.order.dto.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 */

@Api(
        tags = "order",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "订单管理相关API"
)
@Path("/order")
@Component
public interface OrderApi {

    //----------------------------订单信息-------------------------------//
    @ApiOperation(
            value = "订单信息",
            nickname = "订单信息",
            notes = "订单信息")
    @ApiParam(value = "id")
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OrderInfoResp> info(@QueryParam(value = "id") String id);

    //----------------------------更改价格-------------------------------//
    @ApiOperation(
            value = "更改价格",
            nickname = "更改价格",
            notes = "更改价格"
    )
    @Path("/adjustPrice")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> adjustPrice(@QueryParam(value = "id") String id,
                                 @QueryParam(value = "adjustPrice") Double adjustPrice);

//    //----------------------------发货-------------------------------//
//    @ApiOperation(
//            value = "发货",
//            nickname = "发货",
//            notes = "发货"
//    )
//    @Path("/ship")
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> ship(@BeanParam OrderShipReq orderShipReq);

    //----------------------------修改收货地址-------------------------------//
    @ApiOperation(
            value = "修改收货地址",
            nickname = "修改收货地址",
            notes = "修改收货地址"
    )
    @Path("/updateAddress")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> updateAddress(@BeanParam OrderAddressReq orderAddressReq);

    //----------------------------订单分页信息-------------------------------//
    @ApiOperation(
            value = "订单分页信息",
            nickname = "订单分页信息",
            notes = "订单分页信息"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OrderPageResp> page(@BeanParam OrderPageReq orderPageReq);

    //----------------------------搜索adc地址-------------------------------//
    @ApiOperation(
            value = "搜索adc地址",
            nickname = "搜索adc地址",
            notes = "搜索adc地址"
    )
    @Path("/queryAdc")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<AddrResp> queryAdc(@BeanParam AddrReq req);

    //----------------------------导出订单-------------------------------//
//    @ApiOperation(
//            value = "导出订单",
//            nickname = "导出订单",
//            notes = "导出订单"
//    )
//    @Path("/export")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    void exportExcel(@PathParam(value = "id") String id, @Context HttpServletResponse response) throws Exception;

    //----------------------------取消订单-------------------------------//
    /**
     * 取消订单时：
     * 如果已支付->会生成一条退款记录
     * 如果未支付->改变订单状态为拒绝接单
     * 如果处于代发货已生成发货单->1. 改变发货单状态为取消状态，2. 生成一条退款记录
     */
    @ApiOperation(
            value = "取消订单",
            nickname = "取消订单",
            notes = "取消订单"
    )
    @Path("/cancelOrder")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> cancelOrder(@QueryParam(value = "id") String id,
                                @QueryParam(value = "memo") String memo);



    //----------------------------拒绝订单-------------------------------//
    /**
     * 取消订单时：
     * 如果已支付->会生成一条退款记录
     * 如果未支付->改变订单状态为拒绝接单
     * 如果处于代发货已生成发货单->1. 改变发货单状态为取消状态，2. 生成一条退款记录
     */
    @ApiOperation(
            value = "拒绝订单",
            nickname = "拒绝订单",
            notes = "拒绝订单"
    )
    @Path("/rejectOrder")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> rejectOrder(@QueryParam(value = "id") String id,
                                @QueryParam(value = "memo") String memo);

    //----------------------------卖家备注-------------------------------//
    @ApiOperation(
            value = "卖家备注",
            nickname = "卖家备注",
            notes = "卖家备注"
    )
    @Path("/sellerMemo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> sellerMemo(@QueryParam(value = "id") String id,
                               @QueryParam(value = "memo") String memo);


    //----------------------------确认接单-------------------------------//
    /**
     * 确认接单后会生成相应的发货单
     */
    @ApiOperation(
            value = "确认接单",
            nickname = "确认接单",
            notes = "确认接单"
    )
    @Path("/confirmOrder")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> confirmOrder(@QueryParam(value = "id") String id);


    //----------------------------财务确认订单-------------------------------//
    /**
     * 财务确认订单后会生成相应的发货单
     */
    @ApiOperation(
            value = "财务确认订单",
            nickname = "财务确认订单",
            notes = "财务确认订单"
    )
    @Path("/financeConfirm")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> financeConfirm(@QueryParam(value = "id") String id,
                                   @QueryParam(value = "confirm") Boolean confirm,
                                   @QueryParam(value = "memo") String memo);
}
