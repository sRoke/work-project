package net.kingsilk.qh.agency.admin.controller

import net.kingsilk.qh.agency.admin.BaseTest
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.repo.PartnerStaffRepo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired


/**
 * Created by yanfq on 17-3-27.
 */
class MemberControllerTest extends BaseTest {

    @Autowired
    PartnerStaffRepo partnerStaffRepo

    PartnerStaff member

    @Before
    void before() {
        after()
        member = new PartnerStaff()
        member.userId = "test_user"
        member.realName = "test_user_real_name"
        member.phone = "test_user_phone"
        member.idNo = "test_user_idNo"
        partnerStaffRepo.save(member)
    }

    @After
    void after() {
        partnerStaffRepo.deleteAll()
    }

    @Test
    void info() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/partnerStaff/info?id=" + member.id;
        requestGet(url)
    }

    @Test
    void save() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/partnerStaff/save"
        def num = Math.round(Math.random() * 100)
        def jsonObj = [
                userId  : "test_member" + num,
                realName: "test_member_realName" + num,
                phone   : "test_member_phone" + num,
                idNo    : "test_member_idNo" + num
        ]
        requestPost(url, jsonObj)
    }

    @Test
    void page() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/partnerStaff/page?curPage=1&pageSize=2"
        requestGet(url)
    }

    @Test
    void enable() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/partnerStaff/enable?id=" + member.id + "&disabled=" + true
        requestPost(url, null)
    }
}
