package net.kingsilk.qh.agency.admin.resource.test

import com.mongodb.util.JSON
import net.kingsilk.qh.agency.domain.Adc
import net.kingsilk.qh.agency.repo.AdcRepo
import net.kingsilk.qh.agency.wap.api.UniResp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

/**
 * Created by lit on 17/7/26.
 */
@Path("/testLit")
@Component
class TestLitResource {

    @Autowired
    AdcRepo adcRepo

    @Path("init")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Cacheable(value = "addrListCache")
    public def test() {
//        StringBuffer sbf = new StringBuffer()
//        sbf.append("{\n" +
//                "            \"data\": [")
//        List<Adc> adc = adcRepo.findAll(
//                QAdc.adc.parent.isNull()
//        ).asList()
//        adc.each {
//            sbf.append("{\n" +
//                    "                    label: '"+it.name+"',\n" +
//                    "                    value: "+it.no+",\n" +
//                    "                    children: [")
//            List<Adc> adc_two = adcRepo.findAll(
//                    QAdc.adc.parent.eq(it)
//            ).asList()
//            adc_two.each {
//                sbf.append("{\n" +
//                        "                            label: '"+it.name+"',\n" +
//                        "                            value: "+it.no+",\n" +
//                        "                            children: [")
//                List<Adc> adc_three = adcRepo.findAll(
//                        QAdc.adc.parent.eq(it)
//                ).asList()
//                adc_three.each {
//                    sbf.append("{\n" +
//                            "                                    label: '"+it.name+"',\n" +
//                            "                                    value: "+it.no+"\n" +
//                            "                                },")
//                }
//                sbf.append("]\n" +
//                        "                        }, ")
//            }
//            sbf.append("]\n" +
//                    "                }, ")
//        }
//        return sbf.toString()
        System.out.println("real query account.=======================");
        def provincialMap = []
        List<Adc> adc = adcRepo.findAll().asList()
        List<Adc> provincials = adc.findAll { it.parent == null }
        provincials.each { Adc provincial ->
            def cityMap = []
            List<Adc> citys = adc.findAll { it.parent?.id == provincial.id }
            if (!citys) {

                def countyMap = []
                countyMap.add([
                        "label": provincial.name,
                        "value": provincial.no
                ])
                cityMap.add([
                        "label"   : provincial.name,
                        "value"   : provincial.no,
                        "children": countyMap
                ])
            }
            citys.each { Adc city ->
                def countyMap = []
                List<Adc> countys = adc.findAll { it.parent?.id == city.id }
                if (!countys) {
                    countyMap.add([
                            "label": city.name,
                            "value": city.no
                    ])
                }
                countys.each {
                    countyMap.add([
                            "label": it.name,
                            "value": it.no
                    ])
                }
                cityMap.add([
                        "label"   : city.name,
                        "value"   : city.no,
                        "children": countyMap
                ])
            }
            provincialMap.add([
                    "label"   : provincial.name,
                    "value"   : provincial.no,
                    "children": cityMap
            ])
        }
        return new UniResp<ArrayList>(status: 200, data: provincialMap)
    }
}
