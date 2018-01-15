package net.kingsilk.qh.oauth.security.login.wxMp;

import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.attr.*;
import net.kingsilk.qh.oauth.api.s.login.wxMp.*;
import net.kingsilk.qh.oauth.domain.User;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.security.login.*;
import net.kingsilk.qh.oauth.service.*;
import net.kingsilk.wx4j.broker.api.UniResp;
import net.kingsilk.wx4j.broker.api.wxMp.user.auth.*;
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
 * 对用户输入的手机号和短信验证码进行验证。
 */
@Service
public class WxMpAuthenticationProvider implements AuthenticationProvider,
        InitializingBean,
        MessageSourceAware {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Autowired
    private UserChecker userChecker;

    @Autowired
    private WxMpUserAuthApi wxMpUserAuthApi;

    @Autowired
    private UserService userService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private SessionService sessionService;

    /**
     *
     */
    @Override
    public Authentication authenticate(
            Authentication authentication
    ) throws AuthenticationException {


        Assert.isInstanceOf(WxMpAuthenticationToken.class, authentication,
                "WxMpAuthenticationProvider 只支持 WxMpAuthenticationToken");

        WxMpAuthenticationToken token = (WxMpAuthenticationToken) authentication;
        String wxMpAppId = token.getAppId();
        String code = token.getCode();
        String state = token.getState();

        Assert.isTrue(!StringUtils.isEmpty(code), "code不能为空");
        Assert.isTrue(!StringUtils.isEmpty(state), "state不能为空");


        UniResp<VerifyAuthCodeResp> verifyUniResp = wxMpUserAuthApi.verifyAuthCode(
                wxMpAppId,
                code,
                state
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
        WxMpAuthInfo info = new WxMpAuthInfo();
        info.setWxMpAppId(token.getAppId());
        info.setCode(token.getCode());
        info.setState(token.getState());
        info.setOpenId(openId);
        info.setTime(new Date());

        sessionService.add(info);

        WxUser wxUser = wxUserService.getWxUserFromDB(wxMpAppId, openId);
        if (wxUser == null) {
            throw new ErrStatusException(
                    ErrStatus.USER_404_WITH_WX,
                    "未找到绑定该微信公众号的用户"
            );
        }

        // 查找用户是否存在
        User user = userService.getUserFromDB(wxUser.getUserId());
        if (user == null) {
            throw new ErrStatusException(
                    ErrStatus.UNKNOWN,
                    "脏数据？未找到用户"
            );
        }
        userChecker.check(user);

        UserIdAuthentication auth = new UserIdAuthentication(user.getId(), null);
        auth.setDetails(token);
        return auth;
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
