package net.kingsilk.qh.vote.api.voteApp.authorities;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.vote.api.UniResp;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Set;

@Api(
        tags = "authorities",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "权限管理相关API"
)
@Path("/voteApp/{voteAppId}/authorities")
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
            @PathParam(value = "voteAppId") String voteAppId
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
            @PathParam(value = "voteAppId") String voteAppId,
            @ApiParam(value = "用户Id")
            @PathParam(value = "userId") String userId,
            @ApiParam(value = "用户Id")
            @PathParam(value = "orgId") String orgId
    );
}
