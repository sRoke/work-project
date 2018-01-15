package net.kingsilk.qh.agency.wap.api.item.dto;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

public class ItemSearchReq {


    @ApiParam(value = "当前页数", required = false)
    @QueryParam(value = "number")
    private int number;
    @ApiParam(value = "分页大小", required = false)
    @QueryParam(value = "pageSize")
    private int pageSize;
    @ApiParam(value = "类型", required = false)
    @QueryParam(value = "type")
    private String type;
    @ApiParam(value = "排序方式", required = false, allowableValues = "ASC, DESC")
    @QueryParam(value = "sort")
    private String sort = "DESC";
    @ApiParam(value = "排序依据", required = false)
    @QueryParam(value = "sortBy")
    private String sortBy = "dateCreated";
    @ApiParam(value = "商品分类", required = false)
    @QueryParam(value = "categoryId")
    private String categoryId;

    @ApiParam(value = "关键字", required = false)
    @QueryParam(value = "keyWord")
    private String keyWord;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
