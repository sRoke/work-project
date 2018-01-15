package net.kingsilk.qh.shop.api.brandApp.shop.deliverInvoice.dto;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

public class DeliverInvoiceShipReq {

    @ApiParam(required = true, value = "物流公司")
    @QueryParam(value = "company")
    private String company;

    @ApiParam(required = true, value = "物流单号")
    @QueryParam(value = "expressNo")
    private String expressNo;

    /**
     * 发货商
     */
    @ApiParam(required = true, value = "发货商")
    @QueryParam(value = "sourceType")
    private String sourceType;


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

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
