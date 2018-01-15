package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.refund.convert;

import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.refund.dto.RefundInfoResp;
import net.kingsilk.qh.shop.api.brandApp.shop.refund.dto.AddressInfo;
import net.kingsilk.qh.shop.domain.Adc;
import net.kingsilk.qh.shop.domain.Refund;
import net.kingsilk.qh.shop.repo.AdcRepo;
import net.kingsilk.qh.shop.service.service.AddrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mallAddressConvert")
public class AddressConvert {

    @Autowired
    private AddrService addrService;

    @Autowired
    private UserApi userApi;

    @Autowired
    private AdcRepo adcRepo;

    public void aouthToShopAddrConvert(RefundInfoResp infoResp, AddrGetResp addr) {

        if (StringUtils.isBlank(addr.getId())) {
            throw new ErrStatusException(ErrStatus.FINDNULL, "没有默认地址");
        }
        String address = addrService.getAdcInfo(addr.getAdc());
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setAddress(address); //省市区
        addressInfo.setStreet(addr.getStreet());
        addressInfo.setPhone(addr.getPhones().iterator().next());
        UniResp<UserGetResp> user = userApi.getInfoByUserId(addr.getUserId());
        if (user != null) {
            addressInfo.setReceiver(user.getData().getRealName());

        }
        infoResp.setAddrInfo(addressInfo);
    }


    public AddressInfo toAddrModel(Refund.RefundAddress refundAddress) {
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setPhone(refundAddress.getPhone());
        addressInfo.setReceiver(refundAddress.getReceiver());
        addressInfo.setStreet(refundAddress.getStreet());
        addressInfo.setAddress(refundAddress.getAdc());
        Adc adc = adcRepo.findOneByNo(refundAddress.getAdc());
        if (!StringUtils.isEmpty(refundAddress.getAdc())) {
            addressInfo.setCountyNo(adc.getNo());
            addressInfo.setCounty(adc.getName());
            if (adc.getParent() != null) {
                addressInfo.setCity(adc.getParent().getName());
                addressInfo.setCityNo(adc.getParent().getNo());
                if (adc.getParent().getParent() != null) {
                    addressInfo.setProvince(adc.getParent().getParent().getName());
                    addressInfo.setProvinceNo(adc.getParent().getParent().getNo());
                }
            }
        }
        return addressInfo;
    }

}
