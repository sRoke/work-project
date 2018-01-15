package net.kingsilk.qh.agency.admin.resource.partner.convert

import net.kingsilk.qh.agency.admin.api.partner.dto.PartnerInfoResp
import net.kingsilk.qh.agency.domain.Partner
import net.kingsilk.qh.agency.repo.PartnerRepo
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import net.kingsilk.qh.agency.service.AddrService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/24.
 */
@Component
class PartnerConvert {
    @Autowired
    AddrService addrService

    @Autowired
    PartnerRepo partnerRepo

    def partnerInfoConvert(Partner partner) {
        PartnerInfoResp partnerInfoResp = new PartnerInfoResp()
        partnerInfoResp.userId = partner.userId
        partnerInfoResp.realName = partner.realName
        partnerInfoResp.id = partner.id
        partnerInfoResp.brandNameCN = partner.brandId
        partnerInfoResp.seq = partner.seq
        partnerInfoResp.partnerTypeEnum = partner.partnerTypeEnum.desp
        partnerInfoResp.phone = partner.phone
        partnerInfoResp.avatar = partner.avatar
        partnerInfoResp.disabled = partner.disabled
        partnerInfoResp.adc = partner.adc?.no
        partnerInfoResp.shopAddr = addrService.getAdcInfo(partner.adc?.no)
//        partnerInfoResp.shopAddr=partner.shopAddr
        if (!partner.parentId) {
            partnerInfoResp.parentName = BrandAppIdFilter.getBrandAppId()
        }else {
            Partner parent=partnerRepo.findOne(partner.parentId)
            if (parent){
                partnerInfoResp.parentName = parent.shopName
                partnerInfoResp.invitationCode = partner.invitationCode
            }
        }
        partnerInfoResp.partnerApplyStatus=partner.partnerApplyStatus.desp
        partnerInfoResp.createDate = partner.dateCreated.toLocaleString()
        partnerInfoResp.idNo = partner.idNo

        return partnerInfoResp
    }

    def partnerRespConvert(Partner partner) {
        PartnerInfoResp partnerInfoResp = new PartnerInfoResp()
//        partnerInfoResp.memberId = partner.memberId
        partnerInfoResp.realName = partner.realName
        partnerInfoResp.id = partner.id
        partnerInfoResp.brandNameCN = partner.brandId
        partnerInfoResp.seq = partner.seq
        partnerInfoResp.partnerTypeEnum = partner.partnerTypeEnum.desp
        partnerInfoResp.phone = partner.phone
        partnerInfoResp.avatar = partner.avatar
        partnerInfoResp.disabled = partner.disabled
        partnerInfoResp.memo = partner.memo
        return partnerInfoResp
    }
}
