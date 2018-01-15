package net.kingsilk.qh.agency.server.resource.brandApp.partner

import net.kingsilk.qh.agency.api.brandApp.partner.PartnerInfoResp
import net.kingsilk.qh.agency.domain.Adc
import net.kingsilk.qh.agency.domain.Certificate
import net.kingsilk.qh.agency.domain.Partner
import net.kingsilk.qh.agency.repo.AdcRepo
import net.kingsilk.qh.agency.repo.CertificateRepo
import net.kingsilk.qh.agency.repo.PartnerRepo
import net.kingsilk.qh.agency.service.AddrService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 *
 */
@Component
class PartnerConvert {
    @Autowired
    AddrService addrService

    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    AdcRepo adcRepo

    @Autowired
    CertificateRepo certificateRepo

    def partnerInfoConvert(Partner partner) {
        PartnerInfoResp partnerInfoResp = new PartnerInfoResp()
        partnerInfoResp.userId = partner.userId
        partnerInfoResp.realName = partner.realName
        partnerInfoResp.id = partner.id
        partnerInfoResp.brandNameCN = partner.brandAppId
        partnerInfoResp.seq = partner.seq
        partnerInfoResp.partnerType = partner.partnerTypeEnum.desp
        partnerInfoResp.partnerTypeEnum = partner.partnerTypeEnum.code
        partnerInfoResp.shopName = partner.shopName
        partnerInfoResp.phone = partner.phone
        partnerInfoResp.avatar = partner.avatar
        partnerInfoResp.disabled = partner.disabled
        partnerInfoResp.orgId = partner.orgId

        partnerInfoResp.shopBrandAppId=partner.shopBrandAppId
        partnerInfoResp.shopId=partner.shopId

        Adc adc = adcRepo.findOneByNo(partner.adc)
        partnerInfoResp.adc = adc.no
        if (adc.parent) {
            partnerInfoResp.cityNo = adc.parent.no
        }
        if (adc.parent?.parent) {
            partnerInfoResp.provinceNo = adc.parent.parent.no
        }
        partnerInfoResp.shopAddr = addrService.getAdcInfo(adc?.no)
//        partnerInfoResp.shopAddr=partner.shopAddr
//        if (!partner.parent) {
//            partnerInfoResp.parentName = BrandAppIdFilter.getBrandAppId()
//        }else {
//                partnerInfoResp.parentName = partner.parent.shopName
        partnerInfoResp.invitationCode = partner.invitationCode
//        }
        partnerInfoResp.parentId = partner.parent?.id
        partnerInfoResp.partnerApplyStatus = partner.partnerApplyStatus.desp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        partnerInfoResp.createDate = sdf.format(partner.dateCreated)
//        partnerInfoResp.createDate = partner.dateCreated.toLocaleString()
        partnerInfoResp.idNo = partner.idNo
        if (partner.parent) {
            partnerInfoResp.parentDesp = partner.parent.realName + " " + partner.parent.phone + " " + partner.parent.partnerTypeEnum.desp + " " + addrService.getAdcInfo(partner?.parent?.adc)
        }
        return partnerInfoResp
    }

    def partnerInfoRespConvert(Partner partner) {
        PartnerInfoResp partnerInfoResp = new PartnerInfoResp()
//        partnerInfoResp.memberId = partner.memberId
        partnerInfoResp.realName = partner.realName
        partnerInfoResp.id = partner.id
        partnerInfoResp.brandNameCN = partner.brandAppId
        partnerInfoResp.seq = partner.seq
        partnerInfoResp.partnerType = partner.partnerTypeEnum?.desp
        partnerInfoResp.phone = partner.phone
        partnerInfoResp.avatar = partner.avatar
        partnerInfoResp.disabled = partner.disabled
        partnerInfoResp.userId=partner.userId

        partnerInfoResp.shopBrandAppId=partner.shopBrandAppId
        partnerInfoResp.shopId=partner.shopId

        partnerInfoResp.memo = partner.memo
        partnerInfoResp.partnerApplyStatus = partner.partnerApplyStatus?.desp
        Adc adc = adcRepo.findOneByNo(partner.adc)
        partnerInfoResp.adc = adc?.no
        partnerInfoResp.shopAddr = addrService.getAdcInfo(adc?.no)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        partnerInfoResp.createDate = sdf.format(partner.dateCreated)
        if (partner.parent) {
            partnerInfoResp.parentDesp = partner.parent.realName + " " + partner.parent.phone + " " + partner.parent.partnerTypeEnum.desp
        }
        partnerInfoResp.certificateStatus = "未授权"
        Certificate certificate = certificateRepo.findOneByBrandAppIdAndPartnerId(partner.brandAppId, partner.id)
        if (certificate) {
            if (new Date().after(certificate.startTime) && new Date().before(certificate.endTime)) {
                partnerInfoResp.certificateStatus = "已授权"
            } else {
                partnerInfoResp.certificateStatus = "已过期"
            }
        }

        return partnerInfoResp
    }
}
