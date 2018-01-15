package net.kingsilk.qh.platform.api.staffGroup.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.platform.api.UniPageReq;

import java.util.Date;

/**
 * Created by lit on 17-3-20.
 */
@ApiModel(value = "StaffGroupInfoResp")
public class StaffGroupPageReq extends UniPageReq {

    @ApiParam(value = "帐号")
    private String keyWord;
    @ApiParam(value = "开始时间")
    private Date startDate;
    @ApiParam(value = "结束时间")
    private Date endDate;


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
}
