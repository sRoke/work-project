package net.kingsilk.qh.agency.wap.api.addr.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@ApiModel
public class AddrDeleteReq  {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ApiParam(value = "id", required = true)
    @ApiModelProperty(value = "id")
    private String id;
}
