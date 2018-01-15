package net.kingsilk.qh.agency.wap.api.common.dto;

import java.util.List;

/**
 *
 */
public class BasePageResp<T> {

    /**
     * 当前页码
     */

    private int curPage;
    /**
     * 分页大小
     */
    private int pageSize;
    /**
     * 总数量
     */
    private int totalCount;
    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 分页数据
     */
    private List<T> recList;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getRecList() {
        return recList;
    }

    public void setRecList(List<T> recList) {
        this.recList = recList;
    }
}
