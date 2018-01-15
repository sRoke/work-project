package net.kingsilk.qh.raffle.api.raffleApp.raffle.record.dto;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

public class DeliverInvoiceShipReq {

    @ApiParam(required = true, value = "物流公司")
    @QueryParam(value = "company")
    private String company;

    @ApiParam(required = true, value = "物流单号")
    @QueryParam(value = "expressNo")
    private String expressNo;

    @ApiParam(required = true, value = "备注")
    @QueryParam(value = "memo")
    private String memo;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
