package net.kingsilk.qh.oauth.api.s.login.wxMp;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.*;
import net.kingsilk.qh.oauth.api.s.login.*;
import org.springframework.security.access.prepost.*;

import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 *
 */
@Singleton
@Api
@Path("/s/login/wxMp")
@Produces(MediaType.APPLICATION_JSON)
public interface WxMpLoginApi extends QhOAuthStatefulApi {

    @ApiOperation(
            value = "通过用户名、密码登录, 并返回用户 ID。"
    )
    @POST
    @Path("")
    @PreAuthorize("permitAll")
    UniResp<LoginResp> login(

            @ApiParam("微信公众号的 App Id")
            @FormParam("wxMpAppId")
                    String wxMpAppId,

            @ApiParam("微信跳转回来时签发的、用于换取 access token 的一次性 code")
            @FormParam("code")
                    String code,

            @ApiParam("state")
            @FormParam("state")
                    String state
    );
}
