package net.kingsilk.qh.shop.service.service;

import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.oauth.api.user.org.OrgAddReq;
import net.kingsilk.qh.oauth.api.user.org.OrgApi;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffAddReq;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi;
import net.kingsilk.qh.oauth.core.OrgStaffStatusEnum;
import net.kingsilk.qh.oauth.core.OrgStatusEnum;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.core.AuthorityEnum;
import net.kingsilk.qh.shop.core.ShopEnum;
import net.kingsilk.qh.shop.core.ShopStatusEnum;
import net.kingsilk.qh.shop.domain.Shop;
import net.kingsilk.qh.shop.domain.ShopAccount;
import net.kingsilk.qh.shop.domain.ShopStaff;
import net.kingsilk.qh.shop.domain.ShopStaffGroup;
import net.kingsilk.qh.shop.repo.ShopAccountRepo;
import net.kingsilk.qh.shop.repo.ShopRepo;
import net.kingsilk.qh.shop.repo.ShopStaffGroupRepo;
import net.kingsilk.qh.shop.repo.ShopStaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class ShopOrderService {

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private UserApi userApi;

    @Autowired
    private OrgStaffApi orgStaffApi;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private ShopStaffRepo shopStaffRepo;

    @Autowired
    private ShopAccountRepo shopAccountRepo;

    @Autowired
    private ShopStaffGroupRepo shopStaffGroupRepo;

    @Autowired
    private OrgApi orgApi;

    @Autowired
    private SecService secService;

    public String createShop(String brandAppId, Integer expireDate, String userId) {

        if (userId == null) {
            userId = secService.curUserId();
        }
        Shop shop = new Shop();

        shop.setBrandAppId(brandAppId);
        shop.setStatus(ShopStatusEnum.NORMAL);

        net.kingsilk.qh.oauth.api.UniResp<UserGetResp> resp
                = userApi.getInfoByUserId(userId);
        if (resp.getData() == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "用户信息错误");
        }
        String name = resp.getData().getRealName();
        if (name == null) {
            name = "u" + resp.getData().getPhone();
        }
        shop.setName(name);
        shop.setPhone(resp.getData().getPhone());
        shop.setTel(resp.getData().getPhone());
        shop.setShopType(ShopEnum.OTHER);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.add(Calendar.DATE, expireDate);
        shop.setExpireDate(calendar.getTime());

        //创建门店时给他一个组织
        OrgAddReq orgAddReq = new OrgAddReq();
        orgAddReq.setName(shop.getName());
        orgAddReq.setStatus(OrgStatusEnum.ENABLED);
        net.kingsilk.qh.oauth.api.UniResp<String> oauthResp = orgApi.add(resp.getData().getId(), orgAddReq);
        Assert.notNull(oauthResp.getData(), "新增组织失败");

        //组织员工
        OrgStaffAddReq orgStaffAddReq = new OrgStaffAddReq();
        orgStaffAddReq.setUserId(resp.getData().getId());
        orgStaffAddReq.setOrgId(oauthResp.getData());
        orgStaffAddReq.setStatus(OrgStaffStatusEnum.ENABLED);
        net.kingsilk.qh.oauth.api.UniResp<String> orgStaffResp =
                orgStaffApi.add(resp.getData().getId(), oauthResp.getData(), orgStaffAddReq);
        Assert.notNull(orgStaffResp, "新增组织失败");

        shop.setOrgId(oauthResp.getData());
        shop = shopRepo.save(shop);

        //关联负责人后 同时给他一个门店超级管理员的权限
        ShopStaff shopStaff = new ShopStaff();
        shopStaff.setBrandAppId(brandAppId);
        shopStaff.setEnable(true);
        shopStaff.setShopId(shop.getId());
        shopStaff.setMemo("门店超级管理员");
        shopStaff.setUserId(resp.getData().getId());
        shopStaff = shopStaffRepo.save(shopStaff);


        //门店管理员
        ShopStaffGroup shopStaffGroup = new ShopStaffGroup();
        shopStaffGroup.setDesc("门店管理员");
        shopStaffGroup.setName("shopAdmin");
        shopStaffGroup.setBrandAppId(brandAppId);
        shopStaffGroup.setShopId(shop.getId());
        shopStaffGroup.setReserved(true);
        //todo 所有权限
        Set<String> stringSet = new HashSet<>();
        stringSet = authorityService.fillAuth(stringSet, AuthorityEnum.SHOP_SA);
        shopStaffGroup.setAuthorities(stringSet);
        shopStaffGroup.getStaffS().add(shopStaff);
        shopStaffGroupRepo.save(shopStaffGroup);

        ShopAccount shopAccount = new ShopAccount();
        shopAccount.setShopId(shop.getId());
        shopAccount.setBrandAppId(brandAppId);
        shopAccount.setBalance(0);
        shopAccount.setTotalBalance(0);
        shopAccount.setTotalWithdraw(0);
        shopAccountRepo.save(shopAccount);

        return shop.getId();
    }
}
