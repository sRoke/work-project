package net.kingsilk.qh.agency.wap.api.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

/**
 *
 */
@ApiModel
public class BasePageReq <T> {
    public int getCurPage() {
        if (curPage < 1) {
            curPage = 1;
        }
        return curPage - 1;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        if (pageSize < 0) {
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @ApiParam(value = "当前页数", required = false)
    @QueryParam(value = "curPage")
    private int curPage;
    @ApiParam(value = "分页大小", required = false)
    @QueryParam(value = "pageSize")
    private int pageSize;
}
