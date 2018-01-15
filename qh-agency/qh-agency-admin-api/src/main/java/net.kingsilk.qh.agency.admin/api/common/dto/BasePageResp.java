package net.kingsilk.qh.agency.admin.api.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import java.util.List;

/**
 *
 */
@ApiModel(value = "分页返回基本字段")
public class BasePageResp<T> {

    /**
     * 当前页码
     */
    @ApiParam(value = "当前页码")
    private int curPage;
    /**
     * 分页大小
     */
    @ApiParam(value = "分页大小")
    private int pageSize;
    /**
     * 总数量
     */
    @ApiParam(value = "总数量")
    private int totalCount;
    /**
     * 总页数
     */
    @ApiParam(value = "总页数")
    private int totalPages;
    /**
     * 分页数据
     */
    @ApiParam(value = "分页数据")
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
