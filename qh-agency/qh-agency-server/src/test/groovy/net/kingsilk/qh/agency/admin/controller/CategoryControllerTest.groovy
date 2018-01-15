package net.kingsilk.qh.agency.admin.controller

import net.kingsilk.qh.agency.admin.BaseTest
import net.kingsilk.qh.agency.domain.Category
import net.kingsilk.qh.agency.repo.CategoryRepo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired


/**
 * Created by yanfq on 17-3-27.
 */
class CategoryControllerTest extends BaseTest {

    @Autowired
    CategoryRepo categoryRepo

    Category category

    @Before
    void before() {
        after()
        category = new Category()
        category.name = "test_category"
        category.img = "test_category_img"
        category.desp = "test_category_desp"
        category.icon = "test_category_ico"
        categoryRepo.save(category)
    }

    @After
    void after() {
        categoryRepo.deleteAll()
    }


    @Test
    void save() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/category/save"
        def num = Math.round(Math.random() * 100)
        def jsonObj = [
                name: "test_category" + num,
                img : "test_category_realName" + num,
                desp: "test_category_phone" + num,
                icon: "test_category_idNo" + num
        ]
        requestPost(url, jsonObj)
    }

    @Test
    void page() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/category/page?curPage=1&pageSize=2"
        requestPost(url, null)
    }

    @Test
    void enable() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/category/enable"
        def jsonObj = [
                id      : category.id,
                disabled: true
        ]
        requestPost(url, jsonObj)
    }
}
