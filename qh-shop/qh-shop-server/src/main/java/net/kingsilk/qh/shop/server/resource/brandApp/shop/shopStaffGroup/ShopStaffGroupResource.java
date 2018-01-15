package net.kingsilk.qh.shop.server.resource.brandApp.shop.shopStaffGroup;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopStaffGroup.ShopStaffGroupApi;
import net.kingsilk.qh.shop.api.brandApp.shop.shopStaffGroup.dto.ShopStaffGroupInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopStaffGroup.dto.ShopStaffGroupLoadResp;
import net.kingsilk.qh.shop.api.brandApp.shop.shopStaffGroup.dto.ShopStaffGroupSaveReq;
import net.kingsilk.qh.shop.domain.QShopStaff;
import net.kingsilk.qh.shop.domain.QShopStaffGroup;
import net.kingsilk.qh.shop.domain.ShopStaffGroup;
import net.kingsilk.qh.shop.repo.ShopStaffGroupRepo;
import net.kingsilk.qh.shop.server.resource.brandApp.shop.shopStaffGroup.convert.ShopStaffGroupConvert;
import net.kingsilk.qh.shop.service.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Component
public class ShopStaffGroupResource implements ShopStaffGroupApi {

    @Autowired
    private GroupService groupService;

    @Autowired
    private ShopStaffGroupRepo shopStaffGroupRepo;

    @Autowired
    private ShopStaffGroupConvert shopStaffGroupConvert;

    @Autowired
    @Qualifier(value = "mvcConversionService")
    private ConversionService conversionService;

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<ShopStaffGroupInfoResp> info(
            String brandAppId,
            String shopId,
            String id,
            String source) {

        ShopStaffGroup shopStaffGroup = shopStaffGroupRepo.findOne(
                Expressions.allOf(
                        QShopStaffGroup.shopStaffGroup.brandAppId.eq(brandAppId),
                        QShopStaffGroup.shopStaffGroup.shopId.eq(shopId),
                        QShopStaffGroup.shopStaffGroup.id.eq(id),
                        QShopStaffGroup.shopStaffGroup.deleted.ne(true)
                ));

        if (shopStaffGroup == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "该角色不存在");
        }

        ShopStaffGroupInfoResp shopStaffGroupInfoResp = shopStaffGroupConvert.respConvert(shopStaffGroup);
        UniResp<ShopStaffGroupInfoResp> uniResp = new UniResp<>();
        uniResp.setData(shopStaffGroupInfoResp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> save(
            String brandAppId,
            String shopId,
            ShopStaffGroupSaveReq shopStaffGroupSaveReq) {
        ShopStaffGroup shopStaffGroup = shopStaffGroupRepo.findOne(
                Expressions.allOf(
                        QShopStaffGroup.shopStaffGroup.brandAppId.eq(brandAppId),
                        QShopStaffGroup.shopStaffGroup.deleted.ne(true),
                        QShopStaffGroup.shopStaffGroup.name.eq(shopStaffGroupSaveReq.getName()),
                        QShopStaffGroup.shopStaffGroup.shopId.eq(shopId)
                )
        );
        UniResp<String> uniResp = new UniResp<>();
        if (shopStaffGroup != null) {
            uniResp.setStatus(ErrStatus.NOTONLY);
            uniResp.setData("该名称已存在");
            return uniResp;
        }

        shopStaffGroup = new ShopStaffGroup();

        if (StringUtils.isEmpty(shopStaffGroupSaveReq.getName())) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setData("请输入该角色名称");
            return uniResp;
        }
        shopStaffGroup.setName(shopStaffGroupSaveReq.getName());
        if (StringUtils.isEmpty(shopStaffGroupSaveReq.getDesp())) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setData("请输入该角色的描述信息");
            return uniResp;
        }
        shopStaffGroup.setDesc(shopStaffGroupSaveReq.getDesp());
        if (shopStaffGroupSaveReq.getAuthorMap().isEmpty()) {
            uniResp.setStatus(ErrStatus.PARAMNUll);
            uniResp.setData("请给角色选择权限");
            return uniResp;
        }
        shopStaffGroup.setAuthorities(shopStaffGroupSaveReq.getAuthorMap());
        shopStaffGroup.setBrandAppId(brandAppId);
        shopStaffGroup.setShopId(shopId);
        shopStaffGroup.setEnable(true);
        shopStaffGroup.setReserved(false);
        shopStaffGroupRepo.save(shopStaffGroup);

        uniResp.setStatus(200);
        uniResp.setData("SUCCESS");
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> update(
            String brandAppId,
            String shopId,
            String id,
            ShopStaffGroupSaveReq shopStaffGroupSaveReq) {

        ShopStaffGroup shopStaffGroup = shopStaffGroupRepo.findOne(
                Expressions.allOf(
                        QShopStaffGroup.shopStaffGroup.brandAppId.eq(brandAppId),
                        QShopStaffGroup.shopStaffGroup.shopId.eq(shopId),
                        QShopStaffGroup.shopStaffGroup.id.eq(id),
                        QShopStaffGroup.shopStaffGroup.deleted.ne(true)
                ));
        if (shopStaffGroup == null) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "没有该角色");
        }
        if (shopStaffGroup.getName().equals("shopAdmin")) {
            throw new ErrStatusException(ErrStatus.NOAUTH, "超级管理员角色不能修改");
        }
        if (!StringUtils.isEmpty(shopStaffGroupSaveReq.getDesp())) {
            shopStaffGroup.setDesc(shopStaffGroupSaveReq.getDesp());
        }
        UniResp<String> uniResp = new UniResp<>();

        if (!shopStaffGroup.getName().equals(shopStaffGroupSaveReq.getName())) {
            ShopStaffGroup isExist = shopStaffGroupRepo.findOne(
                    Expressions.allOf(
                            QShopStaffGroup.shopStaffGroup.brandAppId.eq(brandAppId),
                            QShopStaffGroup.shopStaffGroup.deleted.ne(true),
                            QShopStaffGroup.shopStaffGroup.name.eq(shopStaffGroupSaveReq.getName()),
                            QShopStaffGroup.shopStaffGroup.shopId.eq(shopId)
                    )
            );

            if (isExist != null) {
                uniResp.setStatus(ErrStatus.NOTONLY);
                uniResp.setData("该名称已存在");
                return uniResp;
            }
        }
        if (!StringUtils.isEmpty(shopStaffGroupSaveReq.getName())) {
            shopStaffGroup.setName(shopStaffGroupSaveReq.getName());
        }
        if (shopStaffGroupSaveReq.getAuthorMap() != null && !shopStaffGroupSaveReq.getAuthorMap().isEmpty()) {
            shopStaffGroup.setAuthorities(shopStaffGroupSaveReq.getAuthorMap());
        }

        shopStaffGroupRepo.save(shopStaffGroup);
        uniResp.setData("SUCCESS");
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<UniPageResp<ShopStaffGroupInfoResp>> page(
            String brandAppId,
            String shopId,
            int size,
            int page,
            List<String> sort,
            String keyWord) {

        PageRequest pageRequest = new PageRequest(page, size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));

        Page<ShopStaffGroup> groups = shopStaffGroupRepo.findAll(
                Expressions.allOf(
                        QShopStaff.shopStaff.deleted.ne(true),
                        QShopStaff.shopStaff.brandAppId.eq(brandAppId),
                        QShopStaff.shopStaff.shopId.eq(shopId)),
                pageRequest);

        Page<ShopStaffGroupInfoResp> resp = groups.map(shopStaffGroup -> {
            ShopStaffGroupInfoResp shopStaffGroupInfoResp = new ShopStaffGroupInfoResp();
            shopStaffGroupInfoResp.setDesp(shopStaffGroup.getDesc());
            shopStaffGroupInfoResp.setName(shopStaffGroup.getName());
            shopStaffGroupInfoResp.setId(shopStaffGroup.getId());
            return shopStaffGroupInfoResp;
        });
        UniResp<UniPageResp<ShopStaffGroupInfoResp>> uniResp = new UniResp<>();
        UniPageResp<ShopStaffGroupInfoResp> uniPageResp = conversionService.convert(resp, UniPageResp.class);
        uniPageResp.setContent(resp.getContent());
        uniResp.setData(uniPageResp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<String> delete(
            String brandAppId,
            String shopId,
            String id) {

        ShopStaffGroup shopStaffGroup = shopStaffGroupRepo.findOne(
                Expressions.allOf(
                        QShopStaffGroup.shopStaffGroup.brandAppId.eq(brandAppId),
                        QShopStaffGroup.shopStaffGroup.shopId.eq(shopId),
                        QShopStaffGroup.shopStaffGroup.id.eq(id),
                        QShopStaffGroup.shopStaffGroup.deleted.ne(true)
                ));
        if (shopStaffGroup.getReserved()) {
            throw new ErrStatusException(ErrStatus.DELERROR, "不能删除系统预留角色");
        }

        shopStaffGroup.setDeleted(true);
        shopStaffGroupRepo.save(shopStaffGroup);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("SUCCESS");
        return uniResp;
    }

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('SHOPSTAFF','SHOP_SA')")
    public UniResp<ShopStaffGroupLoadResp> load() {
        ShopStaffGroupLoadResp shopStaffGroupLoadResp = new ShopStaffGroupLoadResp();
        Map<String, Map<String, Map<String, String>>> map = groupService.getAuthorMap();
        shopStaffGroupLoadResp.setAuthorMap(map);
        UniResp<ShopStaffGroupLoadResp> uniResp = new UniResp<>();
        uniResp.setData(shopStaffGroupLoadResp);
        uniResp.setStatus(200);
        return uniResp;
    }
}
