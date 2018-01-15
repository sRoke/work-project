package net.kingsilk.qh.oauth.api.s.phoneLogin;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.*;

import javax.ws.rs.*;

/**
 * 手机验证码登录
 */
@Api
@Path("/phoneLogin")
@Deprecated
public interface PhoneLoginApi extends QhOAuthStatefulApi {

    /**
     * 检查是否是用手机号码登录的。
     */
    @ApiOperation(
            value = "检查是否已经（使用指定的手机号）登录，若没登录则跳转到手机验证码登录画面，否则会到给定的 回退地址。",
            notes = "注意：该API不直接调用，是通过浏览器相互跳转。"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 302,
                    message = "" +
                            "1. 如果当前会话尚未登录，或登录方式与参数要求不匹配，则重新跳转到微信登录授权画面进行认证。" +
                            "2. 如果已经登录，则按照以下顺序跳转到对应的网址：" +
                            "(1) session 中有登录前要访问的的地址，" +
                            "(2) URL参数中给定的返回的地址" +
                            "(3) 当前应用的主页"
            )
    })
    @GET
    @Path("/check")
    UniResp<Void> check(
            @ApiParam("会话 ID")
            @CookieParam("SESSION")
                    String sessionId,

            @ApiParam("手机号。可选，否则只判断是否已经登录")
            @QueryParam("phone")
                    String phone,

            @QueryParam("fullyAuth")
            @DefaultValue("false")
                    boolean fullyAuth,

            @ApiParam("如果没有登录，则登录授权返回后，是否自动创建用户")
            @QueryParam("createUser")
            @DefaultValue("true")
                    boolean createUser,

            @ApiParam("如果没有登录，则登录授权返回后，返回的URL")
            @QueryParam("backUrl")
                    String backUrl

    );

    @ApiOperation(
            value = "使用手机号，手机短信验证码进行登录"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 302,
                    message = "" +
                            "1. 如果验证码OK，则：" +
                            "1.1 先按参数要求条件创建用户" +
                            "1.2 则按照以下顺序跳转到对应的网址：" +
                            "(1) session 中有登录前要访问的的地址，" +
                            "(2) URL参数中给定的返回的地址" +
                            "(3) 当前应用的主页"
                            + "2. 返回手机短信登录表单画面"
            )
    })
    @POST
    @Path("/verify")
    UniResp<Void> verify(

            @ApiParam("手机号")
            @QueryParam("phone")
                    String phone,

            @ApiParam("用户输入的手机验证码")
            @QueryParam("phoneCode")
                    String phoneCode,

            @ApiParam("如果没有登录，则登录授权返回后，是否自动创建用户")
            @QueryParam("createUser")
            @DefaultValue("true")
                    boolean createUser,

            @ApiParam("如果没有登录，则登录授权返回后，返回的URL")
            @QueryParam("backUrl")
                    String backUrl
    );

    @ApiOperation(
            value = "使用手机号，手机短信验证码进行登录"
    )
    @POST
    @Path("/ajaxVerify")
    UniResp<Boolean> ajaxVerify(

            @ApiParam("手机号")
            @QueryParam("phone")
                    String phone,

            @ApiParam("用户输入的手机验证码")
            @QueryParam("phoneCode")
                    String phoneCode,

            @ApiParam("如果没有登录，则登录授权返回后，是否自动创建用户")
            @QueryParam("createUser")
            @DefaultValue("true")
                    boolean createUser
    );

}
