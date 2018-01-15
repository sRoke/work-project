package net.kingsilk.qh.agency.admin.service

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.core.StaffAuthorityEnum
import net.kingsilk.qh.agency.domain.Company
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.repo.CompanyRepo
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

    //static final String user_id_superAdmin = "58de6b27785a82000005a140"
    //static final String user_id_httAdmin = "58e4bd0c785a82000005a141"

    @Deprecated
    static final String user_id_zll = "58e5dfb42a9f2d0000313b1d"


    static final String company_id_htt = "58e59234785a82000005a143"

    /**
     * 郝太太管理员的ID
     */
    static final String staff_id_htt_admin = "58e593fd785a82000005a145"

    @Deprecated
    static final String staff_id_htt_zll = "58e5e1442a9f2d0000313b1f"
    @Deprecated
    static final String member_id_htt_zll = "58e5f41d785a82000005a147"


    @Autowired
    CompanyRepo companyRepo

    @Autowired
    StaffRepo staffRepo

    @Autowired
    PartnerStaffRepo partnerStaffRepo

    @EventListener
    void onApplicationStated(ContextRefreshedEvent event) {

        checkAndAddCompanyHtt()
        checkAndAddHttStaffAdmin()
        checkAndAddHttStaffZll()
        checkAndAddHttMemberZll()

    }

    /**
     * 公司 : 郝太太
     */
    private void checkAndAddCompanyHtt() {


        if (companyRepo.findOne(company_id_htt)) {
            return
        }

        Company company = new Company()
        company.id = company_id_htt
        company.name = "郝太太"

        companyRepo.save(company)
    }

    /**
     * Staff : 郝太太 管理员
     */
    private void checkAndAddHttStaffAdmin() {

        if (staffRepo.findOne(staff_id_htt_admin)) {
            return
        }

        Staff staff = new Staff()
        staff.id = staff_id_htt_admin
//        staff.company = companyRepo.findOne(company_id_htt)
        staff.phone = "18069855776"
        staff.authorities.add(StaffAuthorityEnum.SA.name())
        //staff.userId = user_id_httAdmin
        staffRepo.save(staff)
    }

    /**
     * Staff : 郝太太 般若
     */
    private void checkAndAddHttStaffZll() {

        if (staffRepo.findOne(staff_id_htt_zll)) {
            return
        }

        Staff staff = new Staff()
        staff.id = staff_id_htt_zll
//        staff.company = companyRepo.findOne(company_id_htt)
        staff.phone = "17091602013"
        staff.userId = user_id_zll
        staffRepo.save(staff)
    }

    /**
     * PartnerStaff : 郝太太 般若
     */
    private void checkAndAddHttMemberZll() {

        if (partnerStaffRepo.findOne(staff_id_htt_zll)) {
            return
        }

        PartnerStaff member = new PartnerStaff()
        member.id = member_id_htt_zll
//        partnerStaff.company = companyRepo.findOne(company_id_htt)
        member.userId = user_id_zll
//        member.phone = "17091602013"
//        partnerStaff.tags.add(PartnerTypeEnum.AGENCY)
        partnerStaffRepo.save(member)
    }
}
