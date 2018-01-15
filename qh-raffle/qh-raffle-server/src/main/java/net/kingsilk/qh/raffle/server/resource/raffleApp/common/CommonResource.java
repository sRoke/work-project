package net.kingsilk.qh.raffle.server.resource.raffleApp.common;

import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.common.CommonApi;
import net.kingsilk.qh.raffle.service.AddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static net.kingsilk.qh.raffle.service.AddrService.getAddr;


@Component("commonAddrResource")
public class CommonResource implements CommonApi {

    @Autowired
    private AddrService addrService;

    @Override
    public UniResp<List<Map<String, Object>>> getAdcList() {
        List<Map<String, Object>> resp;
        if (getAddr != null && getAddr.size() > 0) {
            resp = getAddr;
        } else {
            resp = addrService.getAddrList();
            getAddr = resp;
        }
        UniResp<List<Map<String, Object>>> uniResp = new UniResp<>();
        uniResp.setData(resp);
        uniResp.setStatus(200);
        return uniResp;
    }

}
