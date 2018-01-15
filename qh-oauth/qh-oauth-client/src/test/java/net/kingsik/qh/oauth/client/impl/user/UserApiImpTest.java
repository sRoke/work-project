package net.kingsik.qh.oauth.client.impl.user;

import net.kingsik.qh.oauth.client.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.user.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;


public class UserApiImpTest extends BaseTest {

    @Autowired
    private UserApi userApi;





    @Test
    public void get01() {

        String userId = "58e4bd0c785a82000005a141";
        UniResp<UserGetResp> uniResp = userApi.get(userId);
        assertThat(uniResp).isNotNull();
        assertThat(uniResp.getStatus()).isEqualTo(200);

    }

    @Test
    public void list01() {

        UniResp<UniPage<UserGetResp>> uniResp = userApi.list(
                10,
                0,
                null,
                null);
        assertThat(uniResp).isNotNull();
        assertThat(uniResp.getStatus()).isEqualTo(200);
        assertThat(uniResp.getData()).isNotNull();
        assertThat(uniResp.getData().getSize()).isNotEqualTo(0);
    }


    @Test
    public void list02() {

        List<String> userIds = Arrays.asList(
                "58e4bd0c785a82000005a141",
                "598bd7115cef5d11385c1c04"
        );

        UniResp<UniPage<UserGetResp>> uniResp = userApi.list(
                10,
                0,
                null,
                userIds);
        assertThat(uniResp).isNotNull();
        assertThat(uniResp.getStatus()).isEqualTo(200);
        assertThat(uniResp.getData()).isNotNull();
        assertThat(uniResp.getData().getSize()).isNotEqualTo(2);
    }
}