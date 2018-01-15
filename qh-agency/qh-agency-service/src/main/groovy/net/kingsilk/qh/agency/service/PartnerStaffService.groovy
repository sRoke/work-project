package net.kingsilk.qh.agency.service

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto.PartnerStaffSaveReq
import net.kingsilk.qh.agency.core.PartnerTypeEnum
import net.kingsilk.qh.agency.domain.Partner
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.QPartnerStaff
import net.kingsilk.qh.agency.repo.PartnerRepo
import net.kingsilk.qh.agency.repo.PartnerStaffRepo
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffAddReq
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi
import net.kingsilk.qh.oauth.core.OrgStaffStatusEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert

/**
 * Created by yanfq on 17-4-6.
 */
@Service
//@CompileStatic
class PartnerStaffService {

    @Autowired
    PartnerStaffRepo partnerStaffRepo

    @Autowired
    SecService secService

    @Autowired
    UserService userService

    @Autowired
    OrgStaffApi orgStaffApi

    @Autowired
    PartnerRepo partnerRepo


    List<PartnerStaff> findByUserIdAndBrandAppId(String userId, String brandAppId) {
        List<PartnerStaff> list = partnerStaffRepo.findAll(
                Expressions.allOf(
                        QPartnerStaff.partnerStaff.deleted.in([false, null]),
                        userId ? QPartnerStaff.partnerStaff.userId.eq(userId) : null,
                        brandAppId ? QPartnerStaff.partnerStaff.brandAppId.eq(brandAppId) : null,
                )
        ).toList()
        return list;
    }

    /**
     * 当前登录的会员
     * @return
     */
    PartnerStaff getCurPartnerStaff() {
        String curUserId = secService.curUserId()
        String brandAppId = BrandAppIdFilter.getBrandAppId()
        PartnerStaff partnerStaff = partnerStaffRepo.findOneByUserIdAndBrandAppIdAndDisabledAndDeleted(curUserId, brandAppId, false, false)
        return partnerStaff
    }

    public PartnerStaff convertReqToMember(PartnerStaff member, PartnerStaffSaveReq partnerStaffSaveReq) {
        Set<PartnerTypeEnum> tag = new HashSet<PartnerTypeEnum>()
//        ((HashSet<PartnerTypeEnum>) tag).add(this.getTags().getCode());
        member.setMemo(partnerStaffSaveReq.getMemo())
        member.setUserId(partnerStaffSaveReq.getUserId())
        member.setDisabled(partnerStaffSaveReq.isDisabled())
        return member
    }

//    public PartnerStaff convertPage(Page<PartnerStaff> page, PartnerStaffPageResp memberPageResp) {
//        page.map()
//        return partnerStaff;
//    }


    PartnerStaff register(String userId, Partner partner) {

        String brandAppId = BrandAppIdFilter.getBrandAppId()
        PartnerStaff partnerStaff = partnerStaffRepo.findOneByUserIdAndBrandAppIdAndDisabledAndDeleted(userId, brandAppId, false, false)
        if (partnerStaff) {
            return partnerStaff
        } else {
            partnerStaff = new PartnerStaff()
            partnerStaff.dateCreated = new Date()
            partnerStaff.brandAppId = brandAppId
            partnerStaff.userId = userId
            partnerStaff.partner = partner
//            member.phone = oauthUser.phone as String
            partnerStaff.authorities = null
//            member.realName = null
//            member.idNumber = null
            partnerStaff = partnerStaffRepo.save(partnerStaff)
            //TODO 新增渠道商员工时在oauth里面也新增一份
            OrgStaffAddReq orgStaffAddReq = new OrgStaffAddReq()
            orgStaffAddReq.setUserId(userId)

            //TODO 目前先以staff的id作为orgStaff的工号（orgStaff很多字段都没有）
            orgStaffAddReq.setJobNo(partnerStaff.getId())
            orgStaffAddReq.setOrgId(partner.orgId)
            orgStaffAddReq.setRealName(partner.getRealName())
            orgStaffAddReq.setStatus(OrgStaffStatusEnum.ENABLED)
            net.kingsilk.qh.oauth.api.UniResp<String> uniResp =
                    orgStaffApi.add(userId, partner.orgId, orgStaffAddReq)
            Assert.notNull(uniResp, "新增组织失败")

        }
        return partnerStaff
    }


}
