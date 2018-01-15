package net.kingsilk.qh.agency.wap.controller

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

    /////////////////////////////////get方法，有修改，不分页情况这不考虑，建议另写接口
    int getCurPage() {
        if (!curPage || curPage < 0) {
            curPage = 1
        }
        return curPage - 1              //分页从0开始
    }

    int getPageSize() {
        if (!pageSize || pageSize < 0) {
            pageSize = 10
        }
        return pageSize
    }
}
