package net.kingsilk.qh.vote.api.voteApp.vote.common;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.vote.common.dto.BackInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/voteApp/{voteAppId}")
public interface CommonApi {

    /**
     * 拿brandAppId
     */
    @ApiOperation(
            value = "拿brandAppId"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> get(
            @ApiParam("应用id")
            @PathParam("voteAppId")
                    String voteAppId
    );


    @ApiOperation(
            value = "获取shopId和brandAppId",
            nickname = "获取shopId和brandAppId",
            notes = "获取shopId和brandAppId"
    )
    @GET
    @Path("/backToShop")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<BackInfo> backToShop(
            @ApiParam("应用id")
            @PathParam("voteAppId")
                    String voteAppId
    );


}
