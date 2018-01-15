package net.kingsilk.qh.oauth.core.wap.resource.s.login.phone;

import com.mysema.commons.lang.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.login.*;
import net.kingsilk.qh.oauth.api.s.login.phone.*;
import net.kingsilk.qh.oauth.security.login.*;
import net.kingsilk.qh.oauth.security.login.phone.*;
import net.kingsilk.qh.oauth.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.stereotype.*;

/**
 *
 */
@Component
public class PhoneLoginResource implements PhoneLoginApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Override
    public UniResp<LoginResp> login(
            String phone,
            String code
    ) {

        PhoneCodeAuthenticationToken token = new PhoneCodeAuthenticationToken(phone, code);

        Authentication authResult = authenticationManager.authenticate(token);
        Assert.isTrue(authResult instanceof UserIdAuthentication, "认证失败");

        // 保存到 session 中
        SecurityContextHolder.getContext().setAuthentication(authResult);

        String userId = (String) authResult.getPrincipal();

        LoginResp loginResp = userService.toLoginResp(userId);

        UniResp<LoginResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(loginResp);
        return uniResp;
    }
}
