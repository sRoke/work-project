package net.kingsilk.qh.agency.admin.controller

import net.kingsilk.qh.agency.admin.BaseTest
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.repo.StaffRepo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired


/**
 * Created by yanfq on 17-3-27.
 */
class StaffControllerTest extends BaseTest {
    @Autowired
    StaffRepo staffRepo

    Staff staff

    @Before
    void before() {
        after()
        staff = new Staff()
        staff.userId = "test_user"
        staff.realName = "test_user_real_name"
        staff.nickName = "test_user_nick_name"
        staffRepo.save(staff)
    }

    @After
    void after() {
        staffRepo.deleteAll()
    }

    @Test
    void info() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/staff/info?id=" + staff.id
        requestGet(url)
    }

    @Test
    void save() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/staff/save"
        def num = Math.round(Math.random() * 100)
        def jsonObj = [
                userId  : "test_user" + num,
                realName: "test_user_realName" + num,
                nickName: "test_user_nickName" + num,
                idNumber: "test_user_idNumber" + num

        ]
        requestPost(url, jsonObj)
    }

    @Test
    void page() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/staff/page?curPage=1&pageSize=2"
        requestGet(url)
    }

    @Test
    void enable() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/staff/enable?id=" + staff.id + "&disabled=" + true
        requestPost(url, null)
    }
}
