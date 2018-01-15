package net.kingsilk.qh.shop.server.resource.common;

import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.common.CommonApi;
import net.kingsilk.qh.shop.api.common.dto.AddrQueryAdcResp;
import net.kingsilk.qh.shop.core.GuideTypeEnum;
import net.kingsilk.qh.shop.domain.Adc;
import net.kingsilk.qh.shop.domain.GuidePage;
import net.kingsilk.qh.shop.domain.QGuidePage;
import net.kingsilk.qh.shop.repo.AdcRepo;
import net.kingsilk.qh.shop.repo.GuidePageRepo;
import net.kingsilk.qh.shop.server.resource.common.convert.AddressConvert;
import net.kingsilk.qh.shop.service.service.AddrService;
import net.kingsilk.qh.shop.service.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Component
public class CommonResource implements CommonApi {

    @Autowired
    private AddrService addrService;

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private AddressConvert addressConvert;

    @Autowired
    private SecService secService;

    @Autowired
    private GuidePageRepo guidePageRepo;

    @Override
    public UniResp<Date> queryDate() {
        UniResp uniResp = new UniResp<Date>();
        uniResp.setData(new Date());
        uniResp.setStatus(200);
        uniResp.setTimestamp(new Date());
        return uniResp;
    }

    @Override
    public UniResp<List<Map<String, Object>>> getAdcList() {
        List<Map<String, Object>> resp;
        if (addrService.getAddr != null && addrService.getAddr.size() > 0) {
            resp = addrService.getAddr;
        } else {
            resp = addrService.getAddrList();
            addrService.getAddr = resp;
        }
        UniResp<List<Map<String, Object>>> uniResp = new UniResp<>();
        uniResp.setData(resp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<AddrQueryAdcResp> queryAdc(String adcNo) {
        Adc adc = null;
        if (!StringUtils.isEmpty(adcNo)) {
            adc = adcRepo.findOneByNo(adcNo);
        }
        List<Adc> adcList = adcRepo.findAllByParent(adc);
        AddrQueryAdcResp resp = addressConvert.queryAdcRespConvert(adc, adcList);
        UniResp<AddrQueryAdcResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);
        return uniResp;
    }

    public UniResp<Boolean> guidePage(String type) {

        String userId = secService.curUserId();

        GuidePage guidePage = guidePageRepo.findOne(
                allOf(
                        QGuidePage.guidePage.isUsed.eq(true),
                        QGuidePage.guidePage.userId.eq(userId),
                        QGuidePage.guidePage.type.eq(GuideTypeEnum.valueOf(type))
                )
        );
        UniResp<Boolean> resp = new UniResp<>();
        if (guidePage != null) {
            resp.setData(false);
            resp.setStatus(200);
            return resp;
        }
        guidePage = new GuidePage();
        guidePage.setType(GuideTypeEnum.valueOf(type));
        guidePage.setUserId(userId);
        guidePage.setUsed(true);

        guidePageRepo.save(guidePage);

        resp.setData(true);
        resp.setStatus(200);

        return resp;
    }
}
