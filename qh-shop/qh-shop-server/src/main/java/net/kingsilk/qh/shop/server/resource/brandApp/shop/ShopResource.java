package net.kingsilk.qh.shop.server.resource.brandApp.shop;

import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.user.AddUserReq;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.oauth.api.user.org.OrgAddReq;
import net.kingsilk.qh.oauth.api.user.org.OrgApi;
import net.kingsilk.qh.oauth.api.user.org.OrgUpdateReq;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffAddReq;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi;
import net.kingsilk.qh.oauth.core.OrgStaffStatusEnum;
import net.kingsilk.qh.oauth.core.OrgStatusEnum;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.ShopApi;
import net.kingsilk.qh.shop.api.brandApp.shop.dto.ShopCreateRep;
import net.kingsilk.qh.shop.api.brandApp.shop.dto.ShopResp;
import net.kingsilk.qh.shop.core.*;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.*;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.convert.ShopRespConvert;
import net.kingsilk.qh.shop.service.service.AuthorityService;
import net.kingsilk.qh.shop.service.util.DbUtil;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class ShopResource implements ShopApi {

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private ShopStaffRepo shopStaffRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RefundRepo refundRepo;

    @Autowired
    private ShopStaffGroupRepo shopStaffGroupRepo;

    @Autowired
    private UserApi userApi;

    @Autowired
    private OrgApi orgApi;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private ShopRespConvert shopRespConvert;

    @Autowired
    private OrgStaffApi orgStaffApi;

    @Autowired
    private ShopAccountRepo shopAccountRepo;

    @Override
    @PreAuthorize("isAuthenticated()")
    public UniResp<String> create(String brandAppId, ShopCreateRep shopCreateRep) {
        UniResp<String> uniResp = new UniResp<>();
        if (shopCreateRep == null) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setData("参数为空");
            return uniResp;
        }
        Shop shop = new Shop();
        if (StringUtils.isEmpty(shopCreateRep.getAdcNo())) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setData("地址不能为空");
            return uniResp;
        }
        if (StringUtils.isEmpty(shopCreateRep.getAddress())) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setData("详细地址不能为空");
            return uniResp;
        }
        if (StringUtils.isEmpty(brandAppId)) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setData("所属应用不能为空");
            return uniResp;
        }
        if (StringUtils.isEmpty(shopCreateRep.getPhone())) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setData("负责人不能为空");
            return uniResp;
        }
        if (StringUtils.isEmpty(shopCreateRep.getShopType())) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setData("店铺类型不能为空");
            return uniResp;
        }
        shop.setStatus(ShopStatusEnum.NORMAL);
        shop.setAdcNo(shopCreateRep.getAdcNo());
        shop.setAddress(shopCreateRep.getAddress());
        shop.setBrandAppId(brandAppId);
        shop.setImg(shopCreateRep.getImg());
        shop.setName(shopCreateRep.getName());
        shop.setPhone(shopCreateRep.getPhone());
        shop.setTel(shopCreateRep.getTel());
        shop.setShopType(ShopEnum.valueOf(shopCreateRep.getShopType()));

        //默认给店铺3天的到期时间
//        Date date = new Date(new Date().getTime() + 3 * 24 * 60 * 60 * 1000);

        Calendar todayStartCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        todayStartCalendar.set(todayStartCalendar.get(Calendar.YEAR) + 3, todayStartCalendar.get(Calendar.MONTH), todayStartCalendar.get(Calendar.DATE), 23, 59, 59);
        Date todayStartTime = todayStartCalendar.getTime();

        shop.setExpireDate(todayStartTime);

        //根据phone去oauth里面查找或者创建一个userId
        AddUserReq addUserReq = new AddUserReq();
        addUserReq.setPhone(shopCreateRep.getPhone());
        net.kingsilk.qh.oauth.api.UniResp<String> resp
                = userApi.addUser(addUserReq);

        //创建门店时给他一个组织
        OrgAddReq orgAddReq = new OrgAddReq();
        orgAddReq.setName(shop.getName());
        orgAddReq.setStatus(OrgStatusEnum.ENABLED);
        net.kingsilk.qh.oauth.api.UniResp<String> oauthResp = orgApi.add(resp.getData(), orgAddReq);
        Assert.notNull(oauthResp.getData(), "新增组织失败");

        //组织员工
        OrgStaffAddReq orgStaffAddReq = new OrgStaffAddReq();
        orgStaffAddReq.setUserId(resp.getData());
        orgStaffAddReq.setOrgId(oauthResp.getData());
        orgStaffAddReq.setStatus(OrgStaffStatusEnum.ENABLED);
        net.kingsilk.qh.oauth.api.UniResp<String> orgStaffResp =
                orgStaffApi.add(resp.getData(), oauthResp.getData(), orgStaffAddReq);
        Assert.notNull(orgStaffResp, "新增组织失败");

        shop.setOrgId(oauthResp.getData());
        shop = shopRepo.save(shop);

        //关联负责人后 同时给他一个门店超级管理员的权限
        ShopStaff shopStaff = new ShopStaff();
        shopStaff.setBrandAppId(brandAppId);
        shopStaff.setEnable(true);
        shopStaff.setShopId(shop.getId());
        shopStaff.setMemo("门店超级管理员");
        shopStaff.setUserId(resp.getData());
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

        uniResp.setStatus(HttpStatus.SC_OK);
        uniResp.setData(shop.getId());
        return uniResp;
    }

    @Override
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SA','SHOP_SA')")
    public UniResp<ShopResp> info(String brandAppId, String shopId) {

        Shop shop = shopRepo.findOne(
                allOf(
                        QShop.shop.id.eq(shopId),
                        QShop.shop.deleted.eq(false),
                        QShop.shop.brandAppId.eq(brandAppId)
                )
        );
        UniResp<ShopResp> uniResp = new UniResp<>();
        if (shop != null) {
            ShopResp shopResp = shopRespConvert.convert(shop);
            uniResp.setStatus(HttpStatus.SC_OK);
            uniResp.setData(shopResp);
        } else {
            uniResp.setStatus(ErrStatus.FINDNULL);
        }
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SA','SHOP_SA')")
    public UniResp<String> delet(String brandAppId, String shopId) {

        Shop shop = shopRepo.findOne(
                allOf(
                        QShop.shop.id.eq(shopId),
                        QShop.shop.deleted.eq(false),
                        QShop.shop.brandAppId.eq(brandAppId)
                )
        );
        UniResp<String> uniResp = new UniResp<>();
        if (shop != null) {
            shop.setDeleted(true);
            shopRepo.save(shop);
            uniResp.setStatus(HttpStatus.SC_OK);
            uniResp.setData(shop.getId());
        } else {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setData("找不到门店");
        }
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SA','SHOP_SA')")
    public UniResp<String> enable(String brandAppId, String shopId, Boolean enable) {
        Shop shop = shopRepo.findOne(
                allOf(
                        QShop.shop.id.eq(shopId),
                        QShop.shop.deleted.eq(false),
                        QShop.shop.brandAppId.eq(brandAppId)
                )
        );
        UniResp<String> uniResp = new UniResp<>();
        if (shop != null) {
            if (enable == Boolean.TRUE) {
                shop.setStatus(ShopStatusEnum.NORMAL);
            } else {
                shop.setStatus(ShopStatusEnum.DISABLE);
            }

            shopRepo.save(shop);
            uniResp.setStatus(HttpStatus.SC_OK);
            uniResp.setData("success");
        } else {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setData("找不到门店");
        }
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SA','SHOP_SA')")
    public UniResp<String> update(String brandAppId, String shopId, ShopCreateRep shopCreateRep) {

        Shop shop = shopRepo.findOne(
                allOf(
                        QShop.shop.id.eq(shopId),
                        QShop.shop.deleted.ne(true),
                        QShop.shop.brandAppId.eq(brandAppId)
                )
        );

        if (!StringUtils.isEmpty(shopCreateRep.getAdcNo())) {
            shop.setAdcNo(shopCreateRep.getAdcNo());
        }
        if (!StringUtils.isEmpty(shopCreateRep.getAddress())) {
            shop.setAddress(shopCreateRep.getAddress());
        }
        if (!StringUtils.isEmpty(shopCreateRep.getShopType())) {
            shop.setShopType(ShopEnum.valueOf(shopCreateRep.getShopType()));
        }
        if (!StringUtils.isEmpty(shopCreateRep.getName())) {
            shop.setName(shopCreateRep.getName());
        }
        if (!StringUtils.isEmpty(shopCreateRep.getTel())) {
            shop.setTel(shopCreateRep.getTel());
        }
        if (!StringUtils.isEmpty(shopCreateRep.getImg())) {
            shop.setImg(shopCreateRep.getImg());
        }
        String phone = shop.getPhone();
        if (!StringUtils.isEmpty(shopCreateRep.getPhone()) && !phone.equals(shopCreateRep.getPhone())) {
            // todo  换了负责人后 原来的负责人应该弃用
            //去oauth找userId
            net.kingsilk.qh.oauth.api.UniResp<UniPage<UserGetResp>> resp =
                    userApi.search(null, null, null, phone);
            String userId = resp.getData().getContent().get(0).getId();

            //把负责人在这个店的shopAdmin的权限都去掉
            ShopStaff shopStaff = shopStaffRepo.findOne(
                    allOf(
                            QShopStaff.shopStaff.userId.eq(userId),
                            QShopStaff.shopStaff.brandAppId.eq(brandAppId),
                            QShopStaff.shopStaff.shopId.eq(shopId),
                            QShopStaff.shopStaff.deleted.ne(true),
                            QShopStaff.shopStaff.enable.in(true)
                    )
            );
            ShopStaffGroup shopStaffGroup = shopStaffGroupRepo.findOne(
                    allOf(
                            QShopStaffGroup.shopStaffGroup.brandAppId.eq(brandAppId),
                            QShopStaffGroup.shopStaffGroup.shopId.eq(shopId),
                            QShopStaffGroup.shopStaffGroup.name.eq("shopAdmin")
                    )
            );
            shopStaffGroup.getStaffS().remove(shopStaff);

            // todo 并去oauth里面查看新的phone是否对应userid 同时在shopstaff新增超级管理员
            //根据phone去oauth里面查找或者创建一个userId
            AddUserReq addUserReq = new AddUserReq();
            addUserReq.setPhone(shopCreateRep.getPhone());
            net.kingsilk.qh.oauth.api.UniResp<String> oauthResp
                    = userApi.addUser(addUserReq);
            //组织负责人更换
            String orgId = shop.getOrgId();
            OrgUpdateReq orgUpdateReq = new OrgUpdateReq();
            orgUpdateReq.setName(java.util.Optional.ofNullable(shopCreateRep.getName()));
            orgApi.update(oauthResp.getData(), orgId, orgUpdateReq);

            //组织员工表操作
            net.kingsilk.qh.oauth.api.UniResp<String> checkResp =
                    orgStaffApi.check(oauthResp.getData(), orgId);
            if (StringUtils.isEmpty(checkResp.getData())) {
                OrgStaffAddReq orgStaffAddReq = new OrgStaffAddReq();
                orgStaffAddReq.setUserId(oauthResp.getData());
                orgStaffAddReq.setOrgId(orgId);
                orgStaffAddReq.setStatus(OrgStaffStatusEnum.ENABLED);
                net.kingsilk.qh.oauth.api.UniResp<String> orgStaffResp =
                        orgStaffApi.add(oauthResp.getData(), orgId, orgStaffAddReq);
                Assert.notNull(orgStaffResp, "新增组织失败");

            }

            //如果没有这个员工 创建一个shopstaff

            ShopStaff newShopStaff = shopStaffRepo.findOne(
                    allOf(
                            QShopStaff.shopStaff.userId.eq(oauthResp.getData()),
                            QShopStaff.shopStaff.brandAppId.eq(brandAppId),
                            QShopStaff.shopStaff.shopId.eq(shopId),
                            QShopStaff.shopStaff.deleted.ne(true),
                            QShopStaff.shopStaff.enable.in(true)
                    )
            );
            if (newShopStaff == null) {
                newShopStaff = new ShopStaff();
                newShopStaff.setUserId(oauthResp.getData());
            }
            newShopStaff.setBrandAppId(brandAppId);
            newShopStaff.setEnable(true);
            newShopStaff.setShopId(shop.getId());
            newShopStaff.setMemo("门店超级管理员");
            newShopStaff = shopStaffRepo.save(newShopStaff);

            //在shopAdmin权限里面添加进去新负责人
            shopStaffGroup.getStaffS().add(newShopStaff);
            shopStaffGroupRepo.save(shopStaffGroup);

            shop.setPhone(shopCreateRep.getPhone());


        }

        shopRepo.save(shop);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(ErrStatus.OK);
        uniResp.setData("success");
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SA','SHOP_SA')")
    public UniResp<UniPageResp<ShopResp>> page(
            String brandAppId,
            int size,
            int page,
            List<String> sort,
            String keyWord) {
        PageRequest pageRequest = new PageRequest(page, size, new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));

        Page<Shop> shopPage = shopRepo.findAll(
                allOf(
                        QShop.shop.brandAppId.eq(brandAppId),
                        QShop.shop.deleted.ne(true)), pageRequest
        );

        Page<ShopResp> shopResps = shopPage.map(shop -> {
            ShopResp shopResp = shopRespConvert.convert(shop);
            return shopResp;
        });

        UniPageResp<ShopResp> uniPageResp = conversionService.convert(shopResps, UniPageResp.class);
        uniPageResp.setContent(shopResps.getContent());

        UniResp<UniPageResp<ShopResp>> uniResp = new UniResp<>();
        uniResp.setData(uniPageResp);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }

    public UniResp<List<Map<String, String>>> getShopList(
            String brandAppId,
            String userId
    ) {
        List<Map<String, String>> resp = new ArrayList<>();
        shopStaffRepo.findAll(
                allOf(
                        QShopStaff.shopStaff.brandAppId.eq(brandAppId),
                        QShopStaff.shopStaff.userId.eq(userId)
                )
        ).forEach(
                shopStaff -> {
                    Shop shop = shopRepo.findOne(shopStaff.getShopId());
                    Map<String, String> shopInfo = new HashMap<>();
                    shopInfo.put("name", shop.getName());
                    shopInfo.put("id", shop.getId());
                    resp.add(shopInfo);
                }
        );

        UniResp uniResp = new UniResp();
        uniResp.setStatus(200);
        uniResp.setData(resp);
        return uniResp;
    }

    @Override
    public UniResp<Map<String, Long>> getShopOrderNum(String brandAppId, String shopId) {

        List<Order> orders = Lists.newArrayList(orderRepo.findAll(
                Expressions.allOf(
                        QOrder.order.shopId.eq(shopId),
                        QOrder.order.deleted.ne(true),
                        QOrder.order.brandAppId.eq(brandAppId)
                )
        ));

        Long size = (long) Lists.newArrayList(refundRepo.findAll(
                Expressions.allOf(
                        QRefund.refund.deleted.ne(true),
                        QRefund.refund.shopId.eq(shopId),
                        QRefund.refund.brandAppId.eq(brandAppId),
                        DbUtil.opIn(QRefund.refund.refundStatus, Lists.newArrayList(RefundStatusEnum.REFUNDING, RefundStatusEnum.UNCHECKED, RefundStatusEnum.WAIT_BUYER_SENDING, RefundStatusEnum.WAIT_SELLER_RECEIVED))
                )
        )).size();

        Map<String, Long> map = new HashMap<>();

        map.put(OrderStatusEnum.UNCONFIRMED.getCode(), orders.stream().filter(it ->
                OrderStatusEnum.UNCONFIRMED.equals(it.getStatus())
        ).count());

        map.put(OrderStatusEnum.UNSHIPPED.getCode(), orders.stream().filter(it ->
                OrderStatusEnum.UNSHIPPED.equals(it.getStatus())
        ).count());

        map.put(OrderStatusEnum.UNPAYED.getCode(), orders.stream().filter(it ->
                OrderStatusEnum.UNPAYED.equals(it.getStatus())
        ).count());

        map.put(RefundStatusEnum.REFUNDING.getCode(), size);

        UniResp<Map<String, Long>> uniResp = new UniResp<>();

        uniResp.setData(map);
        uniResp.setStatus(HttpStatus.SC_OK);

        return uniResp;
    }
}
