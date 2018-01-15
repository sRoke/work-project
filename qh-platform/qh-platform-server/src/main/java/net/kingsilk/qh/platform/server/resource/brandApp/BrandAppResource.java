package net.kingsilk.qh.platform.server.resource.brandApp;

import com.querydsl.core.types.dsl.Expressions;
import io.swagger.annotations.Api;
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
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppPageReq;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppReq;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import net.kingsilk.qh.platform.core.SubSysTypeEnum;
import net.kingsilk.qh.platform.domain.*;
import net.kingsilk.qh.platform.repo.AppRepo;
import net.kingsilk.qh.platform.repo.BrandAppRepo;
import net.kingsilk.qh.platform.repo.BrandComRepo;
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

import javax.ws.rs.core.MediaType;

@Api(
        tags = "BrandApp",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "品牌商拥有的应用"
)
@Component
public class BrandAppResource implements BrandAppApi {

    @Autowired
    private BrandAppRepo brandAppRepo;

    @Autowired
    private BrandComRepo brandComRepo;

    @Autowired
    private AppRepo appRepo;

    @Autowired
    private BrandAppConvert brandAppConvert;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Autowired
    private OrgApi orgApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private OrgStaffApi orgStaffApi;

    @Autowired
    private net.kingsilk.qh.agency.api.brandApp.authorities.AuthoritiesApi authoritiesApi;

    @Autowired
    private net.kingsilk.qh.activity.api.brandApp.vote.authorities.AuthoritiesApi atAuthoritiesApi;

    @Autowired
    private AuthoritiesApi shopAuthoritiesApi;

    @Autowired
    private QhPlatformProperties qhPlatformProperties;

    @Override
    public UniResp<String> add(BrandAppReq brandAppReq) {
        Assert.notNull(brandAppReq.getBrandComId(), "品牌商id不能为空");
        Assert.notNull(brandAppReq.getAppId(), "请选择要新增的应用");
        BrandApp brandApp = new BrandApp();
        brandApp.setBrandComId(brandAppReq.getBrandComId());
        brandApp.setWxComAppId(qhPlatformProperties.getQhOAuth().getWap().getWxComAppId());
        brandApp.setAppId(brandAppReq.getAppId());
        if (!StringUtils.isEmpty(brandAppReq.getDate())) {
            brandApp.setExpireDate(brandAppReq.getDate());
        }
        App app = appRepo.findOne(brandAppReq.getAppId());
        if (StringUtils.isEmpty(brandAppReq.getShopName())) {
            brandApp.setShopName(app.getAppName());
        } else {
            brandApp.setShopName(brandAppReq.getShopName());
        }

        //todo 一个应用是一个组织 去oauth里面新增组织

        BrandCom brandCom = brandComRepo.findOne(
                Expressions.allOf(
                        QBrandCom.brandCom.deleted.in(false),
                        QBrandCom.brandCom.id.eq(brandAppReq.getBrandComId())
                ));

        net.kingsilk.qh.oauth.api.UniResp<net.kingsilk.qh.oauth.api.UniPage<UserGetResp>> userIdresp =
                userApi.search(99, 0, null, brandCom.getPhone());
        String userId = userIdresp.getData().getContent().get(0).getId();
        OrgAddReq orgAddReq = new OrgAddReq();
        orgAddReq.setName(brandApp.getShopName());
        orgAddReq.setStatus(OrgStatusEnum.ENABLED);
        net.kingsilk.qh.oauth.api.UniResp<String> oauthResp = orgApi.add(userId, orgAddReq);
        Assert.notNull(oauthResp.getData(), "新增组织失败");

        brandApp.setOwnerOrgId(oauthResp.getData());
        brandAppRepo.save(brandApp);

        OrgStaffAddReq orgStaffAddReq = new OrgStaffAddReq();
        orgStaffAddReq.setUserId(userId);
        orgStaffAddReq.setOrgId(oauthResp.getData());
        orgStaffAddReq.setStatus(OrgStaffStatusEnum.ENABLED);
        net.kingsilk.qh.oauth.api.UniResp<String> orgStaffResp =
                orgStaffApi.add(userId, oauthResp.getData(), orgStaffAddReq);
        Assert.notNull(orgStaffResp, "新增组织失败");

        //TODO  同时给商家的拥有者一个超级管理员的权限
        if (app.getSystemTypeEnum().equals(SubSysTypeEnum.QH_AGENCY)) {
            //调用agency的新增超级管理员以及一些其他操作
            net.kingsilk.qh.agency.api.UniResp<String> uniResp =
                    authoritiesApi.setSAStaff(brandApp.getId(), userId, oauthResp.getData(), brandCom.getBrandComName(), brandCom.getPhone());
            System.out.println(brandCom.getBrandComName() + "    " + brandCom.getPhone());
            Assert.notNull(uniResp.getData(), "新增超级管理员失败");
        }
        if (app.getSystemTypeEnum().equals(SubSysTypeEnum.QH_ACTIVITY)) {
            //调用activity的新增超级管理员及一些操作
            net.kingsilk.qh.activity.api.UniResp<String> atResp =
                    atAuthoritiesApi.setSAStaff(brandApp.getId(), userId, oauthResp.getData());
            Assert.notNull(atResp.getData(), "新增超级管理员失败");
        }

        if (app.getSystemTypeEnum().equals(SubSysTypeEnum.QH_MALL)) {
            //todo 微商城
        }
        if (app.getSystemTypeEnum().equals(SubSysTypeEnum.QH_SHOP)) {
            //todo 门店
            net.kingsilk.qh.shop.api.UniResp<String> shopResp =
                    shopAuthoritiesApi.setSAStaff(brandApp.getId(), userId, oauthResp.getData());
            Assert.notNull(shopResp.getData(), "新增超级管理员失败");
        }


        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("success");
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<BrandAppResp> info(String id) {
        BrandApp brandApp = brandAppRepo.findOne(id);
        BrandAppResp brandAppResp = new BrandAppResp();
        UniResp<BrandAppResp> uniResp = new UniResp<>();
        if (brandApp != null) {
            App app = appRepo.findOne(brandApp.getAppId());
            Assert.notNull(app, "没有此应用");
            brandAppResp = brandAppConvert.brandAppRespConvert(brandApp);
            brandAppResp.setAppName(app.getAppName());
            uniResp.setData(brandAppResp);
        }
        uniResp.setData(brandAppResp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<UniPage<BrandAppResp>> page(BrandAppPageReq brandAppPageReq) {
        Assert.notNull(brandAppPageReq.getBrandComId(), "品牌商id不能为空");

        PageRequest pageRequest = new PageRequest(
                brandAppPageReq.getPage(),
                brandAppPageReq.getSize(),
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));

        Page<BrandApp> page = brandAppRepo.findAll(
                Expressions.allOf(
                        QBrandApp.brandApp.deleted.in(false),
                        QBrandApp.brandApp.brandComId.eq(brandAppPageReq.getBrandComId()))
                , pageRequest);

        Page<BrandAppResp> respPage = page.map(brandApp -> {
            App app = appRepo.findOne(brandApp.getAppId());
            BrandAppResp brandAppResp = brandAppConvert.brandAppRespConvert(brandApp);
            brandAppResp.setAppName(app.getAppName());
            return brandAppResp;
        });

        UniPage<BrandAppResp> uniPage = conversionService.convert(respPage, UniPage.class);

        UniResp<UniPage<BrandAppResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(uniPage);
        return uniResp;
    }

    @Override
    public UniResp<String> del(String id) {
        BrandApp brandApp = brandAppRepo.findOne(id);
        brandApp.setDeleted(true);
        String orgId = brandApp.getOwnerOrgId();

        BrandCom brandCom = brandComRepo.findOne(
                Expressions.allOf(
                        QBrandCom.brandCom.deleted.in(false),
                        QBrandCom.brandCom.id.eq(brandApp.getBrandComId())
                ));
        net.kingsilk.qh.oauth.api.UniResp<net.kingsilk.qh.oauth.api.UniPage<UserGetResp>> userIdresp =
                userApi.search(99, 0, null, brandCom.getPhone());
        String userId = userIdresp.getData().getContent().get(0).getId();
        orgApi.del(userId, orgId);

        brandAppRepo.save(brandApp);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setData("success");
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<String> wxMpId(String brandAppId, String wxMpId) {
        Assert.notNull(wxMpId, "关联的微信公众号不能为空");
        BrandApp brandApp = brandAppRepo.findOne(brandAppId);
        Assert.notNull(brandApp, "没有此关联信息");
        brandApp.setWxComAppId(qhPlatformProperties.getQhOAuth().getWap().getWxComAppId());
        brandApp.setWxMpId(wxMpId);
        brandAppRepo.save(brandApp);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("success");
        return uniResp;
    }


}
