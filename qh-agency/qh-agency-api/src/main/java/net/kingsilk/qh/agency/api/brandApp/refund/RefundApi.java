package net.kingsilk.qh.agency.api.brandApp.refund;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.order.dto.OrderCheckReq;
import net.kingsilk.qh.agency.api.brandApp.order.dto.RefundLogisticsReq;
import net.kingsilk.qh.agency.api.brandApp.refund.dto.*;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Path("/brandApp/{brandAppId}/refund")
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
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUND_R')")
    UniResp<RefundInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id);

    //-------------------------拒绝买家的退货|退款申请---------------------------------//

    /**
     * 拒绝买家的退货|退款申请，状态修改为->卖家拒绝申请
     */
    @ApiOperation(
            value = "拒绝买家的退货|退款申请",
            nickname = "拒绝买家的退货|退款申请",
            notes = "拒绝买家的退货|退款申请"
    )
    @Path("/{id}/reject")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUND_J')")
    UniResp<String> reject(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "拒绝退款原因")
            @QueryParam(value = "rejectReason") String rejectReason);

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
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUND_C')")
    UniResp<String> agreeReturnGoods(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id);


    //-------------------------买家填写退货物流信息---------------------------------//
//      迁移到order中
//    /**
//     * 买家填写退货物流信息，修改退货退款状态为->等待卖家确认收货
//     */
//    @ApiOperation(
//            value = "买家填写退货物流信息",
//            nickname = "买家填写退货物流信息",
//            notes = "买家填写退货物流信息"
//    )
//    @Path("/{id}/writeLogistics")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<String> writeLogistics(
//            @ApiParam(value = "品牌商ID")
//            @PathParam(value = "brandAppId") String brandAppId,
//            @ApiParam(value = "渠道商ID")
//            @PathParam(value = "partnerId") String partnerId,
//            @ApiParam(value = "售后订单ID")
//            @QueryParam(value = "id") String id);

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
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUND_C')")
    UniResp<String> agreeRefund(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
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
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUNDMONEY_U')")
    UniResp<String> refundHandle(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
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
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUND_R')")
    UniResp<RefundPageResp<RefundPageInfo>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
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
            List<OrderCheckReq> req);


    @ApiOperation(
            value = "确认退款订单",
            nickname = "确认退款订单",
            notes = "确认退款订单"
    )
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('REFUNDMONEY_C')")
    UniResp<String> check(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @PathParam(value = "id") String id,
            RefundCheckReq refundCheckReq);


    //----------------------------申请退款用户物流信息-------------------------------//
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
            @PathParam(value = "id") String id,
            RefundLogisticsReq req);

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
            @ApiParam(value = "售后订单ID")
            @PathParam(value = "id") String id,
            @ApiParam(value = "拒绝退款原因")
            @QueryParam(value = "rejectReason") String rejectReason);
}
