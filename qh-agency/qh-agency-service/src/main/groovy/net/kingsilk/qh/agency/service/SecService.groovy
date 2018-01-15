package net.kingsilk.qh.agency.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.AuthenticationTrustResolver
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest

/**
 * 参考 grails 插件 spring-security-core 的相关代码。
 *
 * @see grails.plugin.springsecurity.SpringSecurityService
 * @see grails.plugin.springsecurity.SpringSecurityUtils
 */
@Service
class SecService {
    final Logger log = LoggerFactory.getLogger(getClass());
    private static final String NO_PASSWORD = "N/A"

    @Autowired
    PasswordEncoder passwordEncoder;
    //@Autowired
    AuthenticationTrustResolver authenticationTrustResolver

    /**
     * 获取当前登录用户的名称（实际是 User#id）
     * @param curAuth
     * @return
     */
    private String getUsername(Authentication curAuth) {

        if (curAuth == null) {
            return null
        }

        if (curAuth instanceof AnonymousAuthenticationToken) {
            return null
        }

        if (curAuth instanceof UsernamePasswordAuthenticationToken) {
            UsernamePasswordAuthenticationToken passwdAuth = (UsernamePasswordAuthenticationToken) curAuth
            if (passwdAuth.principal instanceof String) {
                return (String) passwdAuth.principal
            }

            if (passwdAuth.principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) passwdAuth.principal
                return userDetails.getUsername()
            }
        }


        if (curAuth instanceof OAuth2Authentication) {
            OAuth2Authentication oauthAuth = (OAuth2Authentication) curAuth
            return getUsername(oauthAuth.userAuthentication)
        }

        log.warn("未处理的 Authentication 类型 : ${curAuth.getClass()}")
        return null
    }

    /**
     * 返回当前登录用户的用户ID
     * @return
     */
    String curUserId() {
        return getUsername(SCH.context.authentication)
    }

    static boolean isAjax(final HttpServletRequest request) {

        String xmlHttpRequest = "XMLHttpRequest";

        // check the current request's headers
        if (xmlHttpRequest == request.getHeader("X-Requested-With")) {
            return true;
        }

        return false;
    }

    boolean isLoggedIn() {
        def authentication = SCH.context.authentication
        authentication && !authenticationTrustResolver.isAnonymous(authentication)
    }

    /**
     * 判断密码是否正确。
     *
     * @param rawPassword 用户提供的明文密码
     * @param encodedPassword 数据库中的加密后的密码
     * @return 是否正确。true - 正确
     */
    boolean isRightPassword(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }

    /**
     * 判断密码是否是空密码
     * @param encodedPassword 数据库中的加密后的密码
     * @return true - 空密码
     */
    boolean isBlankPassword(
            String encodedPassword
    ) {
        return passwordEncoder.matches(NO_PASSWORD, encodedPassword)
    }

    /**
     * 对用户提供的密码进行加密。
     *
     * @param rawPassword 用户提供的明文密码
     * @return 加密后的密码
     */
    String encodePassword(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword)
    }

}
