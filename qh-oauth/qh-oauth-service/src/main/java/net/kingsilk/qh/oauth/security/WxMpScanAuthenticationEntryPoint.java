package net.kingsilk.qh.oauth.security;

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
 * 微信扫码登录。
 */
@Deprecated
public class WxMpScanAuthenticationEntryPoint implements AuthenticationEntryPoint,
        InitializingBean {

    @Autowired
    private AppAtApi appAtApi;

    @Autowired
    private UserAtApi userAtApi;

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        String appId = "wxf9f2b8b1a506c068";
        // 注意：该网址需要在微信公众号管理后台中 网页授权域 中设置的域名保持一致
        String redirectUrl = "https://kingsilk.net/testAuth";

        String authUrl = userAtApi.createAuthUrl(appId, redirectUrl, "snsapi_userinfo", "randomStr99898");

        response.sendRedirect(authUrl);

    }
}
