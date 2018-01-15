package net.kingsilk.qh.agency.admin.controller.company

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "公司分页请求信息")
public class CompanyPageReq {

    @ApiParam(value = "当前页数", defaultValue = "1")
    private Integer curPage = 1;

    @ApiParam(value = "每页数量", defaultValue = "15")
    private Integer pageSize = 15;

    @ApiParam(value = "公司名称")
    private String keyWord;


    public Integer getCurPage() {
        return curPage
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage
    }

    public Integer getPageSize() {
        return pageSize
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize
    }

    public String getKeyWord() {
        return keyWord
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord
    }
}