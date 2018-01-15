package net.kingsilk.qh.platform.server.resource.app;

import net.kingsilk.qh.platform.api.UniPage;
import net.kingsilk.qh.platform.api.UniResp;
import net.kingsilk.qh.platform.api.app.AppApi;
import net.kingsilk.qh.platform.api.app.dto.AppPageReq;
import net.kingsilk.qh.platform.api.app.dto.AppReq;
import net.kingsilk.qh.platform.api.app.dto.AppResp;
import net.kingsilk.qh.platform.domain.App;
import net.kingsilk.qh.platform.domain.QApp;
import net.kingsilk.qh.platform.repo.AppRepo;
import net.kingsilk.qh.platform.server.resource.app.convert.AppConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.ws.rs.Path;

@Path("/app")
@Component
public class AppResource implements AppApi {

    @Autowired
    private AppConvert appConvert;
    @Autowired
    private AppRepo appRepo;
    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    @Override
    public UniResp<String> update(String id, AppReq appReq) {
        Assert.notNull(id, "更新错误");
        App app = appRepo.findOne(id);
        App updateApp =
                appRepo.save(appConvert.AppReqConvert(app, appReq));
        Assert.notNull(updateApp, "更新失败");
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData("success");
        return uniResp;
    }

    @Override
    public UniResp<UniPage<AppResp>> page(AppPageReq appPageReq) {
        PageRequest pageRequest = new PageRequest(appPageReq.getPage(), appPageReq.getSize(),
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")));
        Page<App> page = appRepo.findAll(
                QApp.app.deleted.in(false), pageRequest
        );
        Page<AppResp> respPage = page.map(app -> {
            AppResp appResp
                    = appConvert.AppConvert(app);
            return appResp;
        });
        UniPage<AppResp> uniPage = conversionService.convert(respPage, UniPage.class);
        UniResp<UniPage<AppResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(uniPage);
        return uniResp;
    }

    @Override
    public UniResp<AppResp> info(String id) {
        App app = appRepo.findOne(id);
        Assert.notNull(id, "查询失败");
        AppResp appResp = appConvert.AppConvert(app);
        UniResp<AppResp> uniResp = new UniResp<>();
        uniResp.setData(appResp);
        uniResp.setStatus(200);
        return uniResp;
    }

}
