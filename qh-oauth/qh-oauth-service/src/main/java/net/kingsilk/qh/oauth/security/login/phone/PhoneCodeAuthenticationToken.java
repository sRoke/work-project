package net.kingsilk.qh.oauth.security.login.phone;

import org.springframework.security.authentication.*;
import org.springframework.security.core.*;

import java.util.*;

/**
 * 代表用户输入的手机号和短信验证码（认证前）。
 */
public class PhoneCodeAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String phone;
    private String code;

    public PhoneCodeAuthenticationToken(
            String phone,
            String code
    ) {
        super(null);
        this.phone = phone;
        this.code = code;
    }

    public PhoneCodeAuthenticationToken(
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return this.code;
    }

    @Override
    public Object getPrincipal() {
        return this.phone;
    }

}
