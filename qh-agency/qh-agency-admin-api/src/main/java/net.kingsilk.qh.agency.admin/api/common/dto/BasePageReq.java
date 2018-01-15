package net.kingsilk.qh.agency.admin.api.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

/**
 *
 */
@ApiModel(value = "分页请求基本字段")
public class BasePageReq <T> {
    @ApiParam(value = "当前页数", required = false)
    @QueryParam(value = "curPage")
    private int curPage;
    @ApiParam(value = "分页大小", required = false)
    @QueryParam(value = "pageSize")
    private int pageSize;

    public int getCurPage() {
//        if (curPage < 1) {
//            curPage = 1;
//        }
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
//        if (pageSize < 0) {
//            pageSize = 15;
//        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


}
