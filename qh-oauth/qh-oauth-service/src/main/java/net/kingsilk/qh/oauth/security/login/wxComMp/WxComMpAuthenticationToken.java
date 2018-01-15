package net.kingsilk.qh.oauth.security.login.wxComMp;

import org.springframework.security.authentication.*;

/**
 * 代表微信认证回调时的token。
 */
public class WxComMpAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    /**
     * 微信第三方平台 APP ID
     */
    private String wxComAppId;

    /**
     * 微信公众号 APP ID
     */
    private String wxMpAppId;

    /**
     * 换取 access token 的一次性 code
     */
    private String code;

    /**
     * 防止请求伪造的状态码
     */
    private String state;

    public WxComMpAuthenticationToken(
            String wxComAppId,
            String wxMpAppId,
            String code,
            String state
    ) {
        super(null);
        this.wxComAppId = wxComAppId;
        this.wxMpAppId = wxMpAppId;
        this.code = code;
        this.state = state;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }


    public String getWxComAppId() {
        return wxComAppId;
    }

    public String getWxMpAppId() {
        return wxMpAppId;
    }

    public String getCode() {
        return code;
    }

    public String getState() {
        return state;
    }
}
