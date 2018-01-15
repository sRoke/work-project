package net.kingsilk.qh.platform.server.resource.home;

import com.querydsl.core.types.dsl.Expressions;
import io.swagger.annotations.Api;
import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffGetResp;
import net.kingsilk.qh.platform.api.UniResp;
import net.kingsilk.qh.platform.api.home.HomeApi;
import net.kingsilk.qh.platform.api.home.dto.HomeResp;
import net.kingsilk.qh.platform.domain.App;
import net.kingsilk.qh.platform.domain.BrandApp;
import net.kingsilk.qh.platform.domain.QBrandApp;
import net.kingsilk.qh.platform.repo.AppRepo;
import net.kingsilk.qh.platform.repo.BrandAppRepo;
import net.kingsilk.qh.platform.repo.BrandComRepo;
import net.kingsilk.qh.platform.service.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Path("/home")
@Api
@Singleton
public class HomeResource implements HomeApi {
    @Autowired
    private SecService secService;

    @Autowired
    private BrandComRepo brandComRepo;

    @Autowired
    private BrandAppRepo brandAppRepo;

    @Autowired
    private AppRepo appRepo;

    @Autowired
    private OrgStaffApi orgStaffApi;

    @Override
    public UniResp<List<HomeResp>> get() {
        String userId = secService.curUserId();

        //先通过userid去oauth里拿orgid的列表
        List<String> list = new ArrayList<>();
        net.kingsilk.qh.oauth.api.UniResp<UniPage<OrgStaffGetResp>> uniPageUniResp
                = orgStaffApi.search(99, 0, list, userId);

        List<HomeResp> respList = new ArrayList<>();

//        uniPageUniResp.getData().getContent().forEach(
//                orgStaffGetResp ->
//                        System.out.println(orgStaffGetResp.getOrgId())
//        );
        //在通过orgid的列表去查找brandApp的列表
        uniPageUniResp.getData().getContent().forEach(
                orgStaffGetResp -> {
                    BrandApp brandApp = brandAppRepo.findOne(
                            Expressions.allOf(
                                    QBrandApp.brandApp.deleted.in(false),
                                    QBrandApp.brandApp.ownerOrgId.eq(orgStaffGetResp.getOrgId())
                            ));
                    if (brandApp != null) {
                        HomeResp homeResp = new HomeResp();
                        homeResp.setAppId(brandApp.getAppId());
                        App app = appRepo.findOne(brandApp.getAppId());
                        homeResp.setUserId(userId);
                        homeResp.setAppUrl(app.getAppUrl());
                        homeResp.setShopName(brandApp.getShopName());
                        homeResp.setShopType(app.getAppName());
                        homeResp.setBrandAppId(brandApp.getId());
                        respList.add(homeResp);
                    }
                }
        );
        UniResp<List<HomeResp>> uniResp = new UniResp<>();
        uniResp.setData(respList);
        uniResp.setStatus(200);
        return uniResp;
    }
}
