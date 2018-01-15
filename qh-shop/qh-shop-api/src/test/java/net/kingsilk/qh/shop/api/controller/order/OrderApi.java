package net.kingsilk.qh.shop.api.controller.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.controller.order.dto.*;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 *
 */

@Api(
        tags = "order",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "订单管理相关API"
)
@Path("/brandApp/{brandAppId}/partner/{partnerId}/order")
@Component
public interface OrderApi {

//    //----------------------------后台收银记录详情-------------------------------//
//    @ApiOperation(
//            value = "订单信息",
//            nickname = "订单信息",
//            notes = "订单信息")
//    @ApiParam(value = "id")
//    @Path("/{id}")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<OrderInfoResp> info(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "渠道商ID")
//            @PathParam(value = "partnerId") String partnerId,
//            @ApiParam(value = "订单ID")
//            @PathParam(value = "id") String id);

//    //----------------------------更改价格-------------------------------//
//    @ApiOperation(
//            value = "更改价格",
//            nickname = "更改价格",
//            notes = "更改价格"
//    )
//    @Path("/{id}/adjustPrice")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> adjustPrice(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "渠道商ID")
//            @PathParam(value = "partnerId") String partnerId,
//            @ApiParam(value = "订单ID")
//            @PathParam(value = "id") String id,
//            @ApiParam(value = "调整后价格")
//            @QueryParam(value = "adjustPrice") Integer adjustPrice);

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

//    //----------------------------修改收货地址-------------------------------//
//    @ApiOperation(
//            value = "修改收货地址",
//            nickname = "修改收货地址",
//            notes = "修改收货地址"
//    )
//    @Path("/{id}/updateAddress")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> updateAddress(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "渠道商ID")
//            @PathParam(value = "partnerId") String partnerId,
//            @ApiParam(value = "订单ID")
//            @PathParam(value = "id") String id,
//            OrderAddressReq orderAddressReq);

//    //----------------------------后台收银记录列表-------------------------------//
//    @ApiOperation(
//            value = "订单分页信息",
//            nickname = "订单分页信息",
//            notes = "订单分页信息"
//    )
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<OrderPageResp<OrderMiniInfo>> page(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "渠道商ID")
//            @PathParam(value = "partnerId") String partnerId,
//            @BeanParam OrderPageReq orderPageReq);
//
//    //----------------------------卖家备注-------------------------------//
//    @ApiOperation(
//            value = "卖家备注",
//            nickname = "卖家备注",
//            notes = "卖家备注"
//    )
//    @Path("/{id}/sellerMemo")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> sellerMemo(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "渠道商ID")
//            @PathParam(value = "partnerId") String partnerId,
//            @ApiParam(value = "订单ID")
//            @PathParam(value = "id") String id,
//            @ApiParam(value = "备注")
//            @QueryParam(value = "memo") String memo);


    //----------------------------生成订单-------------------------------//

    /**
     * 从购物车提交、或者直接购买,生成order信息
     */
    @ApiOperation(
            value = "生成订单",
            nickname = "生成订单",
            notes = "生成订单"
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp check(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            List<OrderCheckReq> req);


    //----------------------------订单优惠-------------------------------//

    /**
     * 根据前端提供的数据，计算折扣后价格
     */
    @ApiOperation(
            value = "订单优惠",
            nickname = "订单优惠",
            notes = "订单优惠"
    )
    @Path("/{id}/calculationPrice")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp calculationPrice(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(name = "折扣", value = "discount")
            @QueryParam(value = "discount") Integer discount,
            @ApiParam(name = "减价", value = "reducePrice")
            @QueryParam(value = "reducePrice") Integer reducePrice);
    //----------------------------提交订单-------------------------------//

    /**
     * 先根据前端数据，计算订单价格，
     * 生成相应的QhPay信息
     */
    @ApiOperation(
            value = "提交订单",
            nickname = "提交订单",
            notes = "提交订单"
    )
    @Path("/{id}/create")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> create(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "orderId")
            @PathParam(value = "id") String id,
            OrderCreateReq req);

//    //----------------------------申请退款-------------------------------//
//
//    /**
//     *申请退款|退货退款信息,修改状态为->等待卖家确认
//     */
//    @ApiOperation(
//            value = "申请退款",
//            nickname = "申请退款",
//            notes = "申请退款"
//    )
//    @Path("/{id}/refund")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> refund(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "渠道商ID")
//            @PathParam(value = "partnerId") String partnerId,
//            OrderRefundReq req);


    @ApiOperation(
            value = "订单列表",
            nickname = "订单列表",
            notes = "订单列表"
    )
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPageResp<OrderInfoModel>> list(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam OrderListReq req);

    @ApiOperation(
            value = "订单详情",
            nickname = "订单详情",
            notes = "订单详情"
    )
    @Path("/{id}/detail")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OrderInfoModel> detail(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "id", required = true)
            @PathParam(value = "id") String id);

    @ApiOperation(
            value = "获取订单在明细中的信息",
            nickname = "获取订单在明细中的信息",
            notes = "获取订单在明细中的信息"
    )
    @Path("/log")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<List<PALogResp>> log(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam PALogReq pareq);


}
