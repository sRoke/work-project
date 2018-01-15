package net.kingsilk.qh.agency.server.resource.brandApp.addr

import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrModel
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrQueryAdcResp
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddressInfo
import net.kingsilk.qh.agency.domain.Adc
import net.kingsilk.qh.agency.domain.Address
import net.kingsilk.qh.agency.domain.Order
import net.kingsilk.qh.agency.domain.Refund
import net.kingsilk.qh.agency.repo.AdcRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/26.
 */
@Component
class AddressConvert {

    @Autowired
    AdcRepo adcRepo

    AddrModel addrModelConvert(Address address) {
        if (!address.asBoolean()) {
            return;

        }
        AddrModel addrModel = new AddrModel()
        addrModel.id = address.id
        addrModel.adcNo = address.adc.no
        addrModel.street = address.street
        addrModel.receiver = address.receiver
        addrModel.phone = address.phone
        addrModel.isDefault = address.defaultAddr

        Adc adc = adcRepo.findOneByNo(address.adc.no)
        if (address.adc) {
            addrModel.countyNo = adc.no
            addrModel.area = ((String) (address.adc.name));
            if (adc.parent) {
                addrModel.cityNo = adc.parent.no
                addrModel.city = ((String) (address.adc.parent.name))
                if (adc.parent.parent) {
                    addrModel.provinceNo = adc.parent.parent.no
                    addrModel.province = ((String) (address.adc.parent.parent.name))
                }
            }
        }
        return addrModel
    }

    AddrQueryAdcResp queryAdcRespConvert(Adc parent, List<Adc> adcList) {
        AddrQueryAdcResp resp = new AddrQueryAdcResp()
        resp.parent = parent?.name
        resp.parentNo = parent?.no
        resp.list = []
        adcList.each { Adc adc ->
            AddrQueryAdcResp.AdcModel model = new AddrQueryAdcResp.AdcModel()
            model.no = adc.no
            model.name = adc.name
            resp.list.add(model)
        }
        return resp;
    }

    def addressInfoConvert(Order.OrderAddress address) {
        if (!address.asBoolean()) {
            return;

        }
        AddressInfo addressInfo = new AddressInfo()
        addressInfo.receiver = address.receiver
        addressInfo.street = address.street
        addressInfo.phone = address.phone
        Adc adc = adcRepo.findOneByNo(address.adc)
        if (address.adc) {
            addressInfo.county = adc.name
            addressInfo.countyNo = adc.no
            if (adc.parent) {
                addressInfo.city = adc.parent.name
                addressInfo.cityNo = adc.parent.no
                if (adc.parent.parent) {
                    addressInfo.province = adc.parent.parent.name
                    addressInfo.provinceNo = adc.parent.parent.no
                }
            }
        }
        return addressInfo;
    }

    def convertAddressToOrderAdr(Address addressInfo) {
        if (!addressInfo) {
            return;
        }
        Order.OrderAddress address = new Order.OrderAddress()
        address.receiver = addressInfo.receiver
        address.street = addressInfo.street
        address.phone = addressInfo.phone
        address.adc = addressInfo.adc.no
        return address;
    }

    def convertAddressToRefundAdr(Address addressInfo) {
        if (!addressInfo) {
            return;
        }
        Refund.RefundAddress address = new Refund.RefundAddress()
        address.receiver = addressInfo.receiver
        address.street = addressInfo.street
        address.phone = addressInfo.phone
        address.adc = addressInfo.adc.no
        return address;
    }

    def convertRefundAdrToAddress(Refund.RefundAddress address) {
        if (!address) {
            return;
        }
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.receiver = address.receiver
        addressInfo.street=address.street
        addressInfo.phone=address.phone
        Adc adc = adcRepo.findOneByNo(address.adc)
        if (address.adc) {
            addressInfo.county = adc.name
            addressInfo.countyNo = adc.no
            if (adc.parent) {
                addressInfo.city = adc.parent.name
                addressInfo.cityNo = adc.parent.no
                if (adc.parent.parent) {
                    addressInfo.province = adc.parent.parent.name
                    addressInfo.provinceNo = adc.parent.parent.no
                }
            }
        }
        return addressInfo;
    }

}
