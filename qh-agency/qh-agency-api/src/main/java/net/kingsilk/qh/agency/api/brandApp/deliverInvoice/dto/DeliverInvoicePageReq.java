package net.kingsilk.qh.agency.api.brandApp.deliverInvoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageReq;

import javax.ws.rs.QueryParam;

/**
 * Created by lit on 17/7/18.
 */
@ApiModel(value = "发货单分页请求信息")
public class DeliverInvoicePageReq extends UniPageReq {

    @ApiParam(value = "关键字")
    @QueryParam(value = "keyWords")
    private String keyWords;
    @ApiParam(value = "开始时间")
    @QueryParam(value = "startDate")
    private String startDate;
    @ApiParam(value = "结束时间")
    @QueryParam(value = "endDate")
    private String endDate;
    @ApiParam(value = "状态")
    @QueryParam(value = "status")
    private String status;

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
