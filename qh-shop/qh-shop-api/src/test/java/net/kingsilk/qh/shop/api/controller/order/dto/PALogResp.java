package net.kingsilk.qh.shop.api.controller.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

@ApiModel(value = "账户明显请求参数")
public class PALogResp {

    @ApiParam(value = "订单id")
    @QueryParam(value = "id")
    private String id;

    @ApiParam(value = "交易编号")
    @QueryParam(value = "seq")
    private String seq;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}

