package net.kingsilk.qh.oauth.api.s.auth.phone;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.*;
import net.kingsilk.qh.oauth.api.s.login.*;
import org.springframework.security.access.prepost.*;

import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * 仅仅认证用户持有给定的手机号，并将认证结果保存在 session 中。
 */
@Singleton
@Api
@Path("/s/auth/phone")
@Produces(MediaType.APPLICATION_JSON)
public interface PhoneAuthApi extends QhOAuthStatefulApi {

    @ApiOperation(
            value = "验证用户持有某个手机号（可以是非登录状态）。"
    )
    @POST
    @Path("")
    @PreAuthorize("permitAll")
    UniResp<Void> auth(

            @ApiParam("手机号")
            @QueryParam("phone")
                    String phone,

            @ApiParam("短信验证码")
            @FormParam("code")
                    String code
    );
}
