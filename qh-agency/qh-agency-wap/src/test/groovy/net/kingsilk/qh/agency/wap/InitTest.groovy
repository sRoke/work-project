package net.kingsilk.qh.agency.wap

import net.kingsilk.qh.agency.core.StaffAuthorityEnum
import net.kingsilk.qh.agency.domain.Company
import net.kingsilk.qh.agency.domain.Staff
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Created by zcw on 3/21/17.
 */
/**
 * 购物车相关测试用例
 */
class InitTest extends BaseTest {

    @Before
    void before() {
        after()
    }

    @After
    void after() {
        //null
    }

    /**
     * 添加company
     */
    @Test
    void initCompany() {
        Company company = new Company()
        company.name = "炮灰测试公司"
        company.bizLicenseNo = "999"
        company.legalPerson = "炮灰"

        company.officeAddr = "东方电子商务园15幢1楼"
        company.contacts = "炮灰"
        company.phone = "17077276627"
        companyRepo.save(company)

        // 添加员工
        Staff staff1 = new Staff()
        staff1.company = company
        staff1.userId = "a_user"
        staff1.realName = "a_user_real_name"
        staff1.nickName = "a_user_nick_name"
        staff1.authorities = [
                StaffAuthorityEnum.XXX.name(),
                StaffAuthorityEnum.YYY.name()
        ] as Set;
        staffRepo.save(staff1)
    }

}
