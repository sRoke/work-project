package net.kingsilk.qh.raffle.server.resource.home;

import com.querydsl.core.types.dsl.Expressions;
import io.swagger.annotations.Api;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.raffle.api.common.ErrStatus;
import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.home.HomeApi;
import net.kingsilk.qh.raffle.domain.QRaffleApp;
import net.kingsilk.qh.raffle.domain.RaffleApp;
import net.kingsilk.qh.raffle.domain.Staff;
import net.kingsilk.qh.raffle.repo.RaffleAppRepo;
import net.kingsilk.qh.raffle.repo.StaffRepo;
import net.kingsilk.qh.raffle.service.RaffleAppService;
import net.kingsilk.qh.shop.api.brandApp.shop.dto.ShopResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.inject.Singleton;
import java.util.LinkedHashSet;
import java.util.Set;

@Api
@Component
public class HomeResource implements HomeApi {


    @Autowired
    private RaffleAppRepo raffleAppRepo;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private RaffleAppService raffleAppService;

    @Autowired
    private UserApi userApi;

    @Override
    public UniResp<String> getRaffleAppId(String brandAppId, String shopId) {
        UniResp<String> uniResp = new UniResp<>();

        if (StringUtils.isEmpty(brandAppId)) {
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            uniResp.setData("参数不能为空");
            return uniResp;
        }
        if (StringUtils.isEmpty(shopId)) {
            uniResp.setStatus(ErrStatus.ACTIVITYERROR);
            uniResp.setData("参数不能为空");
            return uniResp;
        }
        //先去查找是否该门店是否有砍价活动记录
        RaffleApp raffleApp = raffleAppRepo.findOne(
                Expressions.allOf(
                        QRaffleApp.raffleApp.shopId.eq(shopId),
                        QRaffleApp.raffleApp.brandAppId.eq(brandAppId)
                ));

        String raffleAppId;
        if (raffleApp == null) {
            raffleApp = new RaffleApp();
            raffleApp.setShopId(shopId);
            raffleApp.setBrandAppId(brandAppId);
            raffleAppRepo.save(raffleApp);
            raffleAppId = raffleApp.getId();
            net.kingsilk.qh.shop.api.UniResp<ShopResp> resp =
                    raffleAppService.getShopInfo(raffleAppId);
            net.kingsilk.qh.oauth.api.UniResp<net.kingsilk.qh.oauth.api.UniPage<UserGetResp>> pageUniResp =
                    userApi.search(10, 0, null, resp.getData().getPhone());
            String userId = pageUniResp.getData().getContent().get(0).getId();
            //新建管理员 用于权限管理
            Staff staff = new Staff();
            staff.setUserId(userId);
            staff.setRaffleAppId(raffleAppId);
            Set<String> stringSet = new LinkedHashSet<>();
            stringSet.add("SA");
            staff.setAuthorities(stringSet);
            staffRepo.save(staff);
        } else {
            raffleAppId = raffleApp.getId();
        }

        if (StringUtils.isEmpty(raffleAppId)) {
            uniResp.setStatus(ErrStatus.FOUNDNULL);
            uniResp.setData("raffleAppId不能为空");
            return uniResp;
        }
        uniResp.setData(raffleAppId);
        uniResp.setStatus(200);

        return uniResp;
    }
}
