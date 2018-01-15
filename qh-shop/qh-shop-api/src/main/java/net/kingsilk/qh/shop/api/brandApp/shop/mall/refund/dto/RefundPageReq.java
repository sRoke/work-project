package net.kingsilk.qh.shop.api.brandApp.shop.mall.refund.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.shop.api.UniPageReq;

import javax.ws.rs.QueryParam;
import java.util.List;

@ApiModel(value = "订单分页请求信息")
public class RefundPageReq extends UniPageReq {

    @ApiParam(value = "售后订单状态")
    @QueryParam(value = "status")
    private List<String> status;

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }
}
