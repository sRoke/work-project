package net.kingsilk.qh.oauth.api.user.org.orgStaff;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 * 收货地址
 */
@Api
@Path("/user/{userId}/org/{orgId}/orgStaff")
public interface OrgStaffApi extends QhOAuthApi {

    @ApiOperation(
            value = "为用户添加组织"
    )
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> add(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId,

            OrgStaffAddReq addReq
    );

    @ApiOperation(
            value = "删除一个员工"
    )
    @DELETE
    @Path("/{staffId}")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Void> del(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId,

            @ApiParam("员工ID")
            @PathParam("staffId")
                    String staffId
    );

    @ApiOperation(
            value = "获取一个员工"
    )
    @GET
    @Path("/{staffId}")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OrgStaffGetResp> get(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId,

            @ApiParam("员工ID")
            @PathParam("staffId")
                    String staffId
    );

    @ApiOperation(
            value = "获取一个员工"
    )
    @GET
    @Path("/check")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> check(
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
    @Path("/{staffId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Void> update(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId,

            @ApiParam("员工ID")
            @PathParam("staffId")
                    String staffId,

            OrgStaffUpdateReq orgStaffUpdateReq
    );

    @ApiOperation(
            value = "获取多条组织员工"
    )
    @GET
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    UniResp<UniPage<OrgStaffGetResp>> list(

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
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId,

            @ApiParam("员工ID")
            @QueryParam("staffIds")
                    String staffIds
    );


    @ApiOperation(
            value = "通过userId搜索组织员工"
    )
    @GET
    @Path("/search")
    @Consumes(MediaType.APPLICATION_JSON)
    UniResp<UniPage<OrgStaffGetResp>> search(

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

            @ApiParam("查询关键字")
            @QueryParam("userId")
                    String userId
    );

}
