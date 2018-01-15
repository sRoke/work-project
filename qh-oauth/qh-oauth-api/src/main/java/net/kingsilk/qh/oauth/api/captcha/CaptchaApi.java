package net.kingsilk.qh.oauth.api.captcha;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;

import javax.ws.rs.*;

/**
 * 图片验证码API.
 */
@Api
@Path("/captcha")
public interface CaptchaApi extends QhOAuthApi {

    @ApiOperation(
            value = "按要求生成一个图片验证码, 并返回ID"
    )
    @GET
    @Path("/gen/{w}/{h}/{style}/{words}")
    UniResp<String> gen(

            @ApiParam("验证码图片的宽度，单位：px")
            @PathParam("w")
            @DefaultValue("200")
                    int w,

            @ApiParam("验证码图片的高度，单位：px")
            @PathParam("h")
            @DefaultValue("60")
                    int h,

            @ApiParam("预定义风格类别")
            @PathParam("style")
            @DefaultValue("1")
                    String style,

            @ApiParam("候选词。有一些预定义类别。")
            @PathParam("words")
            @DefaultValue("1")
                    String words
    );

    @ApiOperation(
            value = "根据ID获取图片验证码图片"
    )
    @GET
    @Path("/view/{captchaId}")
    byte[] view(
            @ApiParam("图片验证码的ID")
            @PathParam("captchaId")
                    String captchaId
    );


    @ApiOperation(
            value = "验证验证码是否正确",
            notes = "该验证方法不开放给浏览器端应用（含SPA应用），手机APP应用。"
    )
    @GET
    @Path("/verify/{captchaId}")
    UniResp<Boolean> verify(

            @ApiParam("图片验证码的ID")
            @PathParam("captchaId")
                    String captchaId,

            @ApiParam("用户输入的验证码中的文本内容")
            @QueryParam("txt")
                    String txt
    );
}
