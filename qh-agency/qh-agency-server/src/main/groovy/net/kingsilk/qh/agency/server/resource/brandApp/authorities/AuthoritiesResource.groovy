package net.kingsilk.qh.agency.server.resource.brandApp.authorities

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.authorities.AuthoritiesApi
import net.kingsilk.qh.agency.core.PartnerApplyStatusEnum
import net.kingsilk.qh.agency.core.PartnerTypeEnum
import net.kingsilk.qh.agency.core.StaffAuthorityEnum
import net.kingsilk.qh.agency.domain.Partner
import net.kingsilk.qh.agency.domain.PartnerAccount
import net.kingsilk.qh.agency.domain.QStaff
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.repo.CompanyRepo
import net.kingsilk.qh.agency.repo.PartnerAccountRepo
import net.kingsilk.qh.agency.repo.PartnerRepo
import net.kingsilk.qh.agency.repo.StaffRepo
import net.kingsilk.qh.agency.service.CommonService
import net.kingsilk.qh.agency.service.SecService
import net.kingsilk.qh.agency.service.StaffAuthorityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 *
 */
@Component
class AuthoritiesResource implements AuthoritiesApi {

    @Autowired
    StaffRepo staffRepo

    @Autowired
    SecService secService

    @Autowired
    CompanyRepo companyRepo

    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    PartnerAccountRepo partnerAccountRepo

//    @Autowired
//    StaffUserDetailsService staffUserDetailsService


    @Autowired
    StaffAuthorityService staffAuthorityService

    @Autowired
    CommonService commonService

    @Override
    UniResp<Set<String>> getAuthorities(
            String brandAppId
    ) {
        String userId = secService.curUserId();
        Staff staff = staffRepo.findOne(
                Expressions.allOf(
                        QStaff.staff.brandAppId.eq(brandAppId),
                        QStaff.staff.userId.eq(userId)
                ))
        Set<String> authorities
        if (staff) {
            authorities = staffAuthorityService.getAuthorities(staff)
            authorities.each {
                it
                staffAuthorityService.fillAuth(authorities, StaffAuthorityEnum.valueOf(it))
            }
        } else {
            //TODO 渠道商员工的权限暂且没做
            authorities = new LinkedHashSet<>()
            authorities.add("PARTNERSTAFF")
        }
        return new UniResp(status: 200, data: authorities);
    }

    @Override
    UniResp<String> setSAStaff(String brandAppId, String userId, String orgId,String shopName,String phone) {
        Staff staff = new Staff()
        staff.setUserId(userId)
        Set<String> set = new LinkedHashSet<>()
        set.add("SA")
        staff.setAuthorities(set)
        staff.setBrandAppId(brandAppId)
        staffRepo.save(staff)

        //todo 在渠道商表中新增一个partner 类型为brandCom
        Partner partner = new Partner()
        partner.setBrandAppId(brandAppId)
        partner.setUserId(userId)
        partner.setRealName(shopName)
        partner.setPartnerTypeEnum(PartnerTypeEnum.BRAND_COM)
        partner.setOrgId(orgId)
        partner.setSeq(commonService.getDateString())
        partner.setPartnerApplyStatus(PartnerApplyStatusEnum.NORMAL)
        partner.setPhone(phone)
        partnerRepo.save(partner)

        PartnerAccount partnerAccount = new PartnerAccount()
        partnerAccount.setBrandAppId(brandAppId)
        partnerAccount.setPartner(partner)
        partnerAccount.setBalance(0)
        partnerAccount.setOwedBalance(0)
        partnerAccount.setFreezeBalance(0)
        partnerAccount.setNoCashBalance(0)
        partnerAccountRepo.save(partnerAccount)

        UniResp<String> uniResp = new UniResp<>()
        uniResp.setStatus(200)
        uniResp.setData(staff.getId())
        return uniResp
    }
}
