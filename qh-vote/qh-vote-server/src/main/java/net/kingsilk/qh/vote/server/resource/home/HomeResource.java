package net.kingsilk.qh.vote.server.resource.home;


import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.shop.api.brandApp.shop.dto.ShopResp;
import net.kingsilk.qh.vote.api.ErrStatus;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.home.HomeApi;
import net.kingsilk.qh.vote.domain.QVoteApp;
import net.kingsilk.qh.vote.domain.Staff;
import net.kingsilk.qh.vote.domain.VoteApp;
import net.kingsilk.qh.vote.repo.StaffRepo;
import net.kingsilk.qh.vote.repo.VoteAppRepo;
import net.kingsilk.qh.vote.service.service.VoteAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class HomeResource implements HomeApi {

    @Autowired
    private VoteAppRepo voteAppRepo;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private VoteAppService voteAppService;

    @Autowired
    private UserApi userApi;

    @Override
    public UniResp<String> getVoteAppId(String brandAppId, String shopId) {
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
        VoteApp voteApp = voteAppRepo.findOne(
                Expressions.allOf(
                        QVoteApp.voteApp.shopId.eq(shopId),
                        QVoteApp.voteApp.brandAppId.eq(brandAppId)
                ));

        String voteAppId = null;


        if (voteApp == null) {
            voteApp = new VoteApp();
            voteApp.setShopId(shopId);
            voteApp.setBrandAppId(brandAppId);
            voteAppRepo.save(voteApp);
            voteAppId = voteApp.getId();
            net.kingsilk.qh.shop.api.UniResp<ShopResp> resp =
                    voteAppService.getShopInfo(voteAppId);
            net.kingsilk.qh.oauth.api.UniResp<net.kingsilk.qh.oauth.api.UniPage<UserGetResp>> pageUniResp =
                    userApi.search(10, 0, null, resp.getData().getPhone());
            String userId = pageUniResp.getData().getContent().get(0).getId();
            //新建管理员 用于权限管理
            Staff staff = new Staff();
            staff.setUserId(userId);
            staff.setVoteAppId(voteAppId);
            Set<String> stringSet = new LinkedHashSet<>();
            stringSet.add("SA");
            staff.setAuthorities(stringSet);
            staffRepo.save(staff);
        } else {
            voteAppId = voteApp.getId();
        }

        if (StringUtils.isEmpty(voteAppId)) {
            uniResp.setStatus(ErrStatus.FOUNDNULL);
            uniResp.setData("bargainAppId不能为空");
            return uniResp;
        }
        uniResp.setData(voteAppId);
        uniResp.setStatus(200);

        return uniResp;


    }
}
