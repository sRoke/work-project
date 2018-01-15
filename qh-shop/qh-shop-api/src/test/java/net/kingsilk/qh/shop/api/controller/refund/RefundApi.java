package net.kingsilk.qh.shop.api.controller.refund;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.controller.order.dto.OrderCheckReq;
import net.kingsilk.qh.shop.api.controller.refund.dto.RefundInfoResp;
import net.kingsilk.qh.shop.api.controller.refund.dto.RefundPageInfo;
import net.kingsilk.qh.shop.api.controller.refund.dto.RefundPageReq;
import net.kingsilk.qh.shop.api.controller.refund.dto.RefundPageResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Api(
        tags = "refund",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "售后订单相关API"
)
@Component
@Path("/brandApp/{brandAppId}/partner/{partnerId}/refund")
public interface RefundApi {
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
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id);

    //-------------------------同意退货申请---------------------------------//

    /**
     * 同意退货申请，修改退货退款状态为->待买家退货
     */
    @ApiOperation(
            value = "同意退货申请",
            nickname = "同意退货申请",
            notes = "同意退货申请"
    )
    @Path("/{id}/agreeReturnGoods")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> agreeReturnGoods(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id);


    //-------------------------确认收货并同意退款|同意退款---------------------------------//

    /**
     * 确认收货并同意退款|同意退款，修改退货退款状态为->待退款
     */
    @ApiOperation(
            value = "确认收货并同意退款|同意退款",
            nickname = "确认收货并同意退款|同意退款",
            notes = "确认收货并同意退款|同意退款"
    )
    @Path("/{id}/agreeRefund")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> agreeRefund(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id);

    //-------------------------退款---------------------------------//

    /**
     * 确认收货并同意退款|同意退款，修改退货退款状态为->已完成
     */
    @ApiOperation(
            value = "退款",
            nickname = "退款",
            notes = "退款"
    )
    @Path("/{id}/refundHandle")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> refundHandle(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id
//            @BeanParam RefundHandleReq refundHandleReq
    );


    //-------------------------售后分页信息---------------------------------//
    @ApiOperation(
            value = "售后分页信息",
            nickname = "售后分页信息",
            notes = "售后分页信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RefundPageResp<RefundPageInfo>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam RefundPageReq refundPageReq);


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
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            List<OrderCheckReq> req);


    @ApiOperation(
            value = "确认退款订单",
            nickname = "确认退款订单",
            notes = "确认退款订单"
    )
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> check(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @PathParam(value = "id") String id);

    //-------------------------买家取消退款---------------------------------//

    /**
     * 拒绝买家的退货|退款申请，状态修改为->卖家拒绝申请
     */
    @ApiOperation(
            value = "买家取消退款",
            nickname = "买家取消退款",
            notes = "买家取消退款"
    )
    @Path("/{id}/cancelRefund")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> cancelRefund(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "拒绝退款原因")
            @QueryParam(value = "rejectReason") String rejectReason);
}
