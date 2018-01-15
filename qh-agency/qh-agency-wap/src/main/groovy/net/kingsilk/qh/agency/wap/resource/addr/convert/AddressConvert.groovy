package net.kingsilk.qh.agency.wap.resource.addr.convert

import net.kingsilk.qh.agency.domain.Address
import net.kingsilk.qh.agency.wap.api.addr.dto.AddrModel
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
        final Object parent1 =parent2 == null ? null : parent2.parent
        addrModel.province = parent1 == null ? null : parent1.name
        return addrModel
    }
}
