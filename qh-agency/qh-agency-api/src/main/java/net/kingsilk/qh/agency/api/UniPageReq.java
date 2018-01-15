package net.kingsilk.qh.agency.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * 参考 spring-data 中的 Page，方便 Jackson 实例化。
 */
@ApiModel
public class UniPageReq<T> {

    @ApiModelProperty("每页多少个记录,最大100")
    @QueryParam("size")
    @DefaultValue("10")
    private int size;

    @ApiModelProperty("页码。从0开始")
    @QueryParam("page")
    @DefaultValue("0")
    private int page;

    @ApiModelProperty(
            value = "排序。格式为 '排序字段,排序方向'。其中，排序方向为 'asc'、'desc'。可以传递多个 sort 参数",
            example = "age,asc"
    )
    @QueryParam("sort")
    private List<String> sort;


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }
}
