package net.kingsilk.qh.agency.api.brandApp.notify.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@ApiModel
public class NotifyQhPayResp {

    @ApiParam(value = "code", required = true)
    @ApiModelProperty(value = "结果")
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
