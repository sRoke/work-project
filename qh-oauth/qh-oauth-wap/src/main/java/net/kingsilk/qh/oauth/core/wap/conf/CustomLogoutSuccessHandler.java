package net.kingsilk.qh.oauth.core.wap.conf;

import net.kingsilk.qh.oauth.security.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.logout.*;
import org.springframework.stereotype.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        // 如果是 Ajax 请求，则返回 JSON
        if (SecService.isAjax(request)) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write("{\"status\":200}");
            return;
        }

        // 调用 父级处理，详情请参考  AbstractAuthenticationTargetUrlRequestHandler#determineTargetUrl
        super.onLogoutSuccess(request, response, authentication);
    }
}