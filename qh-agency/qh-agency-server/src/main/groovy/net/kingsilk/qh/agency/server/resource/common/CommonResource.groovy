package net.kingsilk.qh.agency.server.resource.common

import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrQueryAdcResp
import net.kingsilk.qh.agency.api.common.CommonApi
import net.kingsilk.qh.agency.domain.Adc
import net.kingsilk.qh.agency.repo.AdcRepo
import net.kingsilk.qh.agency.server.resource.brandApp.addr.AddressConvert
import net.kingsilk.qh.agency.service.AddrService
import net.kingsilk.qh.agency.service.SecService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.ws.rs.Path

/**
 *
 */
@Path("/common")
@Component
public class CommonResource implements CommonApi {

    @Autowired
    AddressConvert addressConvert

    @Autowired
    AdcRepo adcRepo


    @Autowired
    AddrService addrService

    @Autowired
    SecService secService

    @Override
    public UniResp<AddrQueryAdcResp> queryAdc(String adcNo) {
        Adc adc = null
        if (adcNo) {
            adc = adcRepo.findOneByNo(adcNo)
        }
        List<Adc> adcList = adcRepo.findAllByParent(adc)
        AddrQueryAdcResp resp = addressConvert.queryAdcRespConvert(adc, adcList)
        return new UniResp<AddrQueryAdcResp>(status: 200, data: resp)
    }

    @Override
    UniResp<ArrayList> getAdcList() {
        ArrayList resp
        if (addrService.getAddr && addrService.getAddr.size() > 0) {
            resp = addrService.getAddr
        } else {
            resp = addrService.getAddrList()
            addrService.getAddr = resp
        }
        return new UniResp<ArrayList>(status: 200, data: resp)
    }

//    @Override
//    UniResp<String> getUserInfo() {
//        String userId = secService.curUserId()
//        return null
//    }
}
