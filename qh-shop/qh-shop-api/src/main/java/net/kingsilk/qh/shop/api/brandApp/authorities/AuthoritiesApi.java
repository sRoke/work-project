package net.kingsilk.qh.shop.api.brandApp.authorities;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;
import org.springframework.stereotype.Component;

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
@Path("/brandApp/{brandAppId}")
@Component
public interface AuthoritiesApi {




    @ApiOperation(value = "后台获取权限",
            nickname = "后台获取权限",
            notes = "后台获取权限")
    @GET
    @Path("/admin")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Set<String>> admin(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId
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
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "用户Id")
            @PathParam(value = "userId") String userId,
            @ApiParam(value = "用户Id")
            @PathParam(value = "orgId") String orgId
    );
}
