package net.kingsilk.qh.agency.admin.api.refund.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel(value = "订单分页请求信息")
public class RefundPageReq {


    @ApiParam(value = "当前页数", defaultValue = "1")
    private Integer curPage = 1;
    @ApiParam(value = "每页数量", defaultValue = "15")
    private Integer pageSize = 15;
    @ApiParam(value = "售后订单类型")
    private String type;
    @ApiParam(value = "售后订单状态")
    private String status;
    @ApiParam(value = "售后理由")
    private String reason;


    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
