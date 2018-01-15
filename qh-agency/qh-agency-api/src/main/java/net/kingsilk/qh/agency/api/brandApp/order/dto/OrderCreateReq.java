package net.kingsilk.qh.agency.api.brandApp.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

@ApiModel
public class OrderCreateReq {

    @ApiParam(name = "orderId", value = "orderId", required = true)
    @QueryParam(value = "orderId")
    private String orderId;
    @ApiParam(name = "from", value = "from", allowableValues = "CART,ITEM,ORDER", required = false)
    @QueryParam(value = "from")
    private String from;
    @QueryParam(value="memo")
    private String memo;

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
}
