package net.kingsilk.qh.agency.admin.controller.category

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam

/**
 * Created by tpx on 17-3-16.
 */
@ApiModel(value = "商品分类分页请求信息")
class CategoryPageReq {

    @ApiParam(value = "当前页数", defaultValue = "1")
    private Integer curPage = 1;

    @ApiParam(value = "每页数量", defaultValue = "15")
    private Integer pageSize = 15;

    @ApiParam(value = "分类名称")
    private String name;

    public String getName() {
        return name
    }

    public void setName(String name) {
        this.name = name
    }

    public Integer getCurPage() {
        return curPage
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage
    }

    public Integer getPageSize() {
        return pageSize
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize
    }


}