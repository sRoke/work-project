package net.kingsilk.qh.agency.wap.api.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
@Deprecated
@ApiModel
public class OrderConfirmReceiveReq  {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ApiParam(value = "id", required = true)
    @ApiModelProperty(value = "订单id")
    private String id;
}
