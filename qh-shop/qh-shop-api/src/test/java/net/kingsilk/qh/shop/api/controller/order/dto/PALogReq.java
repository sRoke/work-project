package net.kingsilk.qh.shop.api.controller.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;
import java.util.List;

@ApiModel(value = "账户明显请求参数")
public class PALogReq {

    @ApiParam(value = "订单类型")
    @QueryParam(value = "type")
    private String type;

    @ApiParam(value = "明细id和对应的订单id")
    @QueryParam(value = "ids")
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
