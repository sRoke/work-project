package net.kingsilk.qh.agency.api.brandApp.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
public class OrderCancelReq {


    /**
     * 订单id
     */
    @ApiParam(value = "id", required = true)
    private String id;
    /**
     * 取消原因
     */
    @ApiParam(value = "reason", required = false)
    private String reason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
