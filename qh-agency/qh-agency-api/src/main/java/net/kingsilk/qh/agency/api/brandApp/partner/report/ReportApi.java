package net.kingsilk.qh.agency.api.brandApp.partner.report;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.partner.report.dto.PurchaseReq;
import net.kingsilk.qh.agency.api.brandApp.partner.report.dto.PurchaseResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 *
 */

@Api(
        tags = "report",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "报表相关接口"
)
@Path("/brandApp/{brandAppId}/partner/{partnerId}/report")
@Component
public interface ReportApi {
    @ApiOperation(
            value = "报表相关接口",
            nickname = "报表相关接口",
            notes = "报表相关接口"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PurchaseResp> purchase(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @BeanParam PurchaseReq purchaseReq);

}
