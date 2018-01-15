package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.addr.converter;

import net.kingsilk.qh.shop.api.brandApp.shop.mall.addr.dto.AddrModel;
import net.kingsilk.qh.shop.domain.Address;
import net.kingsilk.qh.shop.repo.AdcRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddrModelConverter implements Converter<Address, AddrModel> {

    @Autowired
    private AdcRepo adcRepo;

    @Override
    public AddrModel convert(Address address) {
        AddrModel addrModel = new AddrModel();
        addrModel.setAdcNo(address.getAdc().getNo());
        addrModel.setStreet(address.getStreet());
        addrModel.setReceiver(address.getReceiver());
        addrModel.setPhone(address.getPhone());
        addrModel.setDefaultAddr(address.isDefaultAddr());

//        Adc adc = adcRepo.findOneByNo(address.getAdc().getNo());
//        Optional.ofNullable(address.getAdc()).ifPresent(addrAdc ->
//                {
//                    addrModel.setCountyNo(addrAdc.getNo());
//                    addrModel.setArea(addrAdc.getName());
//                    Optional.ofNullable(adc.getParent()).ifPresent(adcParent ->
//                            {
//                                addrModel.setCityNo(adcParent.getNo());
//                                addrModel.setCity(adcParent.getName());
//                                Optional.ofNullable(adcParent.getParent()).ifPresent(adcParents ->
//                                        {
//                                            addrModel.setProvince(adcParents.getName());
//                                            addrModel.setProvinceNo(adcParents.getNo());
//                                        }
//                                );
//                            }
//                    );
//                }
//        );

        return addrModel;
    }
}
