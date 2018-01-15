package net.kingsilk.qh.oauth.security.login.wxComMp;

import net.kingsilk.qh.oauth.api.s.attr.WxComMpAuthInfo;
import net.kingsilk.qh.oauth.api.s.login.wxComMp.*;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.security.login.*;
import net.kingsilk.qh.oauth.service.*;
import net.kingsilk.wx4j.broker.api.*;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.auth.*;
import org.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.context.request.*;

import java.util.*;

/**
 * 第三方微信代管微信公众号的用户登录授权。
 */
@Service
public class WxComMpAuthenticationProvider implements AuthenticationProvider,
        InitializingBean,
        MessageSourceAware {

    public static final String SESSION_KEY_WX_COM_MP_LAST_LOGIN = WxComMpLoginApi.class.getName() + ".WX_COM_MP_LAST_LOGIN";

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Autowired
    private UserChecker userChecker;

    @Autowired
    private WxComMpUserAuthApi wxComMpUserAuthApi;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    /**
     *
     */
    @Override
    public Authentication authenticate(
            Authentication authentication
    ) throws AuthenticationException {

        Assert.isTrue(authentication instanceof WxComMpAuthenticationToken,
                "只支持 " + WxComMpAuthenticationToken.class
        );

        WxComMpAuthenticationToken token = (WxComMpAuthenticationToken) authentication;

        UniResp<VerifyAuthCodeResp> verifyUniResp = wxComMpUserAuthApi.verifyAuthCode(
                token.getWxComAppId(),
                token.getWxMpAppId(),
                token.getCode(),
                token.getState()
        );

        Assert.isTrue(
                verifyUniResp != null
                        && verifyUniResp.getStatus() == 200 &&
                        verifyUniResp.getData() != null,
                "通过 code 换取微信 access token 失败"
        );
        VerifyAuthCodeResp verifyResp = verifyUniResp.getData();
        String openId = verifyResp.getOpenId();

        // 保存到 session 中
        WxComMpAuthInfo info = new WxComMpAuthInfo();
        info.setWxComAppId(token.getWxComAppId());
        info.setWxMpAppId(token.getWxMpAppId());
        info.setCode(token.getCode());
        info.setState(token.getState());
        info.setOpenId(openId);
        info.setTime(new Date());

        sessionService.add(info);
//        RequestContextHolder.currentRequestAttributes().setAttribute(
//                SESSION_KEY_WX_COM_MP_LAST_LOGIN,
//                info,
//                    RequestAttributes.SCOPE_SESSION
//        );


        User user = userService.requireUser(
                token.getWxMpAppId(),
                verifyResp.getOpenId()
        );

        userChecker.check(user);

        UserIdAuthentication auth = new UserIdAuthentication(user.getId(), null);
        auth.setDetails(token);
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (WxComMpAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


}
