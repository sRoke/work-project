package net.kingsilk.qh.agency.admin.api.partner.dto;

import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.common.dto.BasePageReq;

import javax.ws.rs.QueryParam;
import java.util.Date;

/**
 *
 */
public class PartnerPageReq extends BasePageReq{

    @ApiParam(value = "申请状态")
    @QueryParam(value = "status")
    private String status;

    @ApiParam(value = "申请类型")
    @QueryParam(value = "applyType")
    private  String applyType;

//    @ApiParam(value = "开始时间")
//    @QueryParam(value = "startTime")
//    private Date startTime;
//
//    @ApiParam(value = "结束时间")
//    @QueryParam(value = "endTime")
//    private Date endTime;

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

//    public Date getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(Date startTime) {
//        this.startTime = startTime;
//    }
//
//    public Date getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(Date endTime) {
//        this.endTime = endTime;
//    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
