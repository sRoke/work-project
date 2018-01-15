package net.kingsilk.qh.agency.api.brandApp.deliverInvoice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.deliverInvoice.dto.DeliverInvoiceInfoResp;
import net.kingsilk.qh.agency.api.brandApp.deliverInvoice.dto.DeliverInvoicePageReq;
import net.kingsilk.qh.agency.api.brandApp.deliverInvoice.dto.DeliverInvoicePageResp;
import net.kingsilk.qh.agency.api.brandApp.deliverInvoice.dto.DeliverInvoiceShipReq;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Path("/brandApp/{brandAppId}/deliverInvoice")
@Component
public interface DeliverInvoiceApi {
    //----------------------------发货单信息-------------------------------//
    @ApiOperation(
            value = "发货单信息",
            nickname = "发货单信息",
            notes = "发货单信息")

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('DELIVERINVOICE_R')")
    UniResp<DeliverInvoiceInfoResp> info(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "发货单ID")
            @PathParam(value = "id") String id);

    //----------------------------发货单分页信息-------------------------------//
    @ApiOperation(
            value = "发货单分页信息",
            nickname = "发货单分页信息",
            notes = "发货单分页信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('DELIVERINVOICE_R')")
    UniResp<UniPageResp<DeliverInvoicePageResp>> page(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @BeanParam DeliverInvoicePageReq deliverInvoicePageReq);

    //----------------------------发货-------------------------------//
    @ApiOperation(
            value = "发货",
            nickname = "发货",
            notes = "发货"
    )
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('DELIVERINVOICE_U')")
    UniResp<String> ship(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @PathParam(value = "id") String id,
            DeliverInvoiceShipReq deliverInvoiceShipReq);

    //---------------------------根据订单Id获取发货单信息--------------------------------//
    @ApiOperation(
            value = "根据订单Id获取发货单信息",
            nickname = "根据订单Id获取发货单信息",
            notes = "根据订单Id获取发货单信息"
    )
    @Path("/{id}/deliverInvoice")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> getDeliverInvoiceIdByOrderId(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "订单ID")
            @PathParam(value = "id") String id);
}
