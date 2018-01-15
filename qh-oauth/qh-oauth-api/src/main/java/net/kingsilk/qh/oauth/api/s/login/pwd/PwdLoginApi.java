package net.kingsilk.qh.oauth.api.s.login.pwd;

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
@Path("/s/login/pwd")
@Produces(MediaType.APPLICATION_JSON)
public interface PwdLoginApi extends QhOAuthStatefulApi {

    String API_PATH = "/s/login/pwd";
    String API_PATH_login = API_PATH;

    @ApiOperation(
            value = "通过用户名、密码登录, 并返回用户 ID。"
    )
    @POST
    @Path("")
    @PreAuthorize("permitAll")
    UniResp<LoginResp> login(
            @ApiParam("用户名、手机号、电子邮箱")
            @FormParam("username")
                    String username,

            @ApiParam("密码")
            @FormParam("password")
                    String password
    );
}
