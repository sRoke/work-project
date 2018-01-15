package net.kingsilk.qh.vote.api.home;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.vote.api.UniResp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Api(
        tags = "home",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "员工管理相关API"
)
@Path("/brandApp/{brandAppId}/shop/{shopId}/vote/home")
public interface HomeApi {

    @ApiOperation(
            value = "getVoteAppId",
            nickname = "getVoteAppId",
            notes = "getVoteAppId"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF_R')")
    UniResp<String> getVoteAppId(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId") String shopId);

}
