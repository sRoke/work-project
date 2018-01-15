package net.kingsilk.qh.platform.server.service;

import net.kingsilk.qh.platform.core.BrandComStatusEnum;
import net.kingsilk.qh.platform.core.SubSysTypeEnum;
import net.kingsilk.qh.platform.domain.*;
import net.kingsilk.qh.platform.repo.AppRepo;
import net.kingsilk.qh.platform.repo.BrandAppRepo;
import net.kingsilk.qh.platform.repo.BrandComRepo;
import net.kingsilk.qh.platform.repo.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class InitDbService {

    @Autowired
    private AppRepo appRepo;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private BrandComRepo brandComRepo;

    @Autowired
    private BrandAppRepo brandAppRepo;


    static final String APP_PLATFORM = "5982c8c3add7b434a6f42f77";
    static final String APP_MALL = "5982c8c3add7b434a6f42f78";
    static final String APP_AGENCY = "5982c8c3add7b434a6f42f79";
    static final String APP_ACTIVITY = "5982c8c3add7b434a6f42f81";
    static final String APP_SHOP = "5982c8c3add7b434a6f42f82";

    static final String QhBrandComId = "5982c8c3add7b434a6f42f80";
    static final String QhBrandAppId = "59782691f8fdbc1f9b2c4323";

    @EventListener
    void onApplicationStated(ContextRefreshedEvent event) {
        checkAndAddAppOne();
        checkAndAddAppTwo();
        checkAndAddAppThree();
        checkAndAddAppFour();
        checkAndAddAppShop();
        initAdminUser();
        initQhbrandCom();
        initQhbrandApp();
    }


    private void checkAndAddAppOne() {

        App app = appRepo.findOne(APP_PLATFORM);
        if (app == null) {
            app = new App();
            app.setId(APP_PLATFORM);
            app.setLogo("");
            app.setPrice("8888");
            app.setAppName("平台支撑系统");
            app.setTrialDuration("永久");
            app.setSystemTypeEnum(SubSysTypeEnum.QH_PLATFORM);
            app.setDesp("整个平台的管理系统");
            app.setAppUrl("");
            appRepo.save(app);
        }

    }

    private void checkAndAddAppTwo() {

        App app = appRepo.findOne(APP_MALL);
        if (app == null) {
            app = new App();
            app.setId(APP_MALL);
            app.setLogo("");
            app.setPrice("8888");
            app.setAppName("微商城");
            app.setTrialDuration("永久");
            app.setSystemTypeEnum(SubSysTypeEnum.QH_MALL);
            app.setDesp("微信公众号应用");
            app.setAppUrl("");
            appRepo.save(app);

        }
    }

    private void checkAndAddAppThree() {

        App app = appRepo.findOne(APP_AGENCY);
        if (app == null) {
            app = new App();
            app.setId(APP_AGENCY);
            app.setLogo("");
            app.setPrice("8888");
            app.setAppName("生意参谋");
            app.setTrialDuration("永久");
            app.setSystemTypeEnum(SubSysTypeEnum.QH_AGENCY);
            app.setDesp("经销进货系统");
            app.setAppUrl("");
            appRepo.save(app);
        }
    }

    private void checkAndAddAppFour() {
        App app = appRepo.findOne(APP_ACTIVITY);
        if (app == null) {
            app = new App();
            app.setId(APP_ACTIVITY);
            app.setLogo("");
            app.setPrice("8888");
            app.setAppName("营销系统");
            app.setTrialDuration("永久");
            app.setSystemTypeEnum(SubSysTypeEnum.QH_ACTIVITY);
            app.setDesp("营销系统");
            app.setAppUrl("");
            appRepo.save(app);
        }

    }

    private void checkAndAddAppShop() {
        App app = appRepo.findOne(APP_SHOP);
        if (app==null){
            app = new App();
            app.setId(APP_SHOP);
            app.setLogo("");
            app.setPrice("8888");
            app.setAppName("门店系统");
            app.setTrialDuration("永久");
            app.setSystemTypeEnum(SubSysTypeEnum.QH_SHOP);
            app.setDesp("门店系统");
            app.setAppUrl("");
            appRepo.save(app);
        }

    }

    private void initAdminUser() {
        Staff user = staffRepo.findOne(
                QStaff.staff.userId.eq("58de6b27785a82000005a140")
        );
        if (user == null) {
            user = new Staff();
            user.setMemo("admin");
            user.setDisabled(true);
            user.setUserId("58de6b27785a82000005a140");
            Set<String> stringSet = new LinkedHashSet<>();
            stringSet.add("SA");
            user.setAuthorities(stringSet);
            staffRepo.save(user);
        }

    }

    private void initQhbrandCom() {
        BrandCom brandCom = brandComRepo.findOne(QhBrandComId);

        if (brandCom == null) {
            brandCom = new BrandCom();
            brandCom.setId(QhBrandComId);
            brandCom.setBrandComName("钱皇股份");
            brandCom.setStatus(BrandComStatusEnum.NORMAL);
            brandCom.setPhone("15129027056");
            brandComRepo.save(brandCom);
        }
    }

    private void initQhbrandApp() {
        BrandApp brandApp = brandAppRepo.findOne(QhBrandAppId);

        if (brandApp == null) {
            brandApp = new BrandApp();
            brandApp.setId(QhBrandAppId);
            brandApp.setOwnerOrgId("5982c8c3add7b434a6f42f81");
            brandApp.setShopName("生意参谋");
            brandApp.setAppId("5982c8c3add7b434a6f42f79");
            brandApp.setWxMpId("wx7cc0b96add4254b1");
            brandApp.setWxComAppId("wx2ad540a81da1f8db");
            brandApp.setBrandComId(QhBrandComId);
            brandApp.setDateCreated(new Date());
            brandApp.setCreatedBy("admin");
            brandApp.setExpireDate("2050-1-1");
            brandAppRepo.save(brandApp);
        }

    }

}
