package net.kingsilk.qh.shop.server.resource.brandApp.home;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffGetResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.home.HomeApi;
import net.kingsilk.qh.shop.api.brandApp.home.dto.HomeResp;
import net.kingsilk.qh.shop.core.ShopStatusEnum;
import net.kingsilk.qh.shop.domain.QShop;
import net.kingsilk.qh.shop.domain.Shop;
import net.kingsilk.qh.shop.repo.ShopRepo;
import net.kingsilk.qh.shop.service.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HomeResource implements HomeApi {

    @Autowired
    private SecService secService;

    @Autowired
    private OrgStaffApi orgStaffApi;

    @Autowired
    private ShopRepo shopRepo;

    @Override
    public UniResp<List<HomeResp>> get(String brandAppId) {

        String userId = secService.curUserId();

        //先通过userid去oauth里拿orgid的列表
        List<String> list = new ArrayList<>();
        net.kingsilk.qh.oauth.api.UniResp<UniPage<OrgStaffGetResp>> uniPageUniResp
                = orgStaffApi.search(99, 0, list, userId);

        List<HomeResp> respList = new ArrayList<>();

        //在通过orgid的列表去查找shopId的列表
        uniPageUniResp.getData().getContent().forEach(
                orgStaffGetResp -> {
                    Shop shop = shopRepo.findOne(
                            Expressions.allOf(
                                    QShop.shop.deleted.ne(true),
                                    QShop.shop.status.eq(ShopStatusEnum.NORMAL),
                                    QShop.shop.orgId.eq(orgStaffGetResp.getOrgId()),
                                    QShop.shop.brandAppId.eq(brandAppId)
                            ));
                    if (shop != null) {
                        HomeResp homeResp = new HomeResp();
                        homeResp.setShopId(shop.getId());
                        homeResp.setShopName(shop.getName());
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
