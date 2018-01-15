package net.kingsilk.qh.oauth.api.s.user;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.*;
import net.kingsilk.qh.oauth.api.s.user.dto.*;
import org.springframework.security.access.prepost.*;

import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 * 获取当前Session中用户信息相关API。
 */
@Singleton
@Api
@Path("/s/user")
@Produces({MediaType.APPLICATION_JSON})
public interface UserApi extends QhOAuthStatefulApi {


    @ApiOperation(
            value = "获取当前用户信息"
    )
    @GET
    @Path("/info")
    @PreAuthorize("isAuthenticated()")
    UniResp<InfoResp> info();

    @ApiOperation(
            value = "绑定手机号"
    )
    @POST
    @Path("/bind/phone")
    @PreAuthorize("isAuthenticated()")
    UniResp<Void> bindPhone(

            @ApiParam("电话号码")
            @QueryParam("phone")
                    String phone,

            @ApiParam("验证码")
            @QueryParam("code")
                    String code
    );

    @ApiOperation(
            value = "尝试从session中绑定之前已经微信登录的信息"
    )
    @POST
    @Path("/bind/sessionWxMp")
    @PreAuthorize("isAuthenticated()")
    UniResp<BindSessionWxMpResp> bindSessionWxMp(
    );

    @ApiOperation(
            value = "绑定微信号"
    )
    @POST
    @Path("/bind/wxMp")
    @PreAuthorize("isAuthenticated()")
    UniResp<Void> bindWxMp(
            @ApiParam("微信公众号 APP ID")
            @QueryParam("wxMpAppId")
                    String wxMpAppId,

            @ApiParam("换取用户 access token 的一次性 code")
            @QueryParam("code")
                    String code,

            @ApiParam("state")
            @QueryParam("state")
                    String state

    );


    @ApiOperation(
            value = "保存用户信息"
    )
    @POST
    @Path("/save")
    @PreAuthorize("isAuthenticated()")
    UniResp<String> save(
            UserAddreq userAddreq
    );


    @ApiOperation(
            value = "保存用户信息"
    )
    @PUT
    @Path("/update")
    @PreAuthorize("isAuthenticated()")
    UniResp<String> update(
            UserAddreq userAddreq
    );

    @ApiOperation(
            value = "用户信息列表"
    )
    @GET
    @PreAuthorize("isAuthenticated()")
    UniResp<List<UserInfoResp>> list(
            @ApiParam("用户id")
            @QueryParam("userIds")
                    List<String> userIds
    );

    @ApiOperation(
            value = "用户id列表")
    @GET
    @Path("/search")
    @PreAuthorize("isAuthenticated()")
    UniResp<List<String>> search(
            @ApiParam("关键字")
            @QueryParam("keyWords")
                    String keyWords
    );

}
