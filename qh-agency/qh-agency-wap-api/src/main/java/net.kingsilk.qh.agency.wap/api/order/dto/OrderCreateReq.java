package net.kingsilk.qh.agency.wap.api.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@ApiModel
public class OrderCreateReq {
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @ApiParam(name = "orderId", value = "orderId", required = true)
    private String orderId;
    @ApiParam(name = "from", value = "from", allowableValues = "CART,ITEM,ORDER", required = false)
    private String from;

    private String memo;


}
