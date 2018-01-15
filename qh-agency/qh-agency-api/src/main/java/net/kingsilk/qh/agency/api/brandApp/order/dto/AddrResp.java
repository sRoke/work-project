package net.kingsilk.qh.agency.api.brandApp.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import java.util.List;

@ApiModel(value = "地址返回")
public class AddrResp {

    @ApiParam(value = "父地区编码")
    private String parent;
    @ApiParam(value = "地区级别")
    private String level;
    @ApiParam(value = "地区列表")
    private List<AdcModel> list;

    public static class AdcModel {

        @ApiParam(value = "地区编码")
        private String no;
        @ApiParam(value = "地区中文")
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<AdcModel> getList() {
        return list;
    }

    public void setList(List<AdcModel> list) {
        this.list = list;
    }
}
