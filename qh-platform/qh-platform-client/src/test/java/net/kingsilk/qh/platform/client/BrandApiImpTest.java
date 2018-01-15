package net.kingsilk.qh.platform.client;

import net.kingsilk.qh.platform.api.*;
import net.kingsilk.qh.platform.api.brand.*;
import net.kingsilk.qh.platform.api.brand.dto.BrandAddReq;
import net.kingsilk.qh.platform.api.brand.dto.BrandGetResp;
import net.kingsilk.qh.platform.api.brand.dto.BrandUpdateReq;
import org.junit.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

public class BrandApiImpTest extends BaseTest {

    @Autowired
    private BrandApi brandApi;

    @Test
    public void add01() {

        BrandAddReq bar = new BrandAddReq();
        bar.setBrandComId("5982c8c3add7b434a6f42f77");
        bar.setDesp("商品");
        bar.setFoundingTime("2017/2/1");
        bar.setLogoUrl("test2 test2");
        bar.setNameCN("熊猫2");
        bar.setNameEN("panda2");
        bar.setWebsite("www.panda2.com");
        brandApi.add(bar);
        log.debug(bar.toString());
    }

    @Test
    public void update01() {
        String brandComId = "5982c8c3add7b434a6f42f77";
        String bandId = "59844dd4281f390b624c4d4e";
        BrandUpdateReq brandUpdateReq = new BrandUpdateReq();
        brandUpdateReq.setBrandComId(Optional.of("5984256627569dcece7736e8"));
        brandUpdateReq.setDesp(Optional.of("test22"));
        brandApi.update(brandComId, bandId, brandUpdateReq);
    }

    @Test
    public void del01() {
        String brandComId = "5982c8c3add7b434a6f42f77";
        String bandid = "5987c8191547aa35463f8604";
        brandApi.del(brandComId, bandid);
    }

    @Test
    public void get01() {
        String brandComId = "5982c8c3add7b434a6f42f77";
        String bandid = "5987c8091547aa35463f8603";
        brandApi.get(brandComId, bandid);
    }

    @Test
    public void list01() {
        List<String> list = new ArrayList<>();
        list.add("59844dd4281f390b624c4d4e");
        UniResp<UniPage<BrandGetResp>> un =
                brandApi.list(10, 0, null, "5984256627569dcece7736e8", list);
        System.out.println(un.getData().toString());
    }

}
