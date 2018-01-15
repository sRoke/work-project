package net.kingsilk.qh.agency.api.brandApp.refund.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageReq;

import javax.ws.rs.QueryParam;

@ApiModel(value = "订单分页请求信息")
public class RefundPageReq extends UniPageReq {

    @ApiParam(value = "售后查询来源")
    @QueryParam(value = "source")
    private String source;
    @ApiParam(value = "售后订单类型")
    @QueryParam(value = "type")
    private String type;
    @ApiParam(value = "售后订单状态")
    @QueryParam(value = "status")
    private String status;
    @ApiParam(value = "开始时间")
    @QueryParam(value = "startDate")
    private String startDate;
    @ApiParam(value = "结束时间")
    @QueryParam(value = "endDate")
    private String endDate;
    @ApiParam(value = "关键字", required = false)
    @QueryParam(value = "keyWord")
    private String keyWord;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
