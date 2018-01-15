package net.kingsilk.qh.oauth.core.wap.resource.s.auth.phone;

import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.attr.*;
import net.kingsilk.qh.oauth.api.s.auth.phone.*;
import net.kingsilk.qh.oauth.security.*;
import net.kingsilk.qh.oauth.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 *
 */
@Component
public class PhoneAuthResource implements PhoneAuthApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SecService secService;

    @Override
    public UniResp<Void> auth(
            String phone,
            String code
    ) {
        // 不考虑手机号是否已经被其他人占用、绑定


        // TODO 验证短信验证码
        // TODO 更新短信验证码——已使用

        // 保存到 session 中
        PhoneAuthInfo info = new PhoneAuthInfo();
        info.setPhone(phone);
        info.setCode(code);
        info.setTime(new Date());
        info.setAuthType(PhoneAuthInfo.AuthType.AUTH);
        sessionService.add(info);

        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(null);
        return uniResp;

    }


}
