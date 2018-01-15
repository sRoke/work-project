package net.kingsilk.qh.agency.wap.controller

import org.springframework.data.domain.Page

/**
 * 分页基础resp
 * D -> domain
 * M -> ui-model
 */
@Deprecated
class BasePageResp<D, M> {
    /**
     * 当前页码
     */
    int curPage;

    /**
     * 分页大小
     */
    int pageSize;

    /**
     * 总数量
     */
    int totalCount;

    /**
     * 总页数
     */
    int totalPages

    /**
     * 分页数据
     */
    List<M> recList;

    void convert(Page<D> page, BasePageReq req) {
        curPage = req.curPage + 1
        pageSize = req.pageSize
        totalCount = page.totalElements
        totalPages = page.totalPages
    }
}
