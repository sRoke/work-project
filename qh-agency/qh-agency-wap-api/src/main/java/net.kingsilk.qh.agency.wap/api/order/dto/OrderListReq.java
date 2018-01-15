package net.kingsilk.qh.agency.wap.api.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import net.kingsilk.qh.agency.wap.api.common.dto.BasePageReq;

import javax.ws.rs.QueryParam;

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
public class OrderListReq  extends BasePageReq{


    @ApiParam(value = "订单状态", required = false)
    @QueryParam(value = "status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
