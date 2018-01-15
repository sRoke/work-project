package net.kingsilk.qh.agency.wap

import net.kingsilk.qh.agency.QhAgencyProperties
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.QAddress
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.repo.AddressRepo
import net.kingsilk.qh.agency.repo.CompanyRepo
import net.kingsilk.qh.agency.repo.PartnerStaffRepo
import net.kingsilk.qh.agency.repo.StaffRepo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpEntity
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

import static com.querydsl.core.types.dsl.Expressions.allOf

@RunWith(SpringRunner)
@ActiveProfiles(profiles = ["base", "dev"])
@SpringBootTest
class TestZcw {

    @Autowired
    OAuth2RestTemplate oClientRestTemplate

    @Autowired
    QhAgencyProperties props

    @Autowired
    AddressRepo addressRepo

    @Before
    void init() {
        println("-----------------init")
    }

    @Test
    void testOauthClient() {
        def map = [
                totel_fee: 1,
                orderId  : 123456,
                //openId   : ""               //TODO 获取用户openId
        ]
        HttpEntity<Map> reqEntity = new HttpEntity<Map>(map);
        Map payMap = oClientRestTemplate.postForObject(props.qhPay.wap.api.pay_create, reqEntity, Map)
        println("--------------------支付系统返回值\n${payMap}")
    }

    @Test
    void testDsl() {
        String curUserId = "a_admin"
        List qList = []
        qList.add(QAddress.address.userId.eq(curUserId))
        qList.add(QAddress.address.deleted.in([false, null]))

        Page page = addressRepo.findAll(
                allOf(
                        QAddress.address.userId.eq(curUserId),
                        QAddress.address.deleted.in([false, null]),
                        null
                ),
                new PageRequest(1, 3, new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        )
        println(page.totalPages)
        println(page.totalElements)
        println(page)
    }

    @Autowired
    CompanyRepo companyRepo

    @Autowired
    PartnerStaffRepo partnerStaffRepo

    @Autowired
    StaffRepo staffRepo

    @Test
    void initCompany() {
//        def company = new Company()
//        company.name = "炮灰测试公司"
//        company.bizLicenseNo = "ISO10086"
//        company.contacts = "炮灰"
//        company.phone = "18270839486"
//        company.legalPerson = "炮灰"
//        company.officeAddr = "九堡电子商务园"
//        companyRepo.save(company)
        def company = companyRepo.findOne("58eae75b8158191de95cd06e")

        def member = new PartnerStaff()
        member.company = company
        member.phone = "18270839486"
        member.contacts = "paohui"
        member.avatar = "https://baidu.com/"
        member.realName = "炮灰"
        partnerStaffRepo.save(member)

        def staff = new Staff()
        staff.phone = "18270839486"
        staff.company = company
        staff.avatar = "https://baidu.com/"
        staff.idNumber = "360123200012120001"
        staff.nickName = "炮灰"
        staff.realName = "zcw"
        staffRepo.save(staff)
    }

//    AbstractMongodbQuery query = addressRepo.query()
//    AbstractMongodbQuery q = query.where(
//            QAddress.address.delete.in([false, null])
//    )

}
