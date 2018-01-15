package net.kingsilk.qh.oauth.api.s.qhLogin;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.*;
import net.kingsilk.qh.oauth.core.*;

import javax.ws.rs.*;

/**
 * 手机验证码登录
 */
@Api
@Path("/qhLogin")
@Deprecated
public interface QhLoginApi extends QhOAuthStatefulApi {


    @ApiOperation(
            value = "检查是否已经登录",
            notes = "注意：该API不直接调用，是通过浏览器相互跳转。"
    )
    @GET
    @Path("/check")
    UniResp<Boolean> check(
            @ApiParam("会话 ID")
            @CookieParam("SESSION")
                    String sessionId,

            @QueryParam("fullyAuth")
            @DefaultValue("false")
                    boolean fullyAuth,

            @ApiParam("登录类型")
            @QueryParam("loginType")
                    LoginTypeEnum loginType,


            @ApiParam("此次登录使用的微信公众号的AppId")
            @QueryParam("wxAppId")
                    String wxAppId,

            @ApiParam("此次登录使用的手机号码")
            @QueryParam("phone")
                    String phone

    );


    /**
     * 检查是否是用手机号码登录的。
     */
    @ApiOperation(
            value = "获取登录前最后一次访问的URL"
    )
    @GET
    @Path("/savedReq")
    UniResp<Boolean> savedReq(
            @ApiParam("会话 ID")
            @CookieParam("SESSION")
                    String sessionId
    );


}
