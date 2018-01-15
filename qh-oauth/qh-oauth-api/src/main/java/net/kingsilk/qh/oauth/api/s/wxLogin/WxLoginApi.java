package net.kingsilk.qh.oauth.api.s.wxLogin;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.s.*;

import javax.ws.rs.*;

//import net.kingsilk.qh.oauth.api.wxLogin.dto.*;

/**
 *
 */
@Api
@Path("/s/wx/login")
@Deprecated
public interface WxLoginApi extends QhOAuthStatefulApi {

//    @ApiOperation(
//            value = "前会话是否是按照指定的要求进行登录的",
//            notes = "该API需要通过 access Token 访问。"
//    )
//    @Path("/wx/{wxAppId}/check")
//    UniResp<WxLoginCheckResp> check(
//
//            @ApiParam("此次登录使用的微信公众号的AppId")
//            @PathParam("wxAppId")
//                    String wxAppId,
//
//            @ApiParam("此次登录是否是全新登录（非匿名登录，非 REMEMBER-ME 登录")
//            @QueryParam("fullyAuth")
//                    boolean fullyAuth
//    );

    @ApiOperation(
            value = "检查微信是否按照指定要求登录，未登录则跳转至微信服务器，若已登录则返回到特定的URL。",
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
    @Path("/wx/{wxAppId}/app/check")
    void wxAppCheck(

            @ApiParam("会话 ID")
            @CookieParam("SESSION")
                    String sessionId,

            @ApiParam("此次登录使用的微信公众号的AppId")
            @PathParam("wxAppId")
                    String wxAppId,

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
            value = "微信用户授权后的回调URL。",
            notes = "注意：该API不直接调用，是通过浏览器相互跳转。" +
                    "需要参考 《微信公众平台技术文档》 - 微信网页开发 - 微信网页授权。"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 302,
                    message = "" +
                            "1. 如果微信用户授权OK，则：" +
                            "1.1 先按参数要求条件创建用户" +
                            "1.2 则按照以下顺序跳转到对应的网址：" +
                            "(1) session 中有登录前要访问的的地址，" +
                            "(2) URL参数中给定的返回的地址" +
                            "(3) 当前应用的主页"
            ),
            @ApiResponse(
                    code = 500,
                    message = "" +
                            "微信认证授权出错，显示错误画面"
            )
    })
    @Path("/wx/{wxAppId}/app/auth")
    void wxAppAuth(

            @ApiParam("会话 ID")
            @CookieParam("SESSION")
                    String sessionId,

            @ApiParam("此次登录使用的微信公众号的AppId")
            @PathParam("wxAppId")
                    String wxAppId,

            @ApiParam("根据微信API - 换取代表用户授权的 access token 的一次性 code")
            @QueryParam("code")
                    String code,

            @ApiParam("根据微信API - state （防止 CSFR 的 token ）")
            @QueryParam("state")
                    String state,

            @QueryParam("createUser")
            @DefaultValue("true")
                    boolean createUser,

            @QueryParam("backUrl")
                    String backUrl

    );


    @ApiOperation(
            value = "检查用户是否已经按要求登录，若未登录则跳转到微信扫码授权的URL，否则跳转回特定的URL。"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 302,
                    message = "" +
                            "1. 如果当前会话尚未登录，或登录方式与参数要求不匹配，" +
                            "   则重新跳转到微信登录授权画面进行认证。" +
                            "2. 如果已经登录，则按照以下顺序跳转到对应的网址：" +
                            "(1) session 中有登录前要访问的的地址，" +
                            "(2) URL参数中给定的返回的地址" +
                            "(3) 当前应用的主页"
            )
    })
    @Path("/wx/{wxAppId}/scan/check")
    void wxScanCheck(

            @ApiParam("会话 ID")
            @CookieParam("SESSION")
                    String sessionId,

            @ApiParam("此次登录使用的微信公众号的AppId")
            @PathParam("wxAppId")
                    String wxAppId,

            @ApiParam("是否要全新登录（非匿名登录，非 REMEMBER-ME 登录")
            @QueryParam("fullyAuth")
            @DefaultValue("false")
                    boolean fullyAuth,

            @QueryParam("createUser")
            @DefaultValue("true")
                    boolean createUser,

            @ApiParam("如果没有登录，则登录授权返回后，返回的URL")
            @QueryParam("backUrl")
                    String backUrl

    );

    @ApiOperation(
            value = "通过微信APP扫描在电脑浏览器上的授权二维码进行登录",
            notes = "注意：该API不直接调用，是通过浏览器相互跳转。" +
                    "需要参考 《微信·开放平台》- 网站应用 - 微信登录 - 网站应用微信登录开发指南"
    )
    @ApiResponses({
            @ApiResponse(
                    code = 302,
                    message = "" +
                            "1. 如果微信用户授权OK，则：" +
                            "1.1 先按参数要求条件创建用户" +
                            "1.2 则按照以下顺序跳转到对应的网址：" +
                            "(1) session 中有登录前要访问的的地址，" +
                            "(2) URL参数中给定的返回的地址" +
                            "(3) 当前应用的主页"
            ),
            @ApiResponse(
                    code = 500,
                    message = "" +
                            "微信认证授权出错，显示错误画面"
            )
    })
    @Path("/wx/{wxAppId}/scan/auth")
    void wxScanAuth(

            @ApiParam("会话 ID")
            @CookieParam("SESSION")
                    String sessionId,

            @ApiParam("此次登录使用的微信公众号的AppId")
            @PathParam("wxAppId")
                    String wxAppId,

            @ApiParam("是否要全新登录（非匿名登录，非 REMEMBER-ME 登录")
            @QueryParam("fullyAuth")
                    boolean fullyAuth,

            @QueryParam("createUser")
                    boolean createUser,

            @QueryParam("backUrl")
                    String backUrl

    );
}
