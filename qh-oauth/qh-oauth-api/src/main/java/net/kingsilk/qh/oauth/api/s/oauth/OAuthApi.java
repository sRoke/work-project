package net.kingsilk.qh.oauth.api.s.oauth;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.*;
import org.springframework.security.access.prepost.*;

import javax.inject.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * 获取 Access Token 的相关方法。
 *
 * @see org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint
 * @see org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerEndpointsConfiguration
 */
@Singleton
@Api
@Path("/s/oauth")
@Produces(MediaType.APPLICATION_JSON)
public interface OAuthApi extends QhOAuthStatefulApi {

    String API_PATH = "/s/oauth";
    String API_PATH_authorize = API_PATH + "/authorize";

    @ApiOperation(
            value = "获取AccessToken",
            notes = "获取AccessToken"
    )
    @POST
    @Path("/authorize")
    @PreAuthorize("isAuthenticated()")
    UniResp<AuthorizeResp> authorize(
            @ApiParam("response_type")
            @FormParam("response_type")
                    String responseType,

            @ApiParam("client_id")
            @FormParam("client_id")
                    String clientId,

            @ApiParam("redirect_uri")
            @FormParam("redirect_uri")
                    String redirectUri,

            @ApiParam("scope")
            @FormParam("scope")
                    String scope,

            @ApiParam("state")
            @FormParam("state")
                    String state
    );


    /*

# 请求授权 ----------------- code
GET /oauth/authorize
        ?response_type=code
        &client_id=xxx
        &redirect_uri=xxx
        &scope=xxx
        &state=xxx

# 授权返回
GET redirect_uri
        ?code=xxx
        &state=xxx

# 换取 AT (仅当第一步的 response_type=code 才有该步骤 )
POST /oauth/token
        grant_type=xxx
        &code=xxx
        &redirect_uri=xxx
        &client_id=xxx


# 请求授权 ----------------- token
GET /oauth/authorize
    ?response_type=token
    &client_id=xxx
    &redirect_uri=xxx
    &scope=xxx
    &state=xxx

# 授权返回
302 GET redirect_uri
        #/xxx/yyy
        ?code=xxx
        &state=xxx

-------------------------- 得到 AT 之后， resource Server 要验证 AT
POST /oauth/check_token?token=xxx
Authrorization: Basic xxxxxx
 */

}
