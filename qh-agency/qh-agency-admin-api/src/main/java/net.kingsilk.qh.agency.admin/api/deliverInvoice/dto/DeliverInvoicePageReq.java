package net.kingsilk.qh.agency.admin.api.deliverInvoice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.common.dto.BasePageReq;

import java.util.Date;

/**
 * Created by lit on 17/7/18.
 */
@ApiModel(value = "发货单分页请求信息")
public class DeliverInvoicePageReq extends BasePageReq{

    @ApiParam(value = "关键字")
    private String keyWords;
    @ApiParam(value = "开始时间")
    private Date startTime;
    @ApiParam(value = "结束时间")
    private Date endTime;
    @ApiParam(value = "状态")
    private String status;

    public String getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
