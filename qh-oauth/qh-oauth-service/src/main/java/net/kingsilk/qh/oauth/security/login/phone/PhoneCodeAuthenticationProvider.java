package net.kingsilk.qh.oauth.security.login.phone;

import net.kingsilk.qh.oauth.api.s.attr.PhoneAuthInfo;
import net.kingsilk.qh.oauth.api.s.attr.WxComMpAuthInfo;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import net.kingsilk.qh.oauth.security.login.*;
import net.kingsilk.qh.oauth.service.*;
import org.slf4j.*;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.*;
import org.springframework.context.support.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.Date;

import static com.querydsl.core.types.dsl.Expressions.*;

/**
 * 对用户输入的手机号和短信验证码进行验证。
 */
@Service
public class PhoneCodeAuthenticationProvider implements AuthenticationProvider,
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
    private UserService userService;

    @Autowired
    private SessionService sessionService;

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


        Assert.isInstanceOf(PhoneCodeAuthenticationToken.class, authentication,
                "PhoneCodeAuthenticationProvider 只支持 PhoneCodeAuthenticationToken");

        PhoneCodeAuthenticationToken token = (PhoneCodeAuthenticationToken) authentication;

        String phone = (String) token.getPrincipal();
        String code = (String) token.getCredentials();

        Assert.isTrue(!StringUtils.isEmpty(phone), "手机号码不能为空");
        Assert.isTrue(!StringUtils.isEmpty(code), "手机短信验证码不能为空");


        // 验证验证码的真实性
        Sms sms = smsRepo.findOne(allOf(
                QSms.sms.phone.eq(phone),
                QSms.sms.verifyCode.eq(code),
//                QSms.sms.valid.eq(true),
//                QSms.sms.codeExpiredTime.after(new Date()),
                anyOf(
                        QSms.sms.deleted.isNull(),
                        QSms.sms.deleted.eq(false)
                )
        ));
        if (sms == null) {
            throw new UsernameNotFoundException("手机号或验证码错误");
        }


        // 查找用户是否存在
        User user = userRepo.findOne(allOf(
                QUser.user.phone.eq(phone),
//                QUser.user.phoneVerifiedAt.isNotNull(),
                QUser.user.deleted.eq(false)
        ));

        //UserDetails userDetails = usernamePasswordDetailsService.loadUserByUseId(user.getId());
        // 如果没有该用户，则自动注册该用户
        if (user==null){
            user=userService.registerByPhone(phone);
        }
        // 保存到 session 中
        PhoneAuthInfo info = new PhoneAuthInfo();
        info.setPhone(phone);
        info.setAuthType(PhoneAuthInfo.AuthType.PHONE_LOGIN);
        info.setCode(code);
        info.setTime(new Date());
        sessionService.add(info);
        //检查用户是否被锁、禁用、过期等信息
        userChecker.check(user);

        UserIdAuthentication auth = new UserIdAuthentication(user.getId(), null);
        auth.setDetails(token);
        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (PhoneCodeAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }


}
