package net.kingsilk.qh.shop.api.controller.refund.dto;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "售后订单处理意见信息")
public class RefundHandleReq {

    private Boolean agreeRefund;
    private String expressNo;

    public Boolean getAgreeRefund() {
        return agreeRefund;
    }

    public void setAgreeRefund(Boolean agreeRefund) {
        this.agreeRefund = agreeRefund;
    }


    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }
}
