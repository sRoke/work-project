package net.kingsilk.qh.agency.admin.controller.category

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.domain.Category
import net.kingsilk.qh.agency.util.Page

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "商品分类分页返回信息")
class CategoryPageResp {

    private Integer curPage;

    private Integer pageSize;

    private Integer totalCount;

    private List<CategorySaveReq> recList = new ArrayList<CategorySaveReq>();

    Integer getCurPage() {
        return curPage
    }

    void setCurPage(Integer curPage) {
        this.curPage = curPage
    }

    Integer getPageSize() {
        return pageSize
    }

    void setPageSize(Integer pageSize) {
        this.pageSize = pageSize
    }

    Integer getTotalCount() {
        return totalCount
    }

    void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount
    }

    List<CategorySaveReq> getRecList() {
        return recList
    }

    void setRecList(List<CategorySaveReq> recList) {
        this.recList = recList
    }

    public CategoryPageResp convertToResp(Page<Category> categorys) {
        for (Category it : categorys.list) {
            new CategorySaveReq().convertCategoryToResp(it)
            this.recList.add(it)
        }
        this.pageSize = categorys.pageSize;
        this.curPage = categorys.curPage;
        this.totalCount = categorys.totalCount;
        return this;
    }
}