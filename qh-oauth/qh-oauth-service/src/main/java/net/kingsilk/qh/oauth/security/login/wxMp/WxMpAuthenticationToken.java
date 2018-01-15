package net.kingsilk.qh.oauth.security.login.wxMp;

import org.springframework.security.authentication.*;

/**
 * 代表微信认证回调时的token。
 */
public class WxMpAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    /**
     * 微信 APP ID
     */
    private String appId;

    /**
     * 换取 access token 的一次性 code
     */
    private String code;

    /**
     * 防止请求伪造的状态码
     */
    private String state;

    public WxMpAuthenticationToken(
            String appId,
            String code,
            String state
    ) {
        super(null);
        this.appId = appId;
        this.code = code;
        this.state = state;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.code;
    }

    public String getAppId() {
        return appId;
    }

    public String getCode() {
        return code;
    }


    public String getState() {
        return state;
    }


}
