package net.kingsilk.qh.agency.admin.api.deliverInvoice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.UniResp;
import net.kingsilk.qh.agency.admin.api.deliverInvoice.dto.DeliverInvoiceInfoResp;
import net.kingsilk.qh.agency.admin.api.deliverInvoice.dto.DeliverInvoicePageReq;
import net.kingsilk.qh.agency.admin.api.deliverInvoice.dto.DeliverInvoicePageResp;
import net.kingsilk.qh.agency.admin.api.deliverInvoice.dto.DeliverInvoiceShipReq;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Api(
        tags = "deliverInvoice",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "发货单相关API"
)
@Path("/deliverInvoice")
@Component
public interface DeliverInvoiceApi {
    //----------------------------发货单信息-------------------------------//
    @ApiOperation(
            value = "发货单信息",
            nickname = "发货单信息",
            notes = "发货单信息")
    @ApiParam(value = "id")
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<DeliverInvoiceInfoResp> info(@QueryParam(value = "id") String id);

    //----------------------------发货单分页信息-------------------------------//
    @ApiOperation(
            value = "发货单分页信息",
            nickname = "发货单分页信息",
            notes = "发货单分页信息"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<DeliverInvoicePageResp> page(@BeanParam DeliverInvoicePageReq deliverInvoicePageReq);

    //----------------------------发货-------------------------------//
    @ApiOperation(
            value = "发货",
            nickname = "发货",
            notes = "发货"
    )
    @Path("/ship")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> ship(@BeanParam DeliverInvoiceShipReq deliverInvoiceShipReq);

    @ApiOperation(
            value = "根据订单Id获取发货单信息",
            nickname = "根据订单Id获取发货单信息",
            notes = "根据订单Id获取发货单信息"
    )
    @Path("/getDeliverInvoiceIdByOrderId")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> getDeliverInvoiceIdByOrderId(@QueryParam(value = "id")String id);
}
