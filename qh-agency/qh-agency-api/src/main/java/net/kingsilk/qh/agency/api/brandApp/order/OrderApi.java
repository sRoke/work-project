package net.kingsilk.qh.agency.api.brandApp.order;

import io.swagger.annotations.*;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrSaveReq;
import net.kingsilk.qh.agency.api.brandApp.order.dto.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

/**
 *
 */

@Api(
        tags = "order",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "订单管理相关API"
)
@Path("/brandApp/{brandAppId}/order")
@Component
public interface OrderApi {

    //----------------------------订单信息-------------------------------//
    @ApiOperation(
            value = "订单信息",
            nickname = "订单信息",
            notes = "订单信息")
    @ApiParam(value = "id")
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_R')")
    UniResp<OrderInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id);

    //----------------------------更改价格-------------------------------//
    @ApiOperation(
            value = "更改价格",
            nickname = "更改价格",
            notes = "更改价格"
    )
    @Path("/{id}/adjustPrice")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_C')")
    UniResp<String> adjustPrice(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "调整后价格")
            @QueryParam(value = "adjustPrice") Integer adjustPrice);

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
    @Path("/{id}/updateAddress")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('DELIVERINVOICE_C')")
    UniResp<String> updateAddress(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id,
            OrderAddressReq orderAddressReq);

    //----------------------------订单分页信息-------------------------------//
    @ApiOperation(
            value = "订单分页信息",
            nickname = "订单分页信息",
            notes = "订单分页信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_R')")
    UniResp<OrderPageResp<OrderMiniInfo>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @BeanParam OrderPageReq orderPageReq);

//    //----------------------------搜索adc地址-------------------------------//
//    @ApiOperation(
//            value = "搜索adc地址",
//            nickname = "搜索adc地址",
//            notes = "搜索adc地址"
//    )
//    @Path("/queryAdc")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<AddrResp> queryAdc(@BeanParam AddrReq req);

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

    //----------------------------卖家取消订单-------------------------------//

    /**
     * 取消订单时：
     * 如果已支付->会生成一条退款记录
     * 如果未支付->改变订单状态为拒绝接单
     * 如果处于代发货已生成发货单->1. 改变发货单状态为取消状态，2. 生成一条退款记录
     */
    @ApiOperation(
            value = "卖家取消订单",
            nickname = "卖家取消订单",
            notes = "卖家取消订单"
    )
    @Path("/{id}/cancelOrder")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_Q')")
    UniResp<String> cancelOrder(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "备注")
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
    @Path("/{id}/rejectOrder")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_J')")
    UniResp<String> rejectOrder(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "备注")
            @QueryParam(value = "memo") String memo);

    //----------------------------卖家备注-------------------------------//
    @ApiOperation(
            value = "卖家备注",
            nickname = "卖家备注",
            notes = "卖家备注"
    )
    @Path("/{id}/sellerMemo")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> sellerMemo(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "备注")
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
    @Path("/{id}/confirmOrder")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('ORDER_Y')")
    UniResp<String> confirmOrder(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id);


    //----------------------------财务确认订单-------------------------------//

//    /**
//     * 财务确认订单后会生成相应的发货单
//     */
//    @ApiOperation(
//            value = "财务确认订单",
//            nickname = "财务确认订单",
//            notes = "财务确认订单"
//    )
//    @Path("/financeConfirm")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> financeConfirm(@QueryParam(value = "id") String id,
//                                   @QueryParam(value = "confirm") Boolean confirm,
//                                   @QueryParam(value = "memo") String memo);


//    //----------------------------生成订单-------------------------------//
//
//    /**
//     * 从购物车提交、或者直接购买
//     */
//    @ApiOperation(
//            value = "生成订单",
//            nickname = "生成订单",
//            notes = "生成订单"
//    )
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> check(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            List<OrderCheckReq> req);

//    //----------------------------重新计算订单金额------------------------------//
//    @ApiOperation(
//            value = "生成订单",
//            nickname = "生成订单",
//            notes = "生成订单"
//    )
//    @Path("/{id}/calculatePrice")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> calculatePrice(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "id")
//            @PathParam(value = "id") String id,
//            @ApiParam(value = "是否抵扣")
//            @QueryParam(value = "type") String type
//    );


//    //----------------------------提交订单-------------------------------//
//    @ApiOperation(
//            value = "提交订单",
//            nickname = "提交订单",
//            notes = "提交订单"
//    )
//    @Path("/{id}/create")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> create(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "id")
//            @PathParam(value = "id") String id,
//            OrderCreateReq req);
//
//
//    //----------------------------买家取消订单-------------------------------//
//    @ApiOperation(
//            value = "买家取消订单",
//            nickname = "买家取消订单",
//            notes = "买家取消订单"
//    )
//    @Path("/{id}/cancel")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> cancel(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "订单ID", required = true)
//            @PathParam(value = "id") String id,
//            @ApiParam(value = "取消订单原因")
//            @QueryParam(value = "reason") String reason);
//
//    //----------------------------确认收货-------------------------------//
//    @ApiOperation(
//            value = "确认收货",
//            nickname = "确认收货",
//            notes = "确认收货"
//    )
//    @Path("/{id}/confirmReceive")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> confirmReceive(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "订单ID", required = true)
//            @PathParam(value = "id") String id);
//
//    //----------------------------申请退款-------------------------------//
//
//    /**
//     * 申请退款|退货退款信息,修改状态为->等待卖家确认
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
//            OrderRefundReq req);


    //----------------------------选择收货地址-------------------------------//
    @ApiOperation(
            value = "选择收货地址",
            nickname = "选择收货地址",
            notes = "选择收货地址"
    )
    @Path("/{id}/chooseAddr")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> chooseAddr(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "id")
            @PathParam(value = "id") String id,
            @QueryParam(value = "addrId") String addrId);

    //----------------------------选择收货地址-------------------------------//
    @ApiOperation(
            value = "选择收货地址",
            nickname = "选择收货地址",
            notes = "选择收货地址"
    )
    @Path("/{id}/changeAddr")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> changeAddr(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "id")
            @PathParam(value = "id") String id,
            @QueryParam(value = "addrId") String addrId);

    //----------------------------更改收货地址-------------------------------//
    @ApiOperation(
            value = "选择收货地址",
            nickname = "选择收货地址",
            notes = "选择收货地址"
    )
    @Path("/{id}/addAddr")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> addAddr(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "id")
            @PathParam(value = "id") String id,
            AddrSaveReq req);


    //--------------------------------更新收货地址---------------------------------------//
    @ApiOperation(
            value = "更新收货地址",
            nickname = "更新收货地址",
            notes = "更新收货地址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/{orderId}/updateAddr/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> updateAddr(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "orderId") String orderId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "id") String id,
            AddrSaveReq req);

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

//    //----------------------------获取订单中sku信息-------------------------------//
//    @ApiOperation(
//            value = "获取订单中sku信息",
//            nickname = "获取订单中sku信息",
//            notes = "获取订单中sku信息"
//    )
//    @Path("/getLogisticsCompanyEnum")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<List<OrderItemInfo>> getOrderItemInfo(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "渠道商ID")
//            @PathParam(value = "partnerId") String partnerId);


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
            @ApiParam(value = "id", required = true)
            @PathParam(value = "id") String id);

}
