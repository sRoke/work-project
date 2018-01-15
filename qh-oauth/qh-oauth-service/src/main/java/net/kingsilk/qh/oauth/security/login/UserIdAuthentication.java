package net.kingsilk.qh.oauth.security.login;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;

import java.util.*;

/**
 * 代表用户验证后的信息。但验证方式（details）可能多种多样（比如手机号验证码，用户名密码等）。
 */
public class UserIdAuthentication extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String userId;

    public UserIdAuthentication(
            String userId
    ) {
        super(null);
        this.userId = userId;
        this.setAuthenticated(true);
    }

    public UserIdAuthentication(
            String userId,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.userId = userId;
        this.setAuthenticated(true);
    }

    public UserIdAuthentication(
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }

}
