package net.kingsilk.qh.agency.wap.resource.member.convert

import net.kingsilk.qh.agency.domain.Address
import net.kingsilk.qh.agency.domain.Partner
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.service.AddrService
import net.kingsilk.qh.agency.wap.api.member.dto.AdcModel
import net.kingsilk.qh.agency.wap.api.member.dto.MemberInfoResp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/24.
 */
@Component
class MemberConvert {
    @Autowired
    AddrService addrService
    def memberInfoRespConvert(PartnerStaff member, Partner partner) {
        MemberInfoResp resp = new MemberInfoResp()
        resp.shopName = partner?.shopName
//        resp.addr = partner ? adcConvert(addrService.getAdcInfo(partner?.adc?.no)) : null
        //TODO
        return resp
    }

    def adcConvert(Address address) {
        AdcModel adcModel = new AdcModel()
        if (!address) {
            return;
        }
        adcModel.id = address.id
        adcModel.adcNo = address.adc.no
        adcModel.street = address.street
        adcModel.receiver = address.receiver
        adcModel.phone = address.phone
        adcModel.defaultAddr = address.defaultAddr
        adcModel.area = address.adc.name
        adcModel.city = address.adc.parent?.name
        adcModel.province = address.adc.parent?.parent?.name
        return adcModel
    }
}
