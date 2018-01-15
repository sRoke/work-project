package net.kingsilk.qh.agency.admin

import net.kingsilk.qh.agency.core.SmsTypeEnum
import net.kingsilk.qh.agency.domain.Sms
import net.kingsilk.qh.agency.domain.SmsTemplate
import net.kingsilk.qh.agency.domain.YunFile
import net.kingsilk.qh.agency.repo.AdcRepo
import net.kingsilk.qh.agency.repo.SmsRepo
import net.kingsilk.qh.agency.repo.SmsTemplateRepo
import net.kingsilk.qh.agency.service.AliSmsService
import net.kingsilk.qh.agency.service.CommonService
import net.kingsilk.qh.agency.service.YpSmsService
import net.kingsilk.qh.agency.util.Page
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate

/**
 * Created by zcw on 3/15/17.
 */
@RunWith(SpringRunner)
@SpringBootTest
class TestZcw {

    @Autowired
    RestTemplate restTemplate

    @Autowired
    SmsTemplateRepo smsTemplateRepository

    @Autowired
    AliSmsService aLiSmsService

    @Autowired
    YpSmsService ypSmsService

    @Autowired
    CommonService commonService

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    SmsRepo smsRepository

    @Autowired
    AdcRepo adcRepository

    @Before
    void init() {
        println("-----------------init")
    }

    //@Test
    void test() {
        println("-----------------start")
        String str = restTemplate.getForObject("http://baidu.com/", String)
        println(str)
    }

    //@Test
    void testAliSms() {
        def res = aLiSmsService.sendSms(null, "18270839486", "SMS_19300004", aLiSmsService.convertMap2Json([content: "这是测试信息"]), null, false)
        println(res.toString())
    }

    //@Test
    void testYpSms() {
        println("--------start")
        ypSmsService.sendSms(null, "18270839486", "【小皇叔】您的验证码是123456。如非本人操作，请忽略本短信。该验证码有效期30分钟。")
    }

    //@Test
    void testPageService() {
        Query query = new Query()
        Page<YunFile> page = commonService.domainPages(YunFile, query, 2, 5)
        println("curPage:${page.curPage},pageSize:${page.pageSize},totalCount:${page.totalCount}")
        page.list.each { YunFile it ->
            println(it.id)
        }
    }

    //伪造短信数据
    //@Test
    void addSmsData() {
        int num = 20;
        while (num > 0) {
            Sms sms = new Sms()
            sms.content = "aaa"
            sms.dateCreated = new Date()
            sms.phone = "18270839486"
            sms.sendTime = new Date()
            sms.ip = "127.0.0.1"
            sms.type = SmsTypeEnum.CAPTCHA
            mongoTemplate.save(sms)
            num--
        }
    }

    //////测试repo
    //@Test
    void testMongoRepo() {
        List<Sms> list = smsRepository.findAll();
        int i = 1;
        list.each {
            println("${it.id},${i++}")
        }
    }

    @Test
    void testMongoRepo1() {
        SmsTemplate res = smsTemplateRepository.findOneByKey("key")
        if (!res) {
            println("未找到")
            return
        }
        println("${res.key},${res.title}")
    }

//    //@Test
//    void initAdcDb() {
//        List<Adc> adcList1 = adcRepository.findAllByParent(null)
//        adcList1.each { Adc adc1 ->
//            adc1.no = adc1.id
//            adc1.id = null
//            mongoTemplate.save(adc1)
//            List<Adc> adcList2 = adcRepository.findAllByParent(adc1.no)
//            adcList2.each { Adc adc2 ->
//                adc2.no = adc2.id
//                adc2.id = null
//                mongoTemplate.save(adc2)
//                List<Adc> adcList3 = adcRepository.findAllByParent(adc2.no)
//                adcList3.each { Adc adc3 ->
//                    adc3.no = adc3.id
//                    adc3.id = null
//                    mongoTemplate.save(adc3)
//                }
//            }
//        }
//    }

}
