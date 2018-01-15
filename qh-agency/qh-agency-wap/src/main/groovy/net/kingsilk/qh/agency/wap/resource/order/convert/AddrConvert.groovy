package net.kingsilk.qh.agency.wap.resource.order.convert

import net.kingsilk.qh.agency.domain.Address
import net.kingsilk.qh.agency.wap.api.addr.dto.AddrModel
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/20.
 */
@Component
class AddrConvert {
    AddrModel addrModelConvert(Address address) {
        AddrModel addrModel = new AddrModel();
        if (!address) {
            return;
        }
        addrModel.id = address.id
        addrModel.adcNo = address.adc.no
        addrModel.street = address.street
        addrModel.receiver = address.receiver
        addrModel.phone = address.phone
        addrModel.isDefault = address.defaultAddr
        addrModel.area = address.adc.name
        addrModel.city = address.adc.parent?.name
        addrModel.province = address.adc.parent?.parent?.name
        return addrModel
    }
}
