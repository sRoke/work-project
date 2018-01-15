package net.kingsik.qh.oauth.client.impl.user.addr;

import net.kingsik.qh.oauth.client.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.user.addr.*;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

import static org.assertj.core.api.Assertions.*;


public class AddrApiImpTest extends BaseTest {

    @Autowired
    private AddrApi addrApi;


    @Test
    public void add01() {
        String userId = "58e4bd0c785a82000005a141";

        AddrAddReq req = new AddrAddReq();
        req.setAdc("120101");
        req.setStreet("东方电子商务园");
        req.setPostCode("000111");
        req.setCoorX("10000.00001");
        req.setCoorY("20000.00002");
        req.setContact("zhang3");
        req.setPhones(new HashSet<>(Arrays.asList(
                "17011223344",
                "18033445566"
        )));
        req.setDefaultAddr(true);
        req.setMemo("Hello : " + new Date());

        UniResp<String> uniResp = addrApi.add(userId, req);
        assertThat(uniResp).isNotNull();
        assertThat(uniResp.getStatus()).isEqualTo(200);
        assertThat(uniResp.getData()).isNotNull();

    }


    @Test
    public void get01() {
        String userId = "58e4bd0c785a82000005a141";
        String addrId = "598d78476f1f65755b46a702";

        UniResp<AddrGetResp> uniResp = addrApi.get(userId, addrId);
        assertThat(uniResp).isNotNull();
        assertThat(uniResp.getStatus()).isEqualTo(200);
        assertThat(uniResp.getData()).isNotNull();

    }


    @Test
    public void del01() {
        String userId = "58e4bd0c785a82000005a141";
        String addrId = "598d78986f1f65755b46a703";

        UniResp<Void> uniResp = addrApi.del(userId, addrId);
        assertThat(uniResp).isNotNull();
        assertThat(uniResp.getStatus()).isEqualTo(200);

    }

    @Test
    public void update01() {
        String userId = "58e4bd0c785a82000005a141";
        String addrId = "598d78476f1f65755b46a702";

        AddrUpdateReq updateReq = new AddrUpdateReq();
        updateReq.setMemo(Optional.of("update01 " + new Date()));

        UniResp<Void> uniResp = addrApi.update(userId, addrId, updateReq);
        assertThat(uniResp).isNotNull();
        assertThat(uniResp.getStatus()).isEqualTo(200);

    }

    @Test
    public void list01() {
        String userId = "58e4bd0c785a82000005a141";
        UniResp<UniPage<AddrGetResp>> uniResp = addrApi.list(
                10,
                0,
                null,
                userId,
                null
        );
        assertThat(uniResp).isNotNull();
        assertThat(uniResp.getStatus()).isEqualTo(200);

    }
}