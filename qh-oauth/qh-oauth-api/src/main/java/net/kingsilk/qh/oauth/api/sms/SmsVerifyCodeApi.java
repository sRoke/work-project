package net.kingsilk.qh.oauth.api.sms;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.core.*;

import javax.ws.rs.*;

/**
 * 手机验证码登录
 */
@Api
@Path("/sms/verifyCode")
public interface SmsVerifyCodeApi extends QhOAuthApi {

    /**
     * 发送登录短信验证码。
     *
     * 内部实现逻辑必须检查 fingerPrint, clientIp, 同一个手机号等频率限制。
     */
    @ApiOperation(
            value = "发送登录短信验证码"
    )
    @POST
    @Path("/send")
    UniResp<String> send(

//            @ApiParam("短信签名者标识")
//            @QueryParam("signer")
//                    String signer,

            @ApiParam("短信验证码类型")
            @FormParam("type")
                    SmsVerifyCodeTypeEnum type,

            @ApiParam("手机号")
            @FormParam("phone")
                    String phone

//            @ApiParam("客户端应用（SPA，手机APP）给出的代表用户端环境的 finger print。" +
//                    "必须足够唯一。可选。可参考 Fingerprintjs2")
//            @QueryParam("fingerPrint")
//                    String fingerPrint,
//
//            @ApiParam("客户端IP地址。")
//            @QueryParam("clientIp")
//                    String clientIp,

//            @ApiParam("图片验证码ID")
//            @QueryParam("captchaId")
//                    String captchaId,

//            @ApiParam("用户输入的图片验证码文本")
//            @QueryParam("captchaTxt")
//                    String captchaTxt
    );

    @ApiOperation(
            value = "验证登录短信验证码"
    )
    @POST
    @Path("/verify/loginCode")
    UniResp<String> verifyLoginCode(

            @ApiParam("手机号")
            @QueryParam("phone")
                    String phone,

            @ApiParam("用户输入的手机验证码")
            @QueryParam("phoneCode")
                    String phoneCode
    );

}
