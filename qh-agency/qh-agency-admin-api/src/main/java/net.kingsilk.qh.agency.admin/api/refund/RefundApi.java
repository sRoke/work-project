package net.kingsilk.qh.agency.admin.api.refund;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.agency.admin.api.refund.dto.RefundHandleReq;
import net.kingsilk.qh.agency.admin.api.refund.dto.RefundInfoResp;
import net.kingsilk.qh.agency.admin.api.refund.dto.RefundPageReq;
import net.kingsilk.qh.agency.admin.api.refund.dto.RefundPageResp;
import net.kingsilk.qh.agency.admin.api.UniResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Api(
        tags = "refund",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "售后订单相关API"
)
@Component
@Path("brandAppId/{{bandComId}}/refund")
public interface RefundApi {
    @ApiOperation(
            value = "售后订单信息",
            nickname = "售后订单信息",
            notes = "售后信息"
    )
    @Path("/{refundId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RefundInfoResp> info();

    @ApiOperation(
            value = "售后订单流程处理",
            nickname = "售后订单流程处理",
            notes = "售后订单流程处理"
    )
    @Path("/refundHandle")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> refundHandle(@BeanParam RefundHandleReq refundHandleReq);

    @ApiOperation(
            value = "售后分页信息",
            nickname = "售后分页信息",
            notes = "售后分页信息"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RefundPageResp> page(@BeanParam RefundPageReq refundPageReq);


}
