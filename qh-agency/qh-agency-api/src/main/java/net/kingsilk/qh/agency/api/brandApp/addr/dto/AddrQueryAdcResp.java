package net.kingsilk.qh.agency.api.brandApp.addr.dto;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
public class AddrQueryAdcResp {


    private String parent;
    private String parentNo;
    private List<AdcModel> list;

    public static class AdcModel {

        private String no;
        private String name;

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
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParentNo() {
        return parentNo;
    }

    public void setParentNo(String parentNo) {
        this.parentNo = parentNo;
    }

    public List<AdcModel> getList() {
        return list;
    }

    public void setList(List<AdcModel> list) {
        this.list = list;
    }
}
