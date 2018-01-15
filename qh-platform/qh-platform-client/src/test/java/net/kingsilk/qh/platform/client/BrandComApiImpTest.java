package net.kingsilk.qh.platform.client;

import net.kingsilk.qh.platform.api.*;
import net.kingsilk.qh.platform.api.brandCom.*;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComAddReq;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComGetResp;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComUpdateReq;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

import static net.kingsilk.qh.platform.core.BrandComStatusEnum.*;


public class BrandComApiImpTest extends BaseTest {

    @Autowired
    private BrandComApi brandComApi;

    @Test
    public void add01() {
        BrandComAddReq req = new BrandComAddReq();
        req.setOwnerOrgId("5");
        req.setStatus(APPLYING);
        UniResp<String> un = brandComApi.add(req);
        log.debug(un.getData());
    }

    @Test
    public void delete01() {
        String brandComId = "59844df1281f390b624c4d4f";
        brandComApi.del(brandComId);
    }


    @Test
    public void update01() {
        String brandComId = "5982c8c3add7b434a6f42f77";
        BrandComUpdateReq breq = new BrandComUpdateReq();
        breq.setOwnerOrgId(Optional.of("6"));
        breq.setStatus(Optional.of(APPLYING));
        brandComApi.update(brandComId, breq);
    }

    @Test
    public void get01() {
        String brandComId = "59844df1281f390b624c4d4f";
        UniResp<BrandComGetResp> un = brandComApi.get(brandComId);
        log.debug(un.getData().toString());
    }


    @Test
    public void list01() {
        List<String> list = new ArrayList<>();
        list.add("5982c8c3add7b434a6f42f77");
        list.add("5982d544add7b434a6f42f78");
        UniResp<UniPage<BrandComGetResp>> un = brandComApi.list(10, 0, null, list);
        System.out.println(un.getData().toString());
    }
}