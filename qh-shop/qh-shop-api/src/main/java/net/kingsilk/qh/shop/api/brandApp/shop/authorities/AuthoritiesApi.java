package net.kingsilk.qh.shop.api.brandApp.shop.authorities;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniResp;

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
        description = "微信端权限管理相关API"
)
@Path("/brandApp/{brandAppId}/shop/{shopId}/authorities")
public interface AuthoritiesApi {


    @ApiOperation(
            value = "当前用户的权限信息",
            nickname = "当前用户的权限信息",
            notes = "当前用户的权限信息"
    )
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Set<String>> getAuthorities(
            @ApiParam(value = "品牌商ID")
            @PathParam(value = "brandAppId") String brandAppId,
            @ApiParam(value = "店铺id")
            @PathParam(value = "shopId")
                    String shopId
    );
}
