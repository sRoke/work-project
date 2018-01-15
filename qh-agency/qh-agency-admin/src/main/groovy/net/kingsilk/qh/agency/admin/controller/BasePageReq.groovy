package net.kingsilk.qh.agency.admin.controller

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by zcw on 4/5/17.
 */
@ApiModel
class BasePageReq {

    @ApiParam(value = "当前页数", required = false)
    int curPage;

    @ApiParam(value = "分页大小", required = false)
    int pageSize;

    /////////////////////////////////set、get方法，有修改，不分页情况这不考虑，建议另写接口
    int getCurPage() {
        if (!curPage || curPage < 1) {
            curPage = 1
        }
//        if (pageSize == 0 && curPage == 0) {
//            pageSize = 0;
//        }
        return curPage-1
    }

    void setCurPage(int curPage) {
        this.curPage = curPage
    }

    int getPageSize() {
        if (!pageSize || pageSize < 0) {
            pageSize = 10
        }
//        if (pageSize == 0 && curPage == 0) {
//            pageSize = 0;
//        }
        return pageSize
    }

    void setPageSize(int pageSize) {
        this.pageSize = pageSize
    }
}
