package net.kingsik.qh.oauth.client.impl.s.oauth;

import net.kingsik.qh.oauth.client.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.login.*;
import net.kingsilk.qh.oauth.api.s.login.pwd.*;
import net.kingsilk.qh.oauth.api.s.oauth.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import static org.assertj.core.api.Assertions.*;

public class OAuthImplTest extends BaseTest {

    @Autowired
    PwdLoginApi pwdLoginApi;

    @Autowired
    OAuthApi oauthApi;


    @Test
    public void testAuthorize01() {

        UniResp<AuthorizeResp> uniResp = oauthApi.authorize(
                "token",
                "CLIENT_ID_qh-front",
                "http://test.me/404",
                "LOGIN",
                "7788"
        );
        assertThat(uniResp).isNotNull();
    }
    @Test
    public void testAuthorize02() {

        UniResp<LoginResp> loginUniResp = pwdLoginApi.login("qhAdmin", "qhAdmin");
        assertThat(loginUniResp).isNotNull();


        UniResp<AuthorizeResp> uniResp = oauthApi.authorize(
                "token",
                "CLIENT_ID_qh-front",
                "http://test.me/404",
                "LOGIN",
                "7788"
        );
        assertThat(uniResp).isNotNull();
        assertThat(uniResp.getData().getAccessToken()).isNotNull();
    }
}
