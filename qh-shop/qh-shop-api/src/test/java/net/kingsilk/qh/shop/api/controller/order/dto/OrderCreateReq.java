package net.kingsilk.qh.shop.api.controller.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

@ApiModel
public class OrderCreateReq {

    //    @ApiParam(name = "orderId", value = "orderId", required = true)
//    @QueryParam(value = "orderId")
//    private String orderId;
    @ApiParam(name = "from", value = "from", allowableValues = "CART,ITEM,ORDER", required = false)
    @QueryParam(value = "from")
    private String from;
    @QueryParam(value = "memo")
    private String memo;

    @QueryParam(value = "cellphone")
    private String cellphone;

//    public String getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(String orderId) {
//        this.orderId = orderId;
//    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
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
