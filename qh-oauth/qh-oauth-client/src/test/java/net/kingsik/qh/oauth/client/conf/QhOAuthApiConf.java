package net.kingsik.qh.oauth.client.conf;


import net.kingsilk.qh.oauth.api.s.login.pwd.*;
import net.kingsilk.qh.oauth.api.s.oauth.*;
import net.kingsilk.qh.oauth.api.user.*;
import net.kingsilk.qh.oauth.api.user.addr.*;
import net.kingsilk.qh.oauth.client.impl.s.login.pwd.*;
import net.kingsilk.qh.oauth.client.impl.s.oauth.*;
import net.kingsilk.qh.oauth.client.impl.user.*;
import net.kingsilk.qh.oauth.client.impl.user.addr.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@Configuration
public class QhOAuthApiConf {

    private String oauthUrl = "https://kingsilk.net/oauth2/rs/local/11300/api";
    @Bean
    public UserApi userApi(
            RestOperations restTemplate
    ) {
        return new UserApiImpl(restTemplate, oauthUrl);
    }

    @Bean
    public AddrApi addrApi(RestOperations restTemplate) {
        return new AddrApiImpl(restTemplate);
    }

    // 注意：以下API仅供测试使用

    @Bean
    public PwdLoginApi pwdLoginApi(RestOperations restTemplate) {
        return new PwdLoginImpl(restTemplate, oauthUrl);
    }

    @Bean
    public OAuthApi oauthApi(RestOperations restTemplate) {
        return new OAuthImpl(restTemplate, oauthUrl);
    }
}
