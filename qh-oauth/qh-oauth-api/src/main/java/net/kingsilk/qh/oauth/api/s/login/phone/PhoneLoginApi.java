package net.kingsilk.qh.oauth.api.s.login.phone;

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
@Path("/s/login/phone")
@Produces(MediaType.APPLICATION_JSON)
public interface PhoneLoginApi extends QhOAuthStatefulApi {


    @ApiOperation(
            value = "通过手机号、短信验证码登录。"
    )
    @POST
    @Path("")
    @PreAuthorize("permitAll")
    UniResp<LoginResp> login(

            @ApiParam("手机号")
            @FormParam("phone")
                    String phone,

            @ApiParam("短信验证码")
            @FormParam("code")
                    String code
    );
}
