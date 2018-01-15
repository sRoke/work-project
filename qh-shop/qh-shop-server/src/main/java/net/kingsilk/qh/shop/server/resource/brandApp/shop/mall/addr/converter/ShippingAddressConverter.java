package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.addr.converter;

import net.kingsilk.qh.shop.api.brandApp.shop.mall.addr.dto.AddrModel;
import net.kingsilk.qh.shop.domain.Adc;
import net.kingsilk.qh.shop.domain.Order;
import net.kingsilk.qh.shop.repo.AdcRepo;
import net.kingsilk.qh.shop.service.service.AddrService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ShippingAddressConverter implements Converter<Order.ShippingAddress, AddrModel> {

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private AddrService addrService;

    @Override
    public AddrModel convert(Order.ShippingAddress source) {
        AddrModel addrModel = new AddrModel();
        BeanUtils.copyProperties(source, addrModel);
        addrModel.setAdcNo(source.getAdc());
        Adc adc = adcRepo.findOneByNo(source.getAdc());
        addrService.addrModelAdc(addrModel, adc);
        return addrModel;
    }
}
