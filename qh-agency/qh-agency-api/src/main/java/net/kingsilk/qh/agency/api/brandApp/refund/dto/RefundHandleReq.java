package net.kingsilk.qh.agency.api.brandApp.refund.dto;

import io.swagger.annotations.ApiModel;
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum;

@ApiModel(value = "售后订单处理意见信息")
public class RefundHandleReq {

    private Boolean agreeRefund;
    private LogisticsCompanyEnum company;
    private String expressNo;

    public Boolean getAgreeRefund() {
        return agreeRefund;
    }

    public void setAgreeRefund(Boolean agreeRefund) {
        this.agreeRefund = agreeRefund;
    }

    public LogisticsCompanyEnum getCompany() {
        return company;
    }

    public void setCompany(LogisticsCompanyEnum company) {
        this.company = company;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }
}
