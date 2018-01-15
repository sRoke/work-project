package net.kingsilk.qh.oauth.core.wap.resource.s.reg.phone;

import com.mysema.commons.lang.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.attr.*;
import net.kingsilk.qh.oauth.api.s.reg.phone.*;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.security.*;
import net.kingsilk.qh.oauth.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 *
 */
@Component
public class PhoneRegResource implements PhoneRegApi {

    @Autowired
    private UserService userService;

    @Autowired
    private SecService secService;

    @Autowired
    private SessionService sessionService;


    @Override
    public UniResp<RegResp> reg(
            String phone,
            String code
    ) {

        // 验证手机号是否已经被占用
        boolean inUse = userService.isPhoneInUse(phone);
        Assert.isTrue(!inUse, "手机号已经被占用");

        // TODO 验证短信验证码
        // TODO 更新短信验证码——已使用

        // 注册
        User user = userService.registerByPhone(phone);


        // 确保当前 Session 已经登录
        secService.reauthenticate(user.getId());

        // 保存到 session 中
        PhoneAuthInfo info = new PhoneAuthInfo();
        info.setPhone(phone);
        info.setCode(code);
        info.setTime(new Date());
        info.setAuthType(PhoneAuthInfo.AuthType.PHONE_REG);
        sessionService.add(info);

        RegResp resp = new RegResp();
        resp.setUserId(user.getId());

        UniResp<RegResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);
        return uniResp;
    }
}
