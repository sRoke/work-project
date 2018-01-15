package net.kingsilk.qh.oauth.api.user.org.officeAddr;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.oauth.api.QhOAuthApi;
import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.UniResp;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 组织的办公地址。
 */
@Api
@Path("/user/{userId}/org/{orgId}/officeAddr")
public interface OfficeAddrApi extends QhOAuthApi {

    @ApiOperation(
            value = "为一个组织的新增一个办公地址"
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

            OfficeAddrAddReq addReq
    );

    @ApiOperation(
            value = "删除用户的一个组织"
    )
    @DELETE
    @Path("/{addrId}")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Void> del(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId,

            @ApiParam("地址ID")
            @PathParam("addrId")
                    String addrId
    );

    @ApiOperation(
            value = "获取一个组织的办公地址"
    )
    @GET
    @Path("/{addrId}")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OfficeAddrGetResp> get(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId,

            @ApiParam("地址ID")
            @PathParam("addrId")
                    String addrId
    );

    @ApiOperation(
            value = "更新一个组织的办公地址",
            notes = "局部更新"
    )
    @PATCH
    @Path("/{addrId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Void> update(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId,

            @ApiParam("地址ID")
            @PathParam("addrId")
                    String addrId,

            OfficeAddrUpdateReq updateReq
    );


    @ApiOperation(
            value = "获取组织的所有办公地址"
    )
    @GET
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    UniResp<UniPage<OfficeAddrGetResp>> list(

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

            @ApiParam("地址类型")
            @QueryParam("addrType")
                    String addrType
    );


    @ApiOperation(
            value = "获取组织默认办公地址"
    )
    @GET
    @Path("/default")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<OfficeAddrGetResp> getDefault(

            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId,

            @ApiParam("地址类型")
            @QueryParam("addrType")
                    String addrType
    );


    @ApiOperation(
            value = "设置组织默认办公地址"
    )
    @PUT
    @Path("/default")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Void> setDefault(

            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId,

            @ApiParam("地址类型")
            @QueryParam("addrType")
                    String addrType
    );


    @ApiOperation(
            value = "判断退货地址是否存在",
            nickname = "判断退货地址是否存在",
            notes = "判断退货地址是否存在"
    )
    @Path("/brandComAddr")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> judgeBrandComAddr(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("组织ID")
            @PathParam("orgId")
                    String orgId
    );

}
