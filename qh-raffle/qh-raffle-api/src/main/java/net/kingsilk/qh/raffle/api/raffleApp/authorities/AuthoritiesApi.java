package net.kingsilk.qh.raffle.api.raffleApp.authorities;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.raffle.api.common.UniResp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Api(
        tags = "authorities",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "权限管理相关API"
)
@Path("/raffleApp/{raffleAppId}/authorities")
public interface AuthoritiesApi {

    @ApiOperation(
            value = "当前用户的权限信息",
            nickname = "当前用户的权限信息",
            notes = "当前用户的权限信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Set<String>> getAuthorities(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "raffleAppId") String raffleAppId
    );

    @ApiOperation(
            value = "设置超级管理员",
            notes = "设置超级管理员"
    )
    @GET
    @Path("/userId/{userId}/orgId/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> setSAStaff(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "raffleAppId") String raffleAppId,
            @ApiParam(value = "用户Id")
            @PathParam(value = "userId") String userId,
            @ApiParam(value = "用户Id")
            @PathParam(value = "orgId") String orgId
    );
}
