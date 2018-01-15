package net.kingsilk.qh.agency.api.brandApp.partner.saleRecord;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.partner.saleRecord.dto.SaleRecordResp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Api(
        tags = "account",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "账户管理相关API"
)
@Path("/brandApp/{brandAppId}/partner/{partnerId}/sale")

public interface SaleRecordApi {

    @ApiOperation(
            value = "生意参谋主页信息",
            nickname = "生意参谋主页信息",
            notes = "生意参谋主页信息")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<SaleRecordResp> getSale(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandComId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId);

}
