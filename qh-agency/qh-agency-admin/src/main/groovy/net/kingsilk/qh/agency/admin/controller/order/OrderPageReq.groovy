package net.kingsilk.qh.agency.admin.controller.order

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiParam
import org.joda.time.format.ISODateTimeFormat
import org.springframework.format.annotation.DateTimeFormat

import java.text.SimpleDateFormat

@ApiModel(value = "订单分页请求信息")
class OrderPageReq {

    @ApiParam(value = "当前页数", defaultValue = "0")
    Integer curPage = 0;

    @ApiParam(value = "每页数量", defaultValue = "15")
    Integer pageSize = 15;

    @ApiParam(value = "关键字")
    String keyWord;

    @ApiParam(value = "开始时间")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    Date startDate;

    @ApiParam(value = "结束时间")
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    Date endDate;

    @ApiParam(value = "订单状态")
    String status;

    @Override
    public String toString() {
        return "OrderPageReq{" +
                "curPage=" + curPage +
                ", pageSize=" + pageSize +
                ", keyWord='" + keyWord + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }
}