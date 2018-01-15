package net.kingsilk.qh.agency.wap.api.report;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.kingsilk.qh.agency.wap.api.UniResp;
import net.kingsilk.qh.agency.wap.api.report.dto.PurchaseReq;
import net.kingsilk.qh.agency.wap.api.report.dto.PurchaseResp;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by lit on 17/7/19.
 */

@Api(
        tags = "report",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "报表相关接口"
)
@Path("/report")
@Component
public interface ReportApi {
    @ApiOperation(
            value = "对订单进行支付",
            nickname = "对订单进行支付",
            notes = "对订单进行支付"
    )
    @Path("/purchase")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<PurchaseResp> purchase(PurchaseReq purchaseReq);

}
