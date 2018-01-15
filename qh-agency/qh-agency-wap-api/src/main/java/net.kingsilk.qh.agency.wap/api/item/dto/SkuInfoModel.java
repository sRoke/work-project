package net.kingsilk.qh.agency.wap.api.item.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * sku详情
 */
public class SkuInfoModel extends SkuMiniInfoModel {


    @ApiModelProperty(value = "属性列表")
    private List props;
    @ApiModelProperty(value = "规格列表")
    private List specs;
    @ApiModelProperty(value = "图文描述")
    private String detail;
    private String status;

    public List getProps() {
        return props;
    }

    public void setProps(List props) {
        this.props = props;
    }

    public List getSpecs() {
        return specs;
    }

    public void setSpecs(List specs) {
        this.specs = specs;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
