package net.kingsilk.qh.shop.api.brandApp.shop.deliverInvoice;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.deliverInvoice.dto.DeliverInvoiceShipReq;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Api(
        tags = "deliverInvoice",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "发货单相关API"
)
@Path("/brandApp/{brandAppId}/shop/{shopId}/deliverInvoice")
public interface DeliverInvoiceApi {

    @ApiOperation(
            value = "保存物流信息",
            nickname = "保存物流信息",
            notes = "保存物流信息"
    )
    @Path("/{id}")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('DELIVERINVOICE_U')")
    UniResp<String> logistics(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId") String shopId,
            @PathParam(value = "id") String id,
            DeliverInvoiceShipReq deliverInvoiceShipReq);

    @ApiOperation(
            value = "仅发货",
            nickname = "仅发货",
            notes = "仅发货"
    )
    @Path("/{id}/ship")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('DELIVERINVOICE_U')")
    UniResp<String> ship(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId") String shopId,
            @PathParam(value = "id") String id);

}
