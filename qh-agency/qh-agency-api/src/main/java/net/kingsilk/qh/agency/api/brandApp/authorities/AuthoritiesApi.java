package net.kingsilk.qh.agency.api.brandApp.authorities;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniResp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Set;

/**
 *
 */
@Api(
        tags = "authorities",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "权限管理相关API"
)
@Path("/brandApp/{brandAppId}/authorities")
public interface AuthoritiesApi {

    //--------------------------------当前用户的权限信息---------------------------------------//
    @ApiOperation(
            value = "当前用户的权限信息",
            nickname = "当前用户的权限信息",
            notes = "当前用户的权限信息"
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Set<String>> getAuthorities(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId
    );

    //--------------------------------在平台支撑系统给商家一个超级管理员的权限---------------------------------------//
    @ApiOperation(
            value = "超级管理员的权限",
            nickname = "超级管理员的权限",
            notes = "超级管理员的权限"
    )
    @GET
    @Path("/userId/{userId}/orgId/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> setSAStaff(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "用户Id")
            @PathParam(value = "userId") String userId,
            @ApiParam(value = "用户Id")
            @PathParam(value = "orgId") String orgId,
            @ApiParam(value = "商家名字")
            @QueryParam(value = "shopName")String shopName,
            @ApiParam(value = "商家名字")
            @QueryParam(value = "phone")String phone
    );

}
