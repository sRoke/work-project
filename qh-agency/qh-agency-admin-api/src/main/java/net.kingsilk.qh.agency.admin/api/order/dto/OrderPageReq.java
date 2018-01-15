package net.kingsilk.qh.agency.admin.api.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.common.dto.BasePageReq;
import org.springframework.format.annotation.DateTimeFormat;

import javax.ws.rs.QueryParam;
import java.util.Date;

@ApiModel(value = "订单分页请求信息")
public class OrderPageReq extends BasePageReq{


    @ApiParam(value = "关键字")
    @QueryParam(value = "keyWord")
    private String keyWord;
    @ApiParam(value = "开始时间")
    @QueryParam(value = "startDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDate;
    @ApiParam(value = "结束时间")
    @QueryParam(value = "endDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;
    @ApiParam(value = "订单状态")
    @QueryParam(value = "status")
    private String status;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
