package net.kingsilk.qh.agency.server.resource.brandApp.partner.partnerStaff

import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto.PartnerStaffInfoResp
import net.kingsilk.qh.agency.domain.PartnerStaff
import org.springframework.stereotype.Component

/**
 *
 */
@Component
class PartnerStaffConvert {

    PartnerStaffInfoResp convertPartnerStaffToResp(PartnerStaff partnerStaff) {
        PartnerStaffInfoResp resp = new PartnerStaffInfoResp()
        resp.setMemo(partnerStaff.getMemo())
        resp.partnerSeq = partnerStaff.partner.seq
        resp.setUserId(partnerStaff.getUserId())
        resp.setPartnerId(partnerStaff.getPartner().getId())
        resp.setId(partnerStaff.getId())
        resp.partnerType = partnerStaff?.partner?.partnerTypeEnum?.desp
        resp.lastModifiedDate = partnerStaff.getLastModifiedDate()
        resp.dateCreated = partnerStaff.getDateCreated()
        resp.setOrgId(partnerStaff.getPartner().getOrgId())
        if (partnerStaff.isDisabled()) {
            resp.setDisabled("true")
        } else {
            resp.setDisabled("false")
        }
        return resp
    }
}
