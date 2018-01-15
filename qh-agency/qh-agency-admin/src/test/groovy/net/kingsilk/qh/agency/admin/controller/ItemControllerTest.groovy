package net.kingsilk.qh.agency.admin.controller

import net.kingsilk.qh.agency.admin.BaseTest
import net.kingsilk.qh.agency.core.ItemPropTypeEnum
import net.kingsilk.qh.agency.core.ItemStatusEnum
import net.kingsilk.qh.agency.domain.Company
import net.kingsilk.qh.agency.domain.Item
import net.kingsilk.qh.agency.domain.ItemProp
import net.kingsilk.qh.agency.repo.ItemPropRepo
import net.kingsilk.qh.agency.repo.ItemRepo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by yanfq on 17-3-27.
 */
class ItemControllerTest extends BaseTest {
    @Autowired
    ItemRepo itemRepo
    @Autowired
    ItemPropRepo itemPropRepo

    Item item

    Company company

    ItemProp itemProp

    @Before
    void before() {
        after()
        company = new Company()
        def num = Math.round(Math.random() * 100)
        company.name = "company" + num
        company.bizLicenseNo = "999"
        company.legalPerson = "qqq"

        company.officeAddr = "eqweasd"
        company.contacts = "qwqq"
        company.phone = "111111111111"
        companyRepo.save(company)

        item = new Item()
        item.company = company
        item.code = "xxx"
        item.status = ItemStatusEnum.NORMAL
        def specs = new LinkedHashSet<>()

        Item.SpecDef spec = new Item.SpecDef()

        itemProp = new ItemProp()
        itemProp.company = company
        itemProp.code = "xxx"
        itemProp.memName = "xxx"
        itemProp.memo = "xxx"
        itemProp.name = "xxx"
        itemProp.type = ItemPropTypeEnum.TEXT
        itemProp.unit = "xxx"
        mongoTemplate.save(itemProp)
        spec.itemProp = itemProp
        mongoTemplate.save(spec)
        specs.add(spec)
        item.specs = specs

        Item.UsedItemProp usedItem = new Item.UsedItemProp()
        usedItem.itemProp = itemProp
        usedItem.itemPropValue = null
        mongoTemplate.save(usedItem)
        item.code = "xxx"

        mongoTemplate.save(item)
    }

    @After
    void after() {
        //itemRepo.deleteAll()
        //companyRepo.deleteAll()
    }

    @Test
    void info() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/item/info?id=" + item.id
        requestGet(url)
    }

    @Test
    void save() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/item/save"
        def jsonObj = [
                code  : 'xxx',
                detail: 'xxx',
                title : 'xxx',
                desp  : 'xxx',
                status: ItemStatusEnum.NORMAL,
                props : [
                        code   : 'vvv',
                        memName: 'afd',
                        memo   : 'gads',
                        name   : 'uhub',
                        type   : ItemPropTypeEnum.TEXT,
                        unit   : 'asdf'
                ],
                specs : [
                        code   : 'vvv',
                        memName: 'afd',
                        memo   : 'gads',
                        name   : 'uhub',
                        type   : ItemPropTypeEnum.TEXT,
                        unit   : 'asdf'
                ],
                imgs  : ['fasdf', 'aaaa']

        ]
        requestPost(url, jsonObj)
    }

    @Test
    void page() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/item/page?curPage=1&pageSize=2"
        requestGet(url)
    }

    @Test
    void enable() {
        String url = ut.agency.admin.url + "/net.kingsilk.qh.agency.admin/api/item/enable?id=" + staff.id + "&disabled=" + true
        requestPost(url, null)
    }
}
