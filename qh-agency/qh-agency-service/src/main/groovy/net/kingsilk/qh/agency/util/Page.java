package net.kingsilk.qh.agency.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcw on 3/16/17.
 * 分页相关工具类
 *
 * @see org.springframework.data.domain.Page
 */
@Deprecated
public class Page<T> {

    private Integer curPage;

    private Integer pageSize;

    private Integer totalCount;

    private List<T> list = new ArrayList<T>();

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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
