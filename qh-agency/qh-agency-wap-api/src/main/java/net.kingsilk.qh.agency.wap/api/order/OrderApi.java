package net.kingsilk.qh.agency.wap.api.order;

import io.swagger.annotations.*;
import net.kingsilk.qh.agency.wap.api.UniResp;
import net.kingsilk.qh.agency.wap.api.order.dto.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@RestController

@Api(
        tags = "order",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "订单相关API"
)
@Path("/order")
@Component
public interface OrderApi {
    @ApiOperation(
            value = "订单列表",
            nickname = "订单列表",
            notes = "订单列表"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OrderListResp> list(@BeanParam OrderListReq req);

    @ApiOperation(
            value = "订单详情",
            nickname = "订单详情",
            notes = "订单详情"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OrderInfoModel> info(@ApiParam(value = "id", required = true)
                                 @QueryParam(value = "id") String id);

    /**
     * 从购物车提交、或者直接购买
     *
     * @return
     */
    @ApiOperation(
            value = "生成订单",
            nickname = "生成订单",
            notes = "生成订单"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/check")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> check(@BeanParam OrderCheckReq[] reqs);

    @ApiOperation(
            value = "提交订单",
            nickname = "提交订单",
            notes = "提交订单"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/create")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> create( OrderCreateReq req);

    @ApiOperation(
            value = "取消订单",
            nickname = "取消订单",
            notes = "取消订单"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/cancel")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> cancel(@RequestBody OrderCancelReq req);

    @ApiOperation(
            value = "确认收货",
            nickname = "确认收货",
            notes = "确认收货"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/confirmReceive")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> confirmReceive(@ApiParam(value = "id", required = true)
                                   @QueryParam(value = "id") String id);

    @ApiOperation(
            value = "申请退款",
            nickname = "申请退款",
            notes = "申请退款"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/refund")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> refund(@RequestBody OrderRefundReq req);

    @ApiOperation(
            value = "申请退款用户物流信息",
            nickname = "申请退款用户物流信息",
            notes = "申请退款用户物流信息"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/refundLogistics")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> refundLogistics(@RequestBody OrderRefundLogisticsReq req);

    @ApiOperation(
            value = "选择收货地址",
            nickname = "选择收货地址",
            notes = "选择收货地址"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/chooseAddr")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> chooseAddr(@RequestBody OrderChooseAddrReq req);

    @ApiOperation(
            value = "获取物流公司列表",
            nickname = "获取物流公司列表",
            notes = "获取物流公司列表"
    )
    @ApiResponses({@ApiResponse(code = 200, message = "正常结果")})
    @Path("/getLogisticsCompanyEnum")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Map<String, String>> getLogisticsCompanyEnum();


}
