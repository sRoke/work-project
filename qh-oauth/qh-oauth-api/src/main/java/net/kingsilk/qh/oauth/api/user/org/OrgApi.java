package net.kingsilk.qh.oauth.api.user.org;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 * 收货地址
 */
@Api
@Path("/user/{userId}/org")
public interface OrgApi extends QhOAuthApi {

    @ApiOperation(
            value = "为用户添加组织"
    )
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> add(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            OrgAddReq orgAddReq
    );

    @ApiOperation(
            value = "删除用户的一个组织"
    )
    @DELETE
    @Path("/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Void> del(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId
    );

    @ApiOperation(
            value = "获取用户的一个组织"
    )
    @GET
    @Path("/{orgId}")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OrgGetResp> get(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId
    );

    @ApiOperation(
            value = "更新组织",
            notes = "局部更新"
    )
    @PATCH
    @Path("/{orgId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Void> update(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId,

            OrgUpdateReq orgUpdateReq
    );

    @ApiOperation(
            value = "获取用户的所有组织"
    )
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    UniResp<UniPage<OrgGetResp>> list(

            @ApiParam(value = "每页多少个记录,最大100")
            @QueryParam("size")
            @DefaultValue("10")
                    int size,

            @ApiParam(value = "页码。从0开始")
            @QueryParam("page")
            @DefaultValue("0")
                    int page,

            @ApiParam(
                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、 '+'、'desc'、'-'。可以传递多个 sort 参数",
                    allowMultiple = true,
                    example = "age,asc"
            )
            @QueryParam("sort")
                    List<String> sort,

            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId
    );


}
