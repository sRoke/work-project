package net.kingsilk.qh.raffle.api.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * 参考 spring-data 中的 Page，方便 Jackson 实例化。
 */
@ApiModel
public class UniPageResp<T> {

    @ApiModelProperty("总共有多少页数据")
    private int totalPages;

    @ApiModelProperty("总共匹配的记录")
    private long totalElements;

    @ApiModelProperty("当前页码")
    private int number;

    @ApiModelProperty("每页显示多少条记录")
    private int size;

    @ApiModelProperty("记录列表")
    private List<T> content = new ArrayList<>();

    @ApiModelProperty("排序列表")
    private List<UniOrder> orders = new ArrayList<>();

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public List<UniOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<UniOrder> orders) {
        this.orders = orders;
    }
}
