//package net.kingsilk.qh.raffle.server.resource.raffleApp.wap.addr.converter;
//
//import net.kingsilk.qh.bargain.api.bargainApp.bargain.wap.addr.dto.AddrModel;
//import net.kingsilk.qh.bargain.domain.Adc;
//import net.kingsilk.qh.bargain.domain.Order;
//import net.kingsilk.qh.bargain.repo.AdcRepo;
//import net.kingsilk.qh.bargain.service.service.AddrService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ShippingAddressConverter implements Converter<Order.ShippingAddress, AddrModel> {
//
//    @Autowired
//    private AdcRepo adcRepo;
//
//    @Autowired
//    private AddrService addrService;
//
//    @Override
//    public AddrModel convert(Order.ShippingAddress source) {
//        AddrModel addrModel = new AddrModel();
//        BeanUtils.copyProperties(source, addrModel);
//        addrModel.setAdcNo(source.getAdc());
//        Adc adc = adcRepo.findOneByNo(source.getAdc());
//        addrService.addrModelAdc(addrModel, adc);
//        return addrModel;
//    }
//}
