package net.kingsilk.qh.platform.server.resource.brandCom;


import com.querydsl.core.types.Predicate;
import net.kingsilk.qh.oauth.api.user.AddUserReq;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.oauth.api.user.org.OrgAddReq;
import net.kingsilk.qh.oauth.api.user.org.OrgApi;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffAddReq;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi;
import net.kingsilk.qh.oauth.core.OrgStaffStatusEnum;
import net.kingsilk.qh.oauth.core.OrgStatusEnum;
import net.kingsilk.qh.platform.api.UniPage;
import net.kingsilk.qh.platform.api.UniResp;
import net.kingsilk.qh.platform.api.brandCom.BrandComApi;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComAddReq;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComGetResp;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComUpdateReq;
import net.kingsilk.qh.platform.core.SubSysTypeEnum;
import net.kingsilk.qh.platform.domain.*;
import net.kingsilk.qh.platform.repo.AppRepo;
import net.kingsilk.qh.platform.repo.BrandAppRepo;
import net.kingsilk.qh.platform.repo.BrandComRepo;
import net.kingsilk.qh.platform.server.resource.brandCom.convert.BrandComConvert;
import net.kingsilk.qh.platform.service.ParamUtils;
import net.kingsilk.qh.platform.service.QhPlatformProperties;
import net.kingsilk.qh.shop.api.brandApp.authorities.AuthoritiesApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class BrandComResource implements BrandComApi {

    @Autowired
    private BrandComRepo brandComRepo;

    @Autowired
    private BrandComConvert brandComConvert;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private AppRepo appRepo;

    @Autowired
    private BrandAppRepo brandAppRepo;

    @Autowired
    private UserApi userApi;

    @Autowired
    private OrgApi orgApi;

    @Autowired
    private net.kingsilk.qh.agency.api.brandApp.authorities.AuthoritiesApi authoritiesApi;

    @Autowired
    private net.kingsilk.qh.activity.api.brandApp.vote.authorities.AuthoritiesApi atAuthoritiesApi;

    @Autowired
    private AuthoritiesApi shopAuthoritiesApi;

    @Autowired
    private OrgStaffApi orgStaffApi;

    @Autowired
    private QhPlatformProperties qhPlatformProperties;

//    @Autowired
//    private EventPublisher eventPublisher;

    /**
     * 新增品牌商
     *
     * @param brandComAddReq
     * @return
     */
    @Override
    public UniResp<String> add(BrandComAddReq brandComAddReq) {
        BrandCom brandCom = brandComRepo.findOne(
                allOf(
                        QBrandCom.brandCom.deleted.in(false),
                        QBrandCom.brandCom.phone.eq(brandComAddReq.getPhone())
                ));
        Assert.isNull(brandCom, "该手机号已经注册");

        brandCom = brandComRepo.findOne(
                allOf(
                        QBrandCom.brandCom.deleted.in(false),
                        QBrandCom.brandCom.brandComName.eq(brandComAddReq.getTitle())
                )
        );

        Assert.isNull(brandCom, "该商家名已存在");

        //商家创建之前先把商家的拥有者创建
        AddUserReq addUserReq = new AddUserReq();
        addUserReq.setPhone(brandComAddReq.getPhone());
        net.kingsilk.qh.oauth.api.UniResp<String> resp = userApi.addUser(addUserReq);

        Assert.isTrue(resp.getStatus() == 200, "新增失败");

        brandCom = new BrandCom();
        BrandCom bc =
                brandComConvert.brandComAddReqConvert(brandCom, brandComAddReq);
        BrandCom b = brandComRepo.save(bc);
        Assert.notNull(b, "新增商家信息失败");

//        List<String> applist = new ArrayList<>();
        brandComAddReq.getAppIds().forEach(
                id -> {
                    BrandApp brandApp = new BrandApp();
                    brandApp.setAppId(id);
                    brandApp.setBrandComId(b.getId());
                    brandApp.setExpireDate(brandComAddReq.getDate());
                    App app = appRepo.findOne(id);
                    brandApp.setShopName(app.getAppName());
                    brandApp.setWxComAppId(qhPlatformProperties.getQhOAuth().getWap().getWxComAppId());

                    //TODO 商家应用创建后在oauth表里新增组织（一个商家应用是一个组织）
                    OrgAddReq orgAddReq = new OrgAddReq();
                    orgAddReq.setName(app.getAppName());
                    orgAddReq.setStatus(OrgStatusEnum.ENABLED);
                    net.kingsilk.qh.oauth.api.UniResp<String> oauthResp = orgApi.add(resp.getData(), orgAddReq);
                    Assert.notNull(oauthResp.getData(), "新增组织失败");

                    //todo 把新增的组织id给新增的brandApp做关联
                    brandApp.setOwnerOrgId(oauthResp.getData());
                    brandApp = brandAppRepo.save(brandApp);

                    //todo 在组织员工表里新增一个orStaff
                    OrgStaffAddReq orgStaffAddReq = new OrgStaffAddReq();
                    orgStaffAddReq.setUserId(resp.getData());
                    orgStaffAddReq.setOrgId(oauthResp.getData());
                    orgStaffAddReq.setStatus(OrgStaffStatusEnum.ENABLED);
                    net.kingsilk.qh.oauth.api.UniResp<String> orgStaffResp =
                            orgStaffApi.add(resp.getData(), oauthResp.getData(), orgStaffAddReq);
                    Assert.notNull(orgStaffResp, "新增组织失败");

//                    applist.add(app.getSystemTypeEnum().toString());
                    //TODO  同时给商家的拥有者一个超级管理员的权限
                    if (app.getSystemTypeEnum().equals(SubSysTypeEnum.QH_AGENCY)) {

                        //调用agency的新增超级管理员以及一些其他操作
                        net.kingsilk.qh.agency.api.UniResp<String> uniResp =
                                authoritiesApi.setSAStaff(brandApp.getId(), resp.getData(), oauthResp.getData(), brandComAddReq.getTitle(), brandComAddReq.getPhone());
                        System.out.println(brandComAddReq.getTitle() + "    " + brandComAddReq.getPhone());
                        Assert.notNull(uniResp.getData(), "新增超级管理员失败");
                    }
                    if (app.getSystemTypeEnum().equals(SubSysTypeEnum.QH_ACTIVITY)) {
                        //调用activity的新增超级管理员及一些操作
                        net.kingsilk.qh.activity.api.UniResp<String> atResp =
                                atAuthoritiesApi.setSAStaff(brandApp.getId(), resp.getData(), oauthResp.getData());
                        Assert.notNull(atResp.getData(), "新增超级管理员失败");
                    }
                    if (app.getSystemTypeEnum().equals(SubSysTypeEnum.QH_MALL)) {
                        //todo 微商城
                    }
                    if (app.getSystemTypeEnum().equals(SubSysTypeEnum.QH_SHOP)) {
                        //todo
                        net.kingsilk.qh.shop.api.UniResp<String> shopResp =
                                shopAuthoritiesApi.setSAStaff(brandApp.getId(), resp.getData(), oauthResp.getData());
                        Assert.notNull(shopResp.getData(), "新增超级管理员失败");
                    }
                }
        );

//        //以mq方式新增管理员
//        AddSAEventEx addSAEventEx = new AddSAEventEx();
//        addSAEventEx.setAppList(applist);
//        eventPublisher.publish(addSAEventEx);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("success");
        return uniResp;

    }

    /***
     *  删除品牌商
     * @param brandComId
     * @return
     */
    @Override
    public UniResp<Void> del(String brandComId) {
        Assert.isTrue(!StringUtils.isEmpty(brandComId), "品牌商id不能为空");
        BrandCom brandCom = brandComRepo.findOne(brandComId);
        brandCom.setDeleted(true);
        brandComRepo.save(brandCom);
        //todo 同时把商家的应用假删
        Iterable<BrandApp> brandApps = brandAppRepo.findAll(
                allOf(
                        QBrandApp.brandApp.deleted.in(false),
                        QBrandApp.brandApp.brandComId.eq(brandComId)
                )
        );
        brandApps.forEach(
                brandApp -> {
                    brandApp.setDeleted(true);

                    String orgId = brandApp.getOwnerOrgId();

                    net.kingsilk.qh.oauth.api.UniResp<net.kingsilk.qh.oauth.api.UniPage<UserGetResp>> userIdresp =
                            userApi.search(99, 0, null, brandCom.getPhone());

                    String userId = userIdresp.getData().getContent().get(0).getId();
                    orgApi.del(userId, orgId);
                    brandAppRepo.save(brandApp);
                }
        );
        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }

    /**
     * 更新品牌商信息
     *
     * @param brandComId
     * @param brandComUpdateReq
     * @return
     */
    @Override
    public UniResp<Void> update(String brandComId, BrandComUpdateReq brandComUpdateReq) {
        Assert.isTrue(!StringUtils.isEmpty(brandComId), "品牌商id不能为空");
        BrandCom brandCom = brandComRepo.findOne(brandComId);
        Assert.notNull(brandCom, "更新失败，查无结果");
        brandCom = brandComConvert.brandComUpdateReqConvert(brandCom, brandComUpdateReq);
        brandComRepo.save(brandCom);
        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }

    /**
     * 查询单个品牌商
     *
     * @param brandComId
     * @return
     */
    @Override
    public UniResp<BrandComGetResp> get(String brandComId) {
        Assert.isTrue(!StringUtils.isEmpty(brandComId), "品牌商id不能为空");
        BrandCom brandCom = brandComRepo.findOne(brandComId);
        Assert.notNull(brandCom, "无查询结果");
        BrandComGetResp brandComGetResp = brandComConvert.brandComGetRespConvert(
                new BrandComGetResp(), brandCom);
        UniResp<BrandComGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(brandComGetResp);
        return uniResp;
    }


    /**
     * 查询品牌商列表
     *
     * @param size
     * @param page
     * @param sort
     * @param brandComIds
     * @return
     */
    @Override
    public UniResp<UniPage<BrandComGetResp>> list(
            int size,
            int page,
            List<String> sort,
            List<String> brandComIds
    ) {

        Sort sortObj = null;

        if (sort.size() > 0) {
            sortObj = ParamUtils.toSort(sort.toArray(new String[0]));
        }

        PageRequest pageRequest = new PageRequest(page, size, sortObj);

        Predicate p = null;
        if (brandComIds != null && brandComIds.size() > 0) {
            p = allOf(
                    QBrandCom.brandCom.deleted.in(false),
                    QBrandCom.brandCom.id.in(brandComIds));
        } else {
            p = QBrandCom.brandCom.deleted.in(false);
        }
        Page<BrandCom> brandComPage = brandComRepo.findAll(
                p, pageRequest
        );
        Assert.notNull(brandComPage, "无查询结果");

        Page<BrandComGetResp> respPage = brandComPage.map(brandCom -> {
            BrandComGetResp resp = new BrandComGetResp();
            resp = brandComConvert.brandComGetRespConvert(resp, brandCom);
            return resp;
        });

        UniPage<BrandComGetResp> uniPage = conversionService.convert(respPage, UniPage.class);


        UniResp<UniPage<BrandComGetResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(uniPage);
        return uniResp;
    }

    @Override
    public UniResp<UniPage<BrandComGetResp>> search(
            Integer size,
            Integer page,
            List<String> sort,
            String q) {
        Sort sortObj = null;

        if (sort.size() > 0) {
            sortObj = ParamUtils.toSort(sort.toArray(new String[0]));
        }

        PageRequest pageRequest = new PageRequest(page, size, sortObj);

        Predicate p =
                allOf(
                        QBrandCom.brandCom.deleted.in(false),
                        !StringUtils.isEmpty(q) ? QBrandCom.brandCom.brandComName.like("%" + q + "%") : null
                );
        Page<BrandCom> brandComPage = brandComRepo.findAll(
                p, pageRequest
        );
        Assert.notNull(brandComPage, "无查询结果");

        Page<BrandComGetResp> respPage = brandComPage.map(brandCom -> {
            BrandComGetResp resp = new BrandComGetResp();
            resp = brandComConvert.brandComGetRespConvert(resp, brandCom);
            return resp;
        });

        UniPage<BrandComGetResp> uniPage = conversionService.convert(respPage, UniPage.class);


        UniResp<UniPage<BrandComGetResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(uniPage);
        return uniResp;
    }

    public UniResp<List<Map<String, String>>> getBrandAppList(
            String brandAppId
    ) {
        BrandApp brandApp = brandAppRepo.findOne(brandAppId);

        App app = appRepo.findOne(
                QApp.app.systemTypeEnum.eq(SubSysTypeEnum.QH_SHOP)
        );

        List<Map<String, String>> brandAppInfo = new ArrayList<>();
        brandAppRepo.findAll(
                allOf(
                        QBrandApp.brandApp.brandComId.eq(brandApp.getBrandComId()),
                        QBrandApp.brandApp.appId.eq(app.getId()),
                        QBrandApp.brandApp.deleted.in(false)
                )
        ).forEach(
                brandApp1 -> {
                    Map<String, String> info = new HashMap<>();
                    info.put("name", brandApp1.getShopName());
                    info.put("id", brandApp1.getId());
                    brandAppInfo.add(info);
                }
        );

        UniResp resp = new UniResp();
        resp.setData(brandAppInfo);
        resp.setStatus(200);
        return resp;
    }
}
