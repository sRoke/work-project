package net.kingsilk.qh.shop.api.brandApp.shop.refund.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel
public class OrderCheckReq {
    /**
     * skuId
     */
    @ApiParam(name = "skuId", value = "skuId", required = true)
    private String skuId;

    /**
     * 锁属 orderId
     */
    @ApiParam(name = "orderId", value = "orderId", required = true)
    private String orderId;

    /**
     * 下单数量
     */
    @ApiParam(name = "num", value = "num", required = true)
    private Integer num;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
