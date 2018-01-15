package net.kingsilk.qh.raffle.api.raffleApp.home;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.raffle.api.common.UniResp;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * 首页相关qpi
 */
@Api
@Path("/brandApp/{brandAppId}/shop/{shopId}/raffle/home")
@Singleton
public interface HomeApi {
    @ApiOperation(
            value = "获取raffleAppId",
            nickname = "获取raffleAppId",
            notes = "获取raffleAppId"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF_R')")
    UniResp<String> getRaffleAppId(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId") String shopId);

}
