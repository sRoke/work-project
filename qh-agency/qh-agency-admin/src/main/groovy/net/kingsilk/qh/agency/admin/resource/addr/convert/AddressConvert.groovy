package net.kingsilk.qh.agency.admin.resource.addr.convert

import net.kingsilk.qh.agency.admin.api.addr.dto.AddrQueryAdcResp

import net.kingsilk.qh.agency.domain.Adc
import net.kingsilk.qh.agency.domain.Address
import net.kingsilk.qh.agency.admin.api.addr.dto.AddrModel
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/26.
 */
@Component
class AddressConvert {
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
        addrModel.area = ((String) (address.adc.name));
        final Object parent = address.adc.parent;
        addrModel.city = parent == null ? null : parent.name
        final Object parent2 = address.adc.parent;
        final Object parent1 = parent2 == null ? null : parent2.parent
        addrModel.province = parent1 == null ? null : parent1.name
        return addrModel
    }

    AddrQueryAdcResp queryAdcRespConvert(Adc parent, List<Adc> adcList) {
        AddrQueryAdcResp resp=new AddrQueryAdcResp()
        resp.parent = parent?.name
        resp.parentNo=parent?.no
        resp.list = []
        adcList.each { Adc adc ->
            AddrQueryAdcResp.AdcModel model = new AddrQueryAdcResp.AdcModel()
            model.no = adc.no
            model.name = adc.name
            resp.list.add(model)
        }
        return resp;
    }
}
