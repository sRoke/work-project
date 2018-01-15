package net.kingsilk.qh.agency.api.brandApp.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
public class OrderChooseAddrReq {
    /**
     * 订单id
     */
    @ApiParam(value = "orderId", required = true)
    private String orderId;
    /**
     * 地址id
     */
    @ApiParam(value = "addrId", required = true)
    private String addrId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddrId() {
        return addrId;
    }

    public void setAddrId(String addrId) {
        this.addrId = addrId;
    }
}
