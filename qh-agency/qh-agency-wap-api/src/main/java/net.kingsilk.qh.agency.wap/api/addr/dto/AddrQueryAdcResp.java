package net.kingsilk.qh.agency.wap.api.addr.dto;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
public class AddrQueryAdcResp {
//    public void convert(String parent, List<Adc> adcList) {
//        this.parent = parent;
//        list = new ArrayList<AdcModel>();
//        adcList.invokeMethod("each", new Object[]{new Closure(this, this) {
//            public Boolean doCall(Adc adc) {
//                AdcModel model = new AdcModel();
//                model.no = ((String) (adc.no));
//                model.name = ((String) (adc.name));
//                return list.add(model);
//            }
//
//        }});
//    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<AdcModel> getList() {
        return list;
    }

    public void setList(List<AdcModel> list) {
        this.list = list;
    }

    private String parent;
    private List<AdcModel> list;

    public static class AdcModel {
        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String no;
        private String name;
    }
}
