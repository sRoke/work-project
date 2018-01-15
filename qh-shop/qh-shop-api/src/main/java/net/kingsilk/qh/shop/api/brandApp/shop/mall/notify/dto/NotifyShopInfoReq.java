package net.kingsilk.qh.shop.api.brandApp.shop.mall.notify.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

public class NotifyShopInfoReq {

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
