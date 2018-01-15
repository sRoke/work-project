package net.kingsilk.qh.agency.admin.api.staff.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

/**
 *
 */
@ApiModel(value = "员工分页请求信息")
public class StaffPageReq {


    @ApiParam(value = "当前页数", defaultValue = "1")
    @QueryParam(value = "curPage")
    private Integer curPage;
    @ApiParam(value = "每页数量", defaultValue = "15")
    @QueryParam(value = "pageSize")
    private Integer pageSize;
    @ApiParam(value = "帐号")
    @QueryParam(value = "keyWord")
    private String keyWord;


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
}
