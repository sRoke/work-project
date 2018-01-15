package net.kingsilk.qh.shop.api.controller.refund.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@ApiModel
public class RefundPaymentReq {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @ApiModelProperty(value = "退款id")
    @ApiParam(value = "id", required = true)
    private String id;
    @ApiModelProperty(value = "需退款的金额，单位是分")
    @ApiParam(value = "price", required = true)
    private Integer price;
}
