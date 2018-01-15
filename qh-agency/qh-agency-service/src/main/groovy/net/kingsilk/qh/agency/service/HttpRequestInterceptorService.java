package net.kingsilk.qh.agency.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Created by yuank on 17/8/30.
 */
public class HttpRequestInterceptorService implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        String userAt = getUserAt(SecurityContextHolder.getContext().getAuthentication());

        if (StringUtils.hasText(userAt)) {
            request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + userAt);
        }
        return execution.execute(request, body);
    }

    String getUserAt(Authentication curAuth) {

        if (curAuth instanceof OAuth2Authentication) {
            OAuth2Authentication oauthAuth = (OAuth2Authentication) curAuth;
            OAuth2AuthenticationDetails details= (OAuth2AuthenticationDetails)oauthAuth.getDetails();
            return details.getTokenValue();
        }
        return null;

    }
}
