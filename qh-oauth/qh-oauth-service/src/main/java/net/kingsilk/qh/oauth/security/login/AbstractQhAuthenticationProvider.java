package net.kingsilk.qh.oauth.security.login;

import net.kingsilk.qh.oauth.domain.User;
import net.kingsilk.qh.oauth.repo.*;
import net.kingsilk.qh.oauth.security.login.wxMp.*;
import net.kingsilk.qh.oauth.service.*;
import net.kingsilk.wx4j.broker.api.wxMp.user.*;
import net.kingsilk.wx4j.client.mp.api.userAt.*;
import org.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.event.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.session.*;
import org.springframework.web.context.request.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

/**
 * 对用户输入的手机号和短信验证码进行验证。
 */
public abstract class AbstractQhAuthenticationProvider implements AuthenticationProvider,
        InitializingBean,
        MessageSourceAware {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();


    @Autowired
    private SmsRepo smsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserChecker userChecker;

    @Autowired
    private UserAtApi userAtApi;

    @Autowired
    private WxMpUserApi wxMpUserApi;

    private SessionAuthenticationStrategy sessionStrategy; // FIXME

    private RememberMeServices rememberMeServices; // FIXME

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private AuthenticationSuccessHandler successHandler;  // FIXME


    public abstract User doAuth(Authentication authentication);


    /**
     * 验证手机号和验证码。
     *
     * - 验证短信
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(
            Authentication authentication
    ) throws AuthenticationException {

        User user = doAuth(authentication);

        if (user == null) {
            throw new UsernameNotFoundException("未找到用户");
        }

        // 检查用户
        userChecker.check(user);

        UserIdAuthentication authResult = new UserIdAuthentication(user.getId(), null);
        authResult.setDetails(authentication);


        ServletRequestAttributes servletRequsetAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequsetAttributes.getRequest();
        HttpServletResponse response = servletRequsetAttributes.getResponse();


        // 参考 UsernamePasswordAuthenticationFilter 以及父类中的代码
        if (sessionStrategy != null) {
            sessionStrategy.onAuthentication(authResult, request, response);
        }
        SecurityContextHolder.getContext().setAuthentication(authResult);

        if (rememberMeServices != null) {
            rememberMeServices.loginSuccess(request, response, authResult);
        }

        // Fire event
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
                    authResult, this.getClass()));
        }

        if (successHandler != null) {
            try {
                successHandler.onAuthenticationSuccess(request, response, authResult);
            } catch (IOException | ServletException e) {
                throw new RuntimeException(e);
            }
        }

        return authResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (WxMpAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


}
