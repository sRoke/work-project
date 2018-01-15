package net.kingsilk.qh.agency.admin.controller
/**
 * Created by zcw on 4/5/17.
 */
class  BasePageResp<T> {
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
    List<T> recList;
}
