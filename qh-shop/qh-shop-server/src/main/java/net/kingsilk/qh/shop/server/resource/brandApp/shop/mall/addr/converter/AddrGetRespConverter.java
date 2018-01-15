package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.addr.converter;

import com.google.common.collect.Lists;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.addr.dto.AddrModel;
import net.kingsilk.qh.shop.domain.Adc;
import net.kingsilk.qh.shop.repo.AdcRepo;
import net.kingsilk.qh.shop.service.service.AddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddrGetRespConverter implements Converter<AddrGetResp, AddrModel> {

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private AddrService addrService;

    @Override
    public AddrModel convert(AddrGetResp addrGetResp) {
        AddrModel addrModel = new AddrModel();
        addrModel.setAdcNo(addrGetResp.getAdc());
        addrModel.setStreet(addrGetResp.getStreet());
        addrModel.setDefaultAddr(addrGetResp.isDefaultAddr());
        addrModel.setPhone(Lists.newArrayList(addrGetResp.getPhones()).stream().findFirst().get());
        addrModel.setReceiver(addrGetResp.getContact());
        addrModel.setId(addrGetResp.getId());
        Adc adc = adcRepo.findOneByNo(addrGetResp.getAdc());
        addrService.addrModelAdc(addrModel, adc);
        return addrModel;
    }
}
