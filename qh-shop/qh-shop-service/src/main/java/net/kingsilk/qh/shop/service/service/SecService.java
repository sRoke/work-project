package net.kingsilk.qh.shop.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 参考 grails 插件 spring-security-core 的相关代码。
 *
// * @see grails.plugin.springsecurity.SpringSecurityService
// * @see grails.plugin.springsecurity.SpringSecurityUtils
 */
@Service
public class SecService {
    final Logger log = LoggerFactory.getLogger(getClass());


    //@Autowired
    AuthenticationTrustResolver authenticationTrustResolver;

    /**
     * 获取当前登录用户的名称（实际是 User#id）
     * @param curAuth
     * @return
     */
    private String getUsername(Authentication curAuth) {

        if (curAuth == null) {
            return null;
        }

        if (curAuth instanceof AnonymousAuthenticationToken) {
            return null;
        }

        if (curAuth instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken passwdAuth = (UsernamePasswordAuthenticationToken) curAuth;
            if (passwdAuth.getPrincipal() instanceof String) {
                return (String) passwdAuth.getPrincipal();
            }

            if (passwdAuth.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) passwdAuth.getPrincipal();
                return userDetails.getUsername();
            }
        }


        if (curAuth instanceof OAuth2Authentication) {
            OAuth2Authentication oauthAuth = (OAuth2Authentication) curAuth;
            return getUsername(oauthAuth.getUserAuthentication());
        }

        log.warn("未处理的 Authentication 类型 : ${curAuth.getClass()}");
        return null;
    }

    /**
     * 返回当前登录用户的用户ID
     * @return
     */
    public String curUserId() {
        return getUsername(SecurityContextHolder.getContext().getAuthentication());
    }

    public static boolean isAjax(final HttpServletRequest request) {

        String xmlHttpRequest = "XMLHttpRequest";

        // check the current request's headers
        if (Objects.equals(xmlHttpRequest, request.getHeader("X-Requested-With"))) {
            return true;
        }

        return false;
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication!=null && !authenticationTrustResolver.isAnonymous(authentication);
    }

}
