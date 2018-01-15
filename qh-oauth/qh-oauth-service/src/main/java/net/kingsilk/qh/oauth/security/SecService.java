package net.kingsilk.qh.oauth.security;

import net.kingsilk.qh.oauth.security.login.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.oauth2.provider.*;
import org.springframework.stereotype.*;

import javax.servlet.http.*;

/**
 * 参考 grails 插件 spring-security-core 的相关代码。
 *
 * @see grails.plugin.springsecurity.SpringSecurityService
 * @see grails.plugin.springsecurity.SpringSecurityUtils
 */
@Service
public class SecService {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public static final String NO_PASSWORD = "N/A";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;


    private String getUsername(Authentication curAuth) {

        if (curAuth == null) {
            return null;
        }

        if (curAuth instanceof AnonymousAuthenticationToken) {
            return null;
        }

        if (curAuth instanceof UserIdAuthentication) {
            return (String) curAuth.getPrincipal();
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

        log.warn("未处理的 Authentication 类型 : " + curAuth.getClass());
        return null;
    }


    public String curUserId() {
        return getUsername(SecurityContextHolder.getContext().getAuthentication());
    }

    public void reauthenticate(final String username) {
        reauthenticate(username, null);
    }

    public void reauthenticate(final String username, final String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                userDetails, password == null ? "N/A" : password, userDetails.getAuthorities()));

    }

    public static boolean isAjax(final HttpServletRequest request) {

        // redirectToWx the current request's headers
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            return true;
        }

        return false;
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !authenticationTrustResolver.isAnonymous(authentication);
    }

    /**
     * 判断密码是否正确。
     *
     * @param rawPassword 用户提供的明文密码
     * @param encodedPassword 数据库中的加密后的密码
     * @return 是否正确。true - 正确
     */
    public boolean isRightPassword(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 判断密码是否是空密码
     * @param encodedPassword 数据库中的加密后的密码
     * @return true - 空密码
     */
    public boolean isBlankPassword(
            String encodedPassword
    ) {
        return passwordEncoder.matches(NO_PASSWORD, encodedPassword);
    }

    /**
     * 对用户提供的密码进行加密。
     *
     * @param rawPassword 用户提供的明文密码
     * @return 加密后的密码
     */
    public String encodePassword(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
