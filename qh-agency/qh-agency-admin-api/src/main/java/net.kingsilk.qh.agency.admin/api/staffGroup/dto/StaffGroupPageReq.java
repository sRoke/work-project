package net.kingsilk.qh.agency.admin.api.staffGroup.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.admin.api.common.dto.Base;

import java.util.Date;

/**
 * Created by lit on 17-3-20.
 */
@ApiModel(value = "StaffGroupInfoResp")
public class StaffGroupPageReq extends Base {


    @ApiParam(value = "当前页数", defaultValue = "1")
    @ApiModelProperty(value = "")
    private Integer curPage = 1;
    @ApiParam(value = "每页数量", defaultValue = "15")
    private Integer pageSize = 15;
    @ApiParam(value = "帐号")
    private String keyWord;
    @ApiParam(value = "开始时间")
    private Date startDate;
    @ApiParam(value = "结束时间")
    private Date endDate;


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
