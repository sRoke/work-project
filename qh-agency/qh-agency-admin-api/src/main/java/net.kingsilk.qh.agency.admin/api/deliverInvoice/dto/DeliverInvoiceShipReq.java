package net.kingsilk.qh.agency.admin.api.deliverInvoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum;

/**
 *
 */
@ApiModel(value = "订单修改提供信息")
public class DeliverInvoiceShipReq {
    @ApiParam(required = true, value = "id")
    private String id;

    @ApiParam(required = true, value = "物流公司")
    private String company;

    @ApiParam(required = true, value = "物流单号")
    private String expressNo;

    /**
     * 发货商
     */
    @ApiParam(required = true, value = "发货商")
    private String sourceType;

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
