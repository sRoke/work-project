package net.kingsilk.qh.oauth.api.user.org.regAddr;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * 组织的注册地址
 */
@Api
@Path("/user/{userId}/org/{orgId}/regAddr")
public interface RegAddrApi extends QhOAuthApi {

    @ApiOperation(
            value = "获取一个组织的注册地址"
    )
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<RegAddrGetResp> get(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId
    );

    @ApiOperation(
            value = "更新一个组织的办公地址",
            notes = "局部更新"
    )
    @PATCH
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Void> update(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId,

            RegAddrUpdateReq updateReq
    );

}
