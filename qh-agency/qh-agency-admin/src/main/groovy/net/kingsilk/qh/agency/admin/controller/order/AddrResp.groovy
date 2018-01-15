package net.kingsilk.qh.agency.admin.controller.order

import io.swagger.annotations.ApiModel
import net.kingsilk.qh.agency.domain.Adc


@ApiModel
class AddrResp {

    String parent;

    String level;

    List<AdcModel> list;

    static class AdcModel {
        String no;

        String name;
    }

    void convert(String parent,level , List<Adc> adcList) {
        this.parent = parent
        this.level = level
        list = []
        adcList.each { Adc adc ->
            AdcModel model = new AdcModel()
            model.no = adc.no
            model.name = adc.name
            list.add(model)
        }
    }
}
