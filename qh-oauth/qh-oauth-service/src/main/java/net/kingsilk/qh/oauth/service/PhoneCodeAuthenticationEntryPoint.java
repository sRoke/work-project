package net.kingsilk.qh.oauth.service;

import org.springframework.beans.factory.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * 代表用户输入的手机号和短信验证码（认证前）。
 */
@Deprecated
public class PhoneCodeAuthenticationEntryPoint implements AuthenticationEntryPoint,
        InitializingBean {


    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // AJAX 请求：返回
    }
}
