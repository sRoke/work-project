package net.kingsik.qh.oauth.client.impl.s.login.pwd;

import net.kingsik.qh.oauth.client.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.login.*;
import net.kingsilk.qh.oauth.api.s.login.pwd.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import static org.assertj.core.api.Assertions.*;

public class PwdLoginImplTest extends BaseTest {

    @Autowired
    PwdLoginApi pwdLoginApi;


    @Test
    public void testLogin01() {

        UniResp<LoginResp> uniResp = pwdLoginApi.login("qhAdmin", "qhAdmin");
        assertThat(uniResp).isNotNull();
    }
}
