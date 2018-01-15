package net.kingsilk.qh.oauth.security;

import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import net.kingsilk.wx4j.client.mp.api.appAt.*;
import net.kingsilk.wx4j.client.mp.api.userAt.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * 代表用户输入的手机号和短信验证码（认证前）。
 */
@Deprecated
public class WxMpAuthenticationEntryPoint implements AuthenticationEntryPoint,
        InitializingBean {

    @Autowired
    private AppAtApi appAtApi;

    @Autowired
    private UserAtApi userAtApi;

    @Autowired
    private WxUserAtStateRepo wxUserAtStateRepo;
//
//    @Autowired
//    private WxComMpUserAuthApi wxComMpUserAuthApi;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        //wxComMpUserAuthApi.createAuthUrl()

        String appId = "wxf9f2b8b1a506c068";
        // 注意：该网址需要在微信公众号管理后台中 网页授权域 中设置的域名保持一致
        String redirectUrl = "https://kingsilk.net/testAuth";


        WxUserAtState state = new WxUserAtState();
        wxUserAtStateRepo.save(state);

        String authUrl = userAtApi.createAuthUrl(appId, redirectUrl, "snsapi_userinfo", state.getId());

        response.sendRedirect(authUrl);

    }
}
