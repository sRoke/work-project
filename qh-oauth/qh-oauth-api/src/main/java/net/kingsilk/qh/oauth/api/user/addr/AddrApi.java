package net.kingsilk.qh.oauth.api.user.addr;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 * 收货地址
 */
@Api
@Path("/user/{userId}/addr")
public interface AddrApi extends QhOAuthApi {

    @ApiOperation(
            value = "为用户添加收货地址，并返回地址ID"
    )
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> add(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            AddrAddReq addrReq
    );


    @ApiOperation(
            value = "获取用户的地址"
    )
    @GET
    @Path("/{addrId}")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<AddrGetResp> get(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("地址ID")
            @PathParam("addrId")
                    String addrId
    );

    @ApiOperation(
            value = "删除用户收货地址"
    )
    @DELETE
    @Path("/{addrId}")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Void> del(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("地址ID")
            @PathParam("addrId")
                    String addrId
    );

    @ApiOperation(
            value = "更新用户地址",
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

            @ApiParam("地址ID")
            @PathParam("addrId")
                    String addrId,

            AddrUpdateReq addrReq
    );

    @ApiOperation(
            value = "获取用户的所有收货地址"
    )
    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<UniPage<AddrGetResp>> list(

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

            @ApiParam(
                    value = "地址ID列表",
                    allowMultiple = true
            )
            @QueryParam("addrIds")
                    List<String> addrIds
    );


    @ApiOperation(
            value = "获取用户的默认收货"
    )
    @GET
    @Path("/default")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<AddrGetResp> getDefault(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,
            @ApiParam("地址类型")
            @QueryParam("addrType")
                    String addrType
    );


    @ApiOperation(
            value = "设置用户默认地址"
    )
    @PUT
    @Path("/default/{addrId}")
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Void> setDefault(
            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId,

            @ApiParam("地址ID")
            @PathParam("addrId")
                    String addrId,

            @ApiParam("地址类型")
            @QueryParam("addrType")
                    String addrType
    );

}
