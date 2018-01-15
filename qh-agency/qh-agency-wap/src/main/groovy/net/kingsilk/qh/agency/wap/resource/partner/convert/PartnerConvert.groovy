package net.kingsilk.qh.agency.wap.resource.partner.convert

import net.kingsilk.qh.agency.domain.Partner
import net.kingsilk.qh.agency.service.AddrService
import net.kingsilk.qh.agency.wap.api.partner.dto.PartnerInfoResp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/22.
 */
@Component
class PartnerConvert {

    @Autowired
    AddrService addrService
    PartnerInfoResp partnerInfoRespConvert(Partner partner){
        PartnerInfoResp infoResp=new PartnerInfoResp()

        infoResp.seq=partner.seq
        infoResp.id=partner.id
        infoResp.phone=partner.phone
        infoResp.partnerType=partner.partnerTypeEnum.desp
        infoResp.shopName=partner.shopName
//        infoResp.userName=partner.member.nickName
//        infoResp.leave="不告诉你"
        infoResp.addr=addrService.getAdcInfo(partner?.adc?.no)
        return  infoResp
    }
}
