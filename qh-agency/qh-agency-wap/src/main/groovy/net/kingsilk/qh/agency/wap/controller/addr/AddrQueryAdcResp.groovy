package net.kingsilk.qh.agency.wap.controller.addr

import io.swagger.annotations.ApiModel
import net.kingsilk.qh.agency.domain.Adc

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
class AddrQueryAdcResp {

    String parent;

    List<AdcModel> list;

    static class AdcModel {
        String no;

        String name;
    }

    void convert(String parent, List<Adc> adcList) {
        this.parent = parent
        list = []
        adcList.each { Adc adc ->
            AdcModel model = new AdcModel()
            model.no = adc.no
            model.name = adc.name
            list.add(model)
        }
    }
}
