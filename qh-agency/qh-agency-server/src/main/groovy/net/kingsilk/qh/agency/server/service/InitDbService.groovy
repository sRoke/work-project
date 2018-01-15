package net.kingsilk.qh.agency.server.service

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.core.PartnerApplyStatusEnum
import net.kingsilk.qh.agency.core.PartnerTypeEnum
import net.kingsilk.qh.agency.core.StaffAuthorityEnum
import net.kingsilk.qh.agency.domain.Partner
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.repo.PartnerRepo
import net.kingsilk.qh.agency.repo.PartnerStaffRepo
import net.kingsilk.qh.agency.repo.StaffRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

/**
 * 因为管理后台不完善，先再应用一启动的时候就导入一部分 "静态" 数据。
 */
@Service
@CompileStatic
class InitDbService {

//    static final String user_id_superAdmin = "58de6b27785a82000005a140"
    static final String user_id_httAdmin = "58e4bd0c785a82000005a141"

//    static final String user_id_qh_admin = "58e4bd0c785a82000005a141"
    static final String brandAppId = "59782691f8fdbc1f9b2c4323"
    /**
     * 郝太太管理员的ID
     */
    static final String staff_id_qh_admin = "58e593fd785a82000005a145"
    static final String partner_id_qh_admin = "5997e433f8fdbc75285f6149"

//    @Deprecated
//    static final String staff_id_htt_zll = "58e5e1442a9f2d0000313b1f"
//    @Deprecated
//    static final String member_id_htt_zll = "58e5f41d785a82000005a147"

//    @Autowired
//    CompanyRepo companyRepo

    @Autowired
    StaffRepo staffRepo

    @Autowired
    PartnerStaffRepo partnerStaffRepo

    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    InitDb2_1_0Service initDb2_1_0Service

    @EventListener
    void onApplicationStated(ContextRefreshedEvent event) {

//        checkAndAddCompanyHtt()
        checkAndAddHttStaffAdmin()
        checkAndAddQhPartner()
        initDb2_1_0Service.initPartnerStaffAccount()
        initDb2_1_0Service.initSkuStore()
//        initDb2_1_0Service.initOrder()
//        checkAndAddHttStaffZll()
//        checkAndAddHttMemberZll()

    }

    /**
     * Staff : 郝太太 管理员
     */
    private void checkAndAddHttStaffAdmin() {
        if (staffRepo.findOne(staff_id_qh_admin)) {
            return
        }
        Staff staff = new Staff()
        staff.id = staff_id_qh_admin
        staff.brandAppId = brandAppId
        staff.authorities.add(StaffAuthorityEnum.SA.name())
        staff.userId = user_id_httAdmin
        staffRepo.save(staff)
    }

    private void checkAndAddQhPartner() {

        if (partnerRepo.findOne(partner_id_qh_admin)) {
            return
        }

        Partner partner = new Partner()
        partner.id = partner_id_qh_admin
        partner.brandAppId = brandAppId
        partner.shopName = "钱皇股份"
        partner.realName = "钱皇股份"
        partner.seq = "20170821165838721"
        partner.phone = "0571-86477190"
        partner.adc = "330483"
        partner.lastModifiedDate = new Date()
        partner.userId = user_id_httAdmin
        partner.dateCreated = new Date()

        partner.partnerTypeEnum = PartnerTypeEnum.BRAND_COM
        partner.partnerApplyStatus = PartnerApplyStatusEnum.NORMAL
        partnerRepo.save(partner)
    }

}
