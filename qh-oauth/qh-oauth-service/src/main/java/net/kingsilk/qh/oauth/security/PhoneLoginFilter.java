package net.kingsilk.qh.oauth.security;

import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.domain.User;
import net.kingsilk.qh.oauth.repo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.event.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.User.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

import static com.querydsl.core.types.dsl.Expressions.*;

/**
 * 该filter处理的URL应该是用通过手机号和验证码登录。
 *
 * 只处理 GET /phoneLogin
 *
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 *
 * 1. 根据参数中的手机号和验证码去 “验证码” 表中查询，验证验证码的真实性。
 * 2. 创建新用户/或查找已经绑定该手机号的老用户
 * 3. 免密码认证
 */
@Component
@Deprecated
public class PhoneLoginFilter extends AbstractAuthenticationProcessingFilter {

    static final String SPRING_SECURITY_FORM_PHONE_KEY = "phone";
    static final String SPRING_SECURITY_FORM_VERIFYCODE_KEY = "verifyCode";
    static final String NO_PASSWORD = "N/A";
    private String phoneParameter = SPRING_SECURITY_FORM_PHONE_KEY;
    private String verifyCodeParameter = SPRING_SECURITY_FORM_VERIFYCODE_KEY;
    private boolean postOnly = true;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SmsRepo smsRepo;

    // ~ Constructors
    // ===================================================================================================


    PhoneLoginFilter() {
        super(new AntPathRequestMatcher("/phoneLogin", "GET"));
    }

    // ~ Methods
    // ========================================================================================================

    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException, UsernameNotFoundException {
        String register = request.getParameter("register");
        String backUrl = request.getParameter("backUrl");
        String clientId = request.getParameter("clientId");
        String phone = obtainPhone(request);
        String verifyCode = obtainVerifyCode(request);
        if (phone == null) {
            phone = "";
        }

        if (verifyCode == null) {
            verifyCode = "";
        }

        phone = phone.trim();
        verifyCode = verifyCode.trim();
        //验证验证码的真实性
        Sms sms = smsRepo.findOne(
                allOf(
                        QSms.sms.phone.eq(phone),
                        QSms.sms.verifyCode.eq(verifyCode),
                        QSms.sms.valid.eq(true),
                        QSms.sms.codeExpiredTime.after(new Date())
                )
        );
        if (sms == null) {
            throw new UsernameNotFoundException("手机号或验证码错误");
        }
        //查找用户是否存在
        User user = userRepo.findOne(
                allOf(
                        QUser.user.phone.eq(phone)
                )
        );
        if (user == null) {
            //用户不存在 带了注册参数
            if (!StringUtils.isEmpty(register)) {
                user = new User();
                user.setPhone(phone);
                user.setUsername("u" + phone);
                user.setDateCreated(new Date());
                userRepo.save(user);
            } else {
                throw new UsernameNotFoundException("手机号或验证码错误");
            }
        }

        sms.setValid(false);
        smsRepo.save(sms);

        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority("LOGIN");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(grantedAuthority);

        //使用用户的id作为用户名
        UserBuilder ub = org.springframework.security.core.userdetails.User.withUsername(user.getId());
        ub.password(NO_PASSWORD);
        ub.authorities(authorities);
        UserDetails userDetails = ub.build();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                userDetails, NO_PASSWORD, authorities));

        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Enables subclasses to override the composition of the password, such as by
     * including additional values and a separator.
     * <p>
     * This might be used for example if a postcode/zipcode was required in addition to
     * the password. A delimiter such as a pipe (|) should be used to separate the
     * password and extended value(s). The <code>AuthenticationDao</code> will need to
     * generate the expected password in a corresponding manner.
     * </p>
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the password that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    protected String obtainVerifyCode(HttpServletRequest request) {
        return request.getParameter(verifyCodeParameter);
    }

    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the username that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    protected String obtainPhone(HttpServletRequest request) {
        return request.getParameter(phoneParameter);
    }


    public void setPhoneParameter(String phoneParameter) {
        Assert.hasText(phoneParameter, "phone Parameter must not be empty or null");
        this.phoneParameter = phoneParameter;
    }


    public void setVerifyCodeParameter(String verifyCodeParameter) {
        Assert.hasText(verifyCodeParameter, "verifyCode Parameter must not be empty or null");
        this.verifyCodeParameter = verifyCodeParameter;
    }

    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getPhoneParameter() {
        return phoneParameter;
    }

    public final String getVerifyCodeParameter() {
        return verifyCodeParameter;
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }

    public void setUserRepo(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    public SmsRepo getSmsRepo() {
        return smsRepo;
    }

    public void setSmsRepo(SmsRepo smsRepository) {
        this.smsRepo = smsRepository;
    }

    @Override
    public void afterPropertiesSet() {
        /* Assert.notNull(authenticationManager, "authenticationManager must be specified");*/
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success. Updating SecurityContextHolder to contain: "
                    + authResult);
        }
        SecurityContextHolder.getContext().setAuthentication(authResult);
        //rememberMeServices.loginSuccess(request, response, authResult); // FIXME
        SavedRequestAwareAuthenticationSuccessHandler handler = (SavedRequestAwareAuthenticationSuccessHandler) getSuccessHandler();
        // Fire event
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
                    authResult, this.getClass()));
        }
        //handler.onAuthenticationSuccess(request, response, authResult);

        DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        // 清除认证异常
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        StringBuffer buffer = new StringBuffer();
        buffer.append("/oauth/authorize?v1=1");
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getParameter(key);
            buffer.append("&").append(key).append("=").append(value);
        }

        String targetUrl = buffer.toString();
        //跳转
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}
