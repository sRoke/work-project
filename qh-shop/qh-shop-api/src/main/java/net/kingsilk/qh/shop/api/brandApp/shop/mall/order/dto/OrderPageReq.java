package net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageReq;

import javax.ws.rs.QueryParam;
import java.util.List;

@ApiModel(value = "订单分页请求信息")
public class OrderPageReq extends UniPageReq {


    @ApiParam(value = "关键字")
    @QueryParam(value = "keyWord")
    private String keyWord;
//    @ApiParam(value = "开始时间")
//    @QueryParam(value = "startDate")
//    private String startDate;
//    @ApiParam(value = "结束时间")
//    @QueryParam(value = "endDate")
//    private String endDate;
    @ApiParam(value = "订单状态")
    @QueryParam(value = "status")
    private List<String> status;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

//    public String getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(String startDate) {
//        this.startDate = startDate;
//    }

//    public String getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(String endDate) {
//        this.endDate = endDate;
//    }


    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }
}
