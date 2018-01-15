package net.kingsilk.qh.shop.api.brandApp.shop.mall.order;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

@Api(
        tags = "OrderApi",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "微信端订单相关API"
)
@Path("/brandApp/{brandAppId}/shop/{shopId}/mall/order")
public interface OrderApi {

    //----------------------------创建订单-------------------------------//
    @ApiOperation(
            value = "创建订单",
            nickname = "创建订单",
            notes = "创建订单")
    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_R')")
    UniResp<Map<String, String>> create(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            List<OrderCheckReq> orderCheckReq);

    //----------------------------创建订单-------------------------------//
    @ApiOperation(
            value = "修改地址",
            nickname = "修改地址",
            notes = "修改地址")
    @Path("/{id}/addr/{addrId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_R')")
    UniResp<String> chooseAddr(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @PathParam(value = "id") String id,
            @PathParam(value = "addrId") String addrId
    );

    //----------------------------提交订单-------------------------------//

    @ApiOperation(
            value = "提交订单",
            nickname = "提交订单",
            notes = "提交订单")
    @Path("/{id}/commitOrder")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_R')")
    UniResp<String> commit(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @PathParam(value = "id") String id,
            OrderCommitReq orderCommitReq);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_R')")
    UniResp<OrderPageResp<OrderMiniInfo>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @BeanParam OrderPageReq orderPageReq);


    @ApiOperation(
            value = "订单信息",
            nickname = "订单信息",
            notes = "订单信息")
    @ApiParam(value = "id")
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_R')")
    UniResp<OrderInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id);

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
    @Path("/{id}/rejectOrder")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_J')")
    UniResp<String> rejectOrder(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "门店ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "备注")
            @QueryParam(value = "memo") String memo);


    //----------------------------获取物流公司列表-------------------------------//
    @ApiOperation(
            value = "获取物流公司列表",
            nickname = "获取物流公司列表",
            notes = "获取物流公司列表"
    )
    @Path("/getLogisticsCompanyEnum")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Map<String, String>> getLogisticsCompanyEnum(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId);

    //----------------------------取消订单-------------------------------//
    @ApiOperation(
            value = "取消订单",
            nickname = "取消订单",
            notes = "取消订单"
    )
    @Path("/{id}/cancelOrder")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_J')")
    UniResp<String> cancelOrder(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId")
                    String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId")
                    String shopId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id")
                    String id,
            @ApiParam(value = "备注")
            @QueryParam(value = "memo")
                    String memo);


    //----------------------------确认收货-------------------------------//
    @ApiOperation(
            value = "确认收货",
            nickname = "确认收货",
            notes = "确认收货"
    )
    @Path("/{id}/confirmReceive")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> confirmReceive(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "订单ID", required = true)
            @PathParam(value = "id") String id);


    //----------------------------确认自提-------------------------------//
    @ApiOperation(
            value = "确认自提",
            nickname = "确认自提",
            notes = "确认自提"
    )
    @Path("/{id}/confirmSince")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> confirmSince(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "订单ID", required = true)
            @PathParam(value = "id") String id);


    //----------------------------物流方式（快递或自取）-------------------------------//
    @ApiOperation(
            value = "物流方式",
            nickname = "物流方式",
            notes = "物流方式"
    )
    @Path("/{id}/deliverType")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> deliverType(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "shopId") String shopId,
            @ApiParam(value = "订单ID", required = true)
            @PathParam(value = "id") String id,
            @ApiParam(value = "自取或快递")
            @QueryParam(value = "orderSourceType")
            String orderSourceType);

}

//----------------------------确认接单-------------------------------//

//    /**
//     * 确认接单后会生成相应的发货单
//     */
//    @ApiOperation(
//            value = "确认接单",
//            nickname = "确认接单",
//            notes = "确认接单"
//    )
//    @Path("/{id}/confirmOrder")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> confirmOrder(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "门店ID")
//            @PathParam(value = "shopId") String shopId,
//            @ApiParam(value = "订单ID")
//            @PathParam(value = "id") String id);
//}
