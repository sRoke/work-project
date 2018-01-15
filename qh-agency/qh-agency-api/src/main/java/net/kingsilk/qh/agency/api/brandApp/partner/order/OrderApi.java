package net.kingsilk.qh.agency.api.brandApp.partner.order;

import io.swagger.annotations.*;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrSaveReq;
import net.kingsilk.qh.agency.api.brandApp.order.dto.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
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
@Path("/brandApp/{brandAppId}/partner/{partnerId}/order")
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
    UniResp<OrderInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id);



    //----------------------------订单分页信息-------------------------------//
    @ApiOperation(
            value = "订单分页信息",
            nickname = "订单分页信息",
            notes = "订单分页信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OrderPageResp<OrderMiniInfo>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam OrderPageReq orderPageReq);


    //----------------------------生成订单-------------------------------//

    /**
     * 从购物车提交、或者直接购买
     */
    @ApiOperation(
            value = "生成订单",
            nickname = "生成订单",
            notes = "生成订单"
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> check(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            List<OrderCheckReq> req);

    //----------------------------重新计算订单金额------------------------------//
    @ApiOperation(
            value = "生成订单",
            nickname = "生成订单",
            notes = "生成订单"
    )
    @Path("/{id}/calculatePrice")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> calculatePrice(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "id")
            @PathParam(value = "id") String id,
            @ApiParam(value = "是否抵扣")
            @QueryParam(value = "type") String type
    );


    //----------------------------提交订单-------------------------------//
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
            @ApiParam(value = "id")
            @PathParam(value = "id") String id,
            OrderCreateReq req);


    //----------------------------买家取消订单-------------------------------//
    @ApiOperation(
            value = "买家取消订单",
            nickname = "买家取消订单",
            notes = "买家取消订单"
    )
    @Path("/{id}/cancel")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> cancel(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "订单ID", required = true)
            @PathParam(value = "id") String id,
            @ApiParam(value = "取消订单原因")
            @QueryParam(value = "reason") String reason);

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
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "订单ID", required = true)
            @PathParam(value = "id") String id);

    //----------------------------申请退款-------------------------------//

    /**
     * 申请退款|退货退款信息,修改状态为->等待卖家确认
     */
    @ApiOperation(
            value = "申请退款",
            nickname = "申请退款",
            notes = "申请退款"
    )
    @Path("/{id}/refund")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> refund(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            OrderRefundReq req);


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
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
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
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
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
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
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
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "订单ID")
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
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId);



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
    UniResp<String> confirmOrder(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
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
    UniResp<String> rejectOrder(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "备注")
            @QueryParam(value = "memo") String memo);
}
