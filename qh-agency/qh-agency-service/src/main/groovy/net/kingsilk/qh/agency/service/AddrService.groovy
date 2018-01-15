package net.kingsilk.qh.agency.service

import net.kingsilk.qh.agency.domain.Adc
import net.kingsilk.qh.agency.domain.Address
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.repo.AdcRepo
import net.kingsilk.qh.agency.repo.AddressRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by lit on 17/7/26.
 */
@Service
class AddrService {

    @Autowired
    AdcRepo adcRepo

    @Autowired
    AddressRepo addressRepo

    @Autowired
    PartnerStaffService memberService


    public static ArrayList getAddr
//    @Cacheable(value = "addrListCache")
    ArrayList getAddrList() {
//        System.out.println("CacheConfiguration.ehCacheCacheManager()***********************************");
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
        return provincialMap
    }

    def updateDefaultAddr(Address address) {
        if (!address) {
            return
        }
        PartnerStaff partnerStaff = memberService.getCurPartnerStaff()
        List<Address> list = addressRepo.findAllByPartner(partnerStaff.partner)
        list.each { Address it ->
            if (it.defaultAddr && it.id != address.id) {
                it.defaultAddr = false
                addressRepo.save(it)
            }
        }
        address.defaultAddr = true
        addressRepo.save(address)
    }

    def getAdcInfo(String no) {
        Adc adc = adcRepo.findOneByNo(no)
        if (adc) {
            def area = ((String) (adc.name));
            final Object parent = adc.parent;
            def city = parent == null ? null : parent.name
            final Object parent2 = adc.parent;
            final Object parent1 = parent2 == null ? null : parent2.parent
            def province = parent1 == null ? null : parent1.name
            String resp
            if (city) {
                if (province) {
                    resp = province + " " + city + " " + area
                } else {
                    resp = city + " " + area
                }
            } else {
                resp = area
            }
            return resp
        } else {
            return ""
        }
    }
}
