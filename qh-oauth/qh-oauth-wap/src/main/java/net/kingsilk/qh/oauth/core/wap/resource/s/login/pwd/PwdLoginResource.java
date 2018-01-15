package net.kingsilk.qh.oauth.core.wap.resource.s.login.pwd;

import com.mysema.commons.lang.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.login.*;
import net.kingsilk.qh.oauth.api.s.login.pwd.*;
import net.kingsilk.qh.oauth.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

/**
 *
 */
@Component
public class PwdLoginResource implements PwdLoginApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Override
    public UniResp<LoginResp> login(
            String username,
            String password
    ) {

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authResult = authenticationManager.authenticate(authRequest);
        Assert.isTrue(authResult instanceof UsernamePasswordAuthenticationToken, "认证失败");

        // 保存到 session 中
        SecurityContextHolder.getContext().setAuthentication(authResult);

        User user = (User) authResult.getPrincipal();
        String userId = user.getUsername();
        LoginResp loginResp = userService.toLoginResp(userId);

        UniResp<LoginResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(loginResp);
        return uniResp;
    }
}
