package net.kingsilk.qh.raffle.server.resource.raffleApp.common.convert;

import net.kingsilk.qh.raffle.domain.Adc;
import net.kingsilk.qh.shop.api.common.dto.AddrQueryAdcResp;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressConvert {

    public AddrQueryAdcResp queryAdcRespConvert(Adc parent, List<Adc> adcList) {
        AddrQueryAdcResp resp = new AddrQueryAdcResp();
        if (parent != null) {
            resp.setParent(parent.getName());
            resp.setParentNo(parent.getNo());
        }
        List<AddrQueryAdcResp.AdcModel> list = new ArrayList<>();
        resp.setList(list);
        adcList.forEach(
                adc -> {
                    AddrQueryAdcResp.AdcModel model = new AddrQueryAdcResp.AdcModel();
                    model.setNo(adc.getNo());
                    model.setName(adc.getName());
                    resp.getList().add(model);
                });
        return resp;
    }
}
