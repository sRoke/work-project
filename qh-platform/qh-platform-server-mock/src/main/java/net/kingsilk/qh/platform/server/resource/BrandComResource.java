package net.kingsilk.qh.platform.server.resource;

import io.swagger.annotations.*;
import net.kingsilk.qh.platform.api.*;
import net.kingsilk.qh.platform.api.brandCom.*;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComAddReq;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComGetResp;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComUpdateReq;
import org.springframework.stereotype.*;

import javax.inject.*;
import javax.ws.rs.*;
import java.util.*;

/**
 *
 */
@Component
@Singleton
@Path("/brandCom")
@Api
public class BrandComResource implements BrandComApi {

    @Override
    public UniResp<String> add(BrandComAddReq brandComAddReq) {
        return null;
    }

    @Override
    public UniResp<Void> del(String bandComId) {
        return null;
    }

    @Override
    public UniResp<Void> update(String bandComId, BrandComUpdateReq brandComUpdateReq) {
        return null;
    }

    @Override
    public UniResp<BrandComGetResp> get(String bandComId) {
        return null;
    }

    @Override
    public UniResp<UniPage<BrandComGetResp>> list(int size, int page, List<String> sort, List<String> bandComIds) {
        return null;
    }

    @Override
    public UniResp<UniPage<BrandComGetResp>> search(Integer size, Integer page, List<String> sort, String q) {
        return null;
    }

    @Override
    public UniResp<List<Map<String, String>>> getBrandAppList(String brandAppId) {
        return null;
    }


}
