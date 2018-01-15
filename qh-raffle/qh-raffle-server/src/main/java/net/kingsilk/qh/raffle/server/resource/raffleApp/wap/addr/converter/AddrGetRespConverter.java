package net.kingsilk.qh.raffle.server.resource.raffleApp.wap.addr.converter;

import com.google.common.collect.Lists;
import net.kingsilk.qh.oauth.api.user.addr.AddrApi;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.raffle.api.raffleApp.raffle.wap.addr.dto.AddrModel;
import net.kingsilk.qh.raffle.domain.Adc;
import net.kingsilk.qh.raffle.repo.AdcRepo;
import net.kingsilk.qh.raffle.service.AddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddrGetRespConverter implements Converter<AddrGetResp, AddrModel> {

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private AddrApi addrApi;

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
