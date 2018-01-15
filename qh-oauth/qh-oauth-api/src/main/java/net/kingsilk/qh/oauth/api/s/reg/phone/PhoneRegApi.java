package net.kingsilk.qh.oauth.api.s.reg.phone;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.*;
import net.kingsilk.qh.oauth.api.s.login.*;
import org.springframework.security.access.prepost.*;

import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * 通过手机号注册。
 */
@Singleton
@Api
@Path("/s/reg/phone")
@Produces(MediaType.APPLICATION_JSON)
public interface PhoneRegApi extends QhOAuthStatefulApi {

    @ApiOperation(
            value = "通过手机号验证码注册新用户"
    )
    @POST
    @Path("")
    @PreAuthorize("permitAll")
    UniResp<RegResp> reg(
            @ApiParam("手机号")
            @QueryParam("phone")
                    String phone,

            @ApiParam("短信验证码")
            @FormParam("code")
                    String code
    );
}
