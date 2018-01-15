package net.kingsilk.qh.agency.api.brandApp.partner;

import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.api.UniPageReq;

import javax.ws.rs.QueryParam;

/**
 *
 */
public class PartnerPageReq extends UniPageReq {

    @ApiParam(value = "申请状态")
    @QueryParam(value = "status")
    private String status;

    @ApiParam(value = "申请类型")
    @QueryParam(value = "applyType")
    private String applyType;
    @ApiParam(value = "来源")
    @QueryParam(value = "source")
    private String source;
    @ApiParam(value = "开始时间")
    @QueryParam(value = "startDate")
    private String startDate;

    @ApiParam(value = "结束时间")
    @QueryParam(value = "endDate")
    private String endDate;

    @ApiParam(value = "关键字")
    @QueryParam(value = "keyWord")
    private String keyWord;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
