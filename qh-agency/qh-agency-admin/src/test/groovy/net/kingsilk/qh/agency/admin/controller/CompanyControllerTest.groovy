package net.kingsilk.qh.agency.admin.controller

import net.kingsilk.qh.agency.admin.BaseTest
import net.kingsilk.qh.agency.domain.Company
import net.kingsilk.qh.agency.repo.CompanyRepo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by yanfq on 17-3-27.
 */
class CompanyControllerTest extends BaseTest {

    @Autowired
    CompanyRepo companyRepo

    Company company

    @Before
    void before() {
        after()
        company = new Company()
        company.name = "test_company"
        company.bizLicenseNo = "xxx"
        company.legalPerson = "xxx"
        company.officeAddr = "xxx"
        company.contacts = "xxx"
        company.phone = "xxx"
        companyRepo.save(company)
    }

    @After
    void after() {
        companyRepo.deleteAll()
    }

    @Test
    void info() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/company/info?id=" + company.id;
        requestGet(url)
    }

    @Test
    void save() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/company/save"
        def num = Math.round(Math.random() * 100)
        def jsonObj = [
                name        : "test_company" + num,
                bizLicenseNo: "ccc" + num,
                legalPerson : "xxx" + num,
                officeAddr  : "xxx" + num,
                contacts    : "xxx" + num,
                phone       : "xxx" + num
        ]
        requestPost(url, jsonObj)
    }

    @Test
    void page() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/company/page?curPage=1&pageSize=2"
        requestGet(url)
    }

}
