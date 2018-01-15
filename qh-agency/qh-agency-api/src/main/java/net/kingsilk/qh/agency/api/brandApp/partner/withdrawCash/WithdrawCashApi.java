package net.kingsilk.qh.agency.api.brandApp.partner.withdrawCash;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniResp;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * 提现相关API
 */
@Api(
        tags = "withdrawCash",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "员工管理相关API"
)
@Path("/brandApp/{brandAppId}/partner/{partnerId}/withdrawCash")
@Component
public interface WithdrawCashApi {

    @ApiOperation(
            value = "提现申请",
            nickname = "提现申请",
            notes = "提现申请"
    )
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> apply(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "渠道商ID")
            @PathParam(value = "partnerId") String partnerId,
            @QueryParam(value = "applyAmount") Integer applyAmount
//            @QueryParam(value = "password") String password
    );


}
