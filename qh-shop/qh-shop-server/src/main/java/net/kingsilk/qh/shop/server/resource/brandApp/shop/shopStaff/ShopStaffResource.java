package net.kingsilk.qh.shop.server.resource.brandApp.shop.shopStaff;

import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.user.AddUserReq;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffAddReq;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi;
import net.kingsilk.qh.oauth.core.OrgStaffStatusEnum;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopStaff.ShopStaffApi;
import net.kingsilk.qh.shop.api.brandApp.shop.shopStaff.dto.ShopStaffInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopStaff.dto.ShopStaffSaveReq;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.ShopRepo;
import net.kingsilk.qh.shop.repo.ShopStaffGroupRepo;
import net.kingsilk.qh.shop.repo.ShopStaffRepo;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.shopStaff.convert.ShopStaffConvert;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.shopStaffGroup.convert.ShopStaffGroupConvert;
import net.kingsilk.qh.shop.service.service.GroupService;
import net.kingsilk.qh.shop.service.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class ShopStaffResource implements ShopStaffApi {

    @Autowired
    private ShopStaffRepo shopStaffRepo;

    @Autowired
    private ShopStaffConvert shopStaffConvert;

    @Autowired
    private UserApi userApi;

    @Autowired
    private GroupService groupService;

    @Autowired
    private ShopStaffGroupConvert shopStaffGroupConvert;

    @Autowired
    private ShopRepo shopRepo;

    @Autowired
    private OrgStaffApi orgStaffApi;

    @Autowired
    private ShopStaffGroupRepo shopStaffGroupRepo;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private SecService secService;

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<ShopStaffInfoResp> info(
            String brandAppId,
            String shopId,
            String id) {

        UniResp<ShopStaffInfoResp> uniResp = new UniResp<>();

        ShopStaff shopStaff = shopStaffRepo.findOne(id);
        if (shopStaff == null) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setMessage("员工信息不存在");
            return uniResp;
        }
        ShopStaffInfoResp infoResp = shopStaffConvert.infoRespConvert(shopStaff);

        groupService.search(null, shopStaff.getId()).forEach(
                shopStaffGroup -> {
                    infoResp.getStaffGroupList().add(shopStaffGroupConvert.staffGroupModelConvert(shopStaffGroup));
                }
        );
        //TODO 需要oauth查其他信息
        net.kingsilk.qh.oauth.api.UniResp<UserGetResp> respUniResp =
                userApi.get(shopStaff.getUserId());
        infoResp.setRealName(respUniResp.getData().getRealName());
        infoResp.setPhone(respUniResp.getData().getPhone());

        uniResp.setStatus(200);
        uniResp.setData(infoResp);
        return uniResp;

    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> save(
            String brandAppId,
            String shopId,
            ShopStaffSaveReq shopStaffSaveReq) {
        Shop shop = shopRepo.findOne(
                allOf(
                        QShop.shop.deleted.ne(true),
                        QShop.shop.id.eq(shopId))
        );

        // TODO userId 应该先在oauth表里新增一个员工 再根据其userId 新增品牌商员工
        AddUserReq addUserReq = new AddUserReq();
        addUserReq.setPhone(shopStaffSaveReq.getPhone());
        addUserReq.setRealName(shopStaffSaveReq.getRealName());
        net.kingsilk.qh.oauth.api.UniResp<String> resp = userApi.addUser(addUserReq);

        ShopStaff shopStaff = shopStaffRepo.findOne(
                allOf(
                        QShopStaff.shopStaff.brandAppId.eq(brandAppId),
                        QShopStaff.shopStaff.shopId.eq(shopId),
                        QShopStaff.shopStaff.deleted.ne(true),
                        QShopStaff.shopStaff.userId.eq(resp.getData())
                ));

        UniResp<String> uniResp = new UniResp<>();
        if (shopStaff != null) {
            uniResp.setStatus(ErrStatus.NOTONLY);
            uniResp.setData("该手机号已经被使用");
            return uniResp;
        }

        shopStaff = new ShopStaff();
        shopStaff.setUserId(resp.getData());
        shopStaff.setMemo(shopStaffSaveReq.getMemo());
        shopStaff.setShopId(shopId);
        shopStaff.setEnable(shopStaffSaveReq.getEnable());
        shopStaff.setBrandAppId(brandAppId);

        shopStaff = shopStaffRepo.save(shopStaff);

        //TODO  在oauth的组织表里面新增
        OrgStaffAddReq orgStaffAddReq = new OrgStaffAddReq();
        orgStaffAddReq.setUserId(shopStaff.getUserId());

        //TODO 目前先以shopStaff的id作为orgStaff的工号（orgStaff很多字段都没有）
        orgStaffAddReq.setJobNo(shopStaff.getId());
        orgStaffAddReq.setOrgId(shop.getOrgId());
        orgStaffAddReq.setRealName(shopStaffSaveReq.getRealName());
        orgStaffAddReq.setStatus(OrgStaffStatusEnum.ENABLED);
        net.kingsilk.qh.oauth.api.UniResp<String> oauthResp =
                orgStaffApi.add(shopStaff.getUserId(), shopStaff.getBrandAppId(), orgStaffAddReq);
        if (oauthResp.getData() == null) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setData("新增组织失败");
            return uniResp;
        }


        List<ShopStaffGroup> newGroupList = new ArrayList<>();
        for (String groupId : shopStaffSaveReq.getStaffGroupIds()) {
            ShopStaffGroup shopStaffGroup = shopStaffGroupRepo.findOne(groupId);
            Set<ShopStaff> staffs = shopStaffGroup.getStaffS();
            staffs.add(shopStaff);
            shopStaffGroup.setStaffS(staffs);
            newGroupList.add(shopStaffGroup);
        }
        shopStaffGroupRepo.save(newGroupList);

        uniResp.setStatus(200);
        uniResp.setData("保存成功");

        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> update(
            String brandAppId,
            String shopId,
            String id,
            ShopStaffSaveReq shopStaffSaveReq) {

        ShopStaff shopStaff = shopStaffRepo.findOne(
                allOf(
                        QShopStaff.shopStaff.deleted.ne(true),
                        QShopStaff.shopStaff.shopId.eq(shopId),
                        QShopStaff.shopStaff.id.eq(id)
                )
        );


        //TODO 在oauth里面更新userInfo
        AddUserReq addUserReq = new AddUserReq();
        addUserReq.setPhone(shopStaffSaveReq.getPhone());
        addUserReq.setRealName(shopStaffSaveReq.getRealName());
        addUserReq.setUserId(shopStaff.getUserId());
        net.kingsilk.qh.oauth.api.UniResp<String> resp = userApi.update(addUserReq);

        UniResp<String> uniResp = new UniResp<>();
        if (resp.getData() == null) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setData("更新失败");
            return uniResp;
        }

        List<ShopStaffGroup> oldGroupList = groupService.search(null, shopStaff.getId());
        for (ShopStaffGroup ssg : oldGroupList) {
            ssg.getStaffS().remove(shopStaff);
        }
        shopStaffGroupRepo.save(oldGroupList);
        List<ShopStaffGroup> newGroupList = new ArrayList<>();
        if (shopStaffSaveReq.getStaffGroupIds().size() > 0) {
            for (String groupId : shopStaffSaveReq.getStaffGroupIds()) {
                ShopStaffGroup shopStaffGroup = shopStaffGroupRepo.findOne(groupId);
                Set<ShopStaff> staffs = shopStaffGroup.getStaffS();
                if (!staffs.contains(shopStaff)) {
                    staffs.add(shopStaff);
                    shopStaffGroup.setStaffS(staffs);
                    newGroupList.add(shopStaffGroup);
                }
            }
        }
        shopStaffGroupRepo.save(newGroupList);
        shopStaff.setEnable(shopStaffSaveReq.getEnable());
        shopStaffRepo.save(shopStaff);

        uniResp.setStatus(200);
        uniResp.setData("保存成功");

        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<UniPageResp<ShopStaffInfoResp>> page(
            String brandAppId,
            String shopId,
            int size,
            int page,
            List<String> sort,
            List<String> idList,
            String keyWord) {
        PageRequest pageRequest = new PageRequest(page, size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));

        Page<ShopStaff> staffPage = shopStaffRepo.findAll(
                allOf(
                        QShopStaff.shopStaff.deleted.ne(true),
                        QShopStaff.shopStaff.shopId.eq(shopId),
                        QShopStaff.shopStaff.brandAppId.eq(brandAppId),
                        !idList.isEmpty() ? QShopStaff.shopStaff.userId.in(idList) : null
                ), pageRequest);


        //TODO 根据userid去oauth查相关知识
        net.kingsilk.qh.oauth.api.UniResp<UniPage<UserGetResp>> pageUniResp =
                userApi.list(size, page, sort, idList);

        UniPageResp<ShopStaffInfoResp> resp = conversionService.convert(staffPage, UniPageResp.class);
        staffPage.getContent().forEach(
                shopStaff -> {
                    ShopStaffInfoResp shopStaffInfoResp = shopStaffConvert.infoRespConvert(shopStaff);
                    pageUniResp.getData().getContent().forEach(
                            userGetResp -> {
                                if (shopStaffInfoResp.getUserId().equals(userGetResp.getId())) {
                                    shopStaffInfoResp.setRealName(userGetResp.getRealName());
                                    shopStaffInfoResp.setPhone(userGetResp.getPhone());
                                }
                            }
                    );
                    resp.getContent().add(shopStaffInfoResp);
                }

        );
        UniResp<UniPageResp<ShopStaffInfoResp>> uniResp = new UniResp<>();
        uniResp.setData(resp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> enable(
            String brandAppId,
            String shopId,
            String id,
            boolean status) {

        ShopStaff shopStaff = shopStaffRepo.findOne(
                allOf(
                        QShopStaff.shopStaff.deleted.ne(true),
                        QShopStaff.shopStaff.shopId.eq(shopId),
                        QShopStaff.shopStaff.id.eq(id)
                )
        );
        UniResp<String> uniResp = new UniResp<>();
        if (shopStaff == null) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setData("员工信息不存在");
            return uniResp;
        }
        shopStaff.setEnable(status);
        shopStaffRepo.save(shopStaff);

        uniResp.setData("操作成功");
        uniResp.setStatus(200);
        return uniResp;

    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<ShopStaffInfoResp> currentShopStaffInfo(
            String brandAppId,
            String shopId
    ) {
        UniResp<ShopStaffInfoResp> uniResp = new UniResp<>();

        String userId = secService.curUserId();
        ShopStaff shopStaff = shopStaffRepo.findOne(
                allOf(
                        QShopStaff.shopStaff.brandAppId.eq(brandAppId),
                        QShopStaff.shopStaff.shopId.eq(shopId),
                        QShopStaff.shopStaff.userId.eq(userId)
                )
        );
        if (shopStaff == null) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setMessage("员工信息不存在");
            return uniResp;
        }
        ShopStaffInfoResp infoResp = shopStaffConvert.infoRespConvert(shopStaff);

        groupService.search(null, shopStaff.getId()).forEach(
                shopStaffGroup -> {
                    infoResp.getStaffGroupList().add(shopStaffGroupConvert.staffGroupModelConvert(shopStaffGroup));
                }
        );

        Shop shop=shopRepo.findOne(shopStaff.getShopId());


        infoResp.setShopName(shop.getName());
        //TODO 需要oauth查其他信息
//        net.kingsilk.qh.oauth.api.UniResp<UserGetResp> respUniResp =
//                userApi.get(shopStaff.getUserId());
//        infoResp.setRealName(respUniResp.getData().getRealName());
//        infoResp.setPhone(respUniResp.getData().getPhone());

        uniResp.setStatus(200);
        uniResp.setData(infoResp);
        return uniResp;
    }
}
