package net.kingsilk.qh.oauth.api.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.oauth.api.QhOAuthApi;
import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.s.user.dto.InfoResp;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 *
 */
@Api
@Path("/user")
public interface UserApi extends QhOAuthApi {

    @ApiOperation(
            value = "为用户添加收货地址"
    )
    @GET
    @Path("/{userId}")
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<UserGetResp> get(

            @ApiParam("用户ID")
            @PathParam("userId")
                    String userId
    );


    @ApiOperation(
            value = "获取多个品牌商信息"
    )
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<UniPage<UserGetResp>> list(

            @ApiParam(value = "每页多少个记录,最大100")
            @QueryParam("size")
            @DefaultValue("10")
                    Integer size,

            @ApiParam(value = "页码。从0开始")
            @QueryParam("page")
            @DefaultValue("0")
                    Integer page,

            @ApiParam(
                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、 '+'、'desc'、'-'。可以传递多个 sort 参数",
                    allowMultiple = true,
                    example = "age,asc"
            )
            @QueryParam("sort")
                    List<String> sort,


            @ApiParam(value = "用户ID", allowMultiple = true)
            @QueryParam("userIds")
                    List<String> userIds

    );


    @ApiOperation(
            value = "获取多个品牌商信息"
    )
    @GET
    @Path("/search")
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<UniPage<UserGetResp>> search(

            @ApiParam(value = "每页多少个记录,最大100")
            @QueryParam("size")
            @DefaultValue("10")
                    Integer size,

            @ApiParam(value = "页码。从0开始")
            @QueryParam("page")
            @DefaultValue("0")
                    Integer page,

            @ApiParam(
                    value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、 '+'、'desc'、'-'。可以传递多个 sort 参数",
                    allowMultiple = true,
                    example = "age,asc"
            )
            @QueryParam("sort")
                    List<String> sort,


            @ApiParam(value = "查询语句")
            @QueryParam("q")
                    String q

    );

    @ApiOperation(
            value = "oauth 在其他项目user新增"
    )
    @POST
    @Path("/oauth/addUser")
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<String> addUser(
            AddUserReq addUserReq
    );

    @ApiOperation(
            value = "获取当前用户信息"
    )
    @GET
    @Path("/info")
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<UserGetResp> info();

    @ApiOperation(
            value = "更新用户信息"
    )
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<String> update(
            AddUserReq addUserReq
    );

    @ApiOperation(
            value = "获取用户/客户端信息"
    )
    @GET
    @Path("/getInfoByUserId")
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<UserGetResp> getInfoByUserId(
            @ApiParam("用户id")
            @QueryParam("userId")
                    String userId
    );

    @ApiOperation(
            value = "获取用户/客户端信息"
    )
    @GET
    @Path("/getInfoByPhone")
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<UserGetResp> getInfoByPhone(
            @ApiParam("用户id")
            @QueryParam("phone")
                    String phone
    );

    @ApiOperation(
            value = "获取用户/客户端信息"
    )
    @GET
    @Path("/getOpenIdByUserId")
    @Produces({MediaType.APPLICATION_JSON})
    UniResp<String> getOpenIdByUserId(
            @ApiParam("用户信息")
            @QueryParam("userId")
                    String userId
    );

}
