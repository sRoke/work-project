package net.kingsilk.qh.agency.admin.controller.testZcw

import io.swagger.annotations.Api
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.repo.AdcRepo
import net.kingsilk.qh.agency.repo.SmsRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * Created by zcw on 17-3-14.
 */
@RestController()
@RequestMapping("/api/testZcw")
@Api(
        tags = "testZcw",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        description = "炮灰的测试"
)
class TestZcwController {

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    SmsRepo smsRepository

    @Autowired
    AdcRepo adcRepository

    @RequestMapping(path = "/test",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
    UniResp<String> test() {
        return new UniResp<String>(status: 200, data: "test authority success")
    }

//    @RequestMapping(path = "/initAdcDb",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseBody
//    String initAdcDb() {
//        List<Adc> adcList1 = adcRepository.findAllByParent1(null)
//        adcList1.each { Adc adc1 ->
//            mongoTemplate.remove(adc1)
//            adc1.no = adc1.id
//            adc1.id = null
//            println(adc1.name)
//            mongoTemplate.save(adc1)
//            List<Adc> adcList2 = adcRepository.findAllByParent1(adc1.no)
//            adcList2.each { Adc adc2 ->
//                mongoTemplate.remove(adc2)
//                adc2.no = adc2.id
//                adc2.id = null
//                adc2.parent = adcRepository.findOneByNo(adc2.parent1)
//                println("--------" + adc2.name)
//                mongoTemplate.save(adc2)
//                List<Adc> adcList3 = adcRepository.findAllByParent1(adc2.no)
//                adcList3.each { Adc adc3 ->
//                    mongoTemplate.remove(adc3)
//                    adc3.no = adc3.id
//                    adc3.id = null
//                    adc3.parent = adcRepository.findOneByNo(adc3.parent1)
//                    println("--------========" + adc3.name)
//                    mongoTemplate.save(adc3)
//                }
//            }
//        }
//        return "SUCCESS"
//    }
}
