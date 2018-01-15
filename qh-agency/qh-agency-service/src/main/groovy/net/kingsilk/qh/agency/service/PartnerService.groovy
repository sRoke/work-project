package net.kingsilk.qh.agency.service

import net.kingsilk.qh.agency.api.ErrStatus
import net.kingsilk.qh.agency.api.ErrStatusException
import net.kingsilk.qh.agency.core.PartnerApplyStatusEnum
import net.kingsilk.qh.agency.domain.Partner
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.QPartnerStaff
import net.kingsilk.qh.agency.repo.PartnerRepo
import net.kingsilk.qh.agency.repo.PartnerStaffRepo
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import static com.querydsl.core.types.dsl.Expressions.allOf

/**
 *
 */

@Service
class PartnerService {

    @Autowired
    private PartnerStaffService partnerStaffService

    @Autowired
    private UserService userService

    @Autowired
    private PartnerRepo partnerRepo

    @Autowired
    private PartnerStaffRepo partnerStaffRepo

    @Autowired
    private SecService secService
    public void check() {

        String userId=secService.curUserId()
        String brandAppId = BrandAppIdFilter.getBrandAppId()
        PartnerStaff partnerStaff = partnerStaffRepo.findOne(
                allOf(
                        QPartnerStaff.partnerStaff.brandAppId.eq(brandAppId),
                        QPartnerStaff.partnerStaff.userId.eq(userId),
                        QPartnerStaff.partnerStaff.deleted.isFalse()
                )
        )

        Partner partner = partnerRepo.findOneByUserIdAndBrandAppId(userId, brandAppId)
        if (partner == null) {
            throw new ErrStatusException(
                    ErrStatus.PARTNER_404,
                    "未找到该渠道商"
            )
        }
        if (partner.partnerApplyStatus == PartnerApplyStatusEnum.APPLYING) {
            throw new ErrStatusException(
                    ErrStatus.PARTNER_APPLYING,
                    "渠道商申请中"
            )
        } else if (partner.partnerApplyStatus == PartnerApplyStatusEnum.REJECT) {
            throw new ErrStatusException(
                    ErrStatus.PARTNER_REJECT,
                    "渠道商申请被拒绝"
            )
        }
        if (partnerStaff == null) {
            throw new ErrStatusException(
                    ErrStatus.USER_404_WITH_PARTNER_STAFF,
                    "未找到该渠道商员工"
            )
        }
    }
}
