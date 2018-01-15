package net.kingsilk.qh.shop.api.brandApp.shop.mall.refund.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel
public class OrderCheckReq {
    /**
     * skuId
     */
    @ApiParam(name = "skuId", value = "skuId", required = true)
//    @QueryParam(value = "skuId")
    private String skuId;

    /**
     * 锁属 orderId
     */
    @ApiParam(name = "orderId", value = "orderId", required = true)
//    @QueryParam(value = "orderId")
    private String orderId;

    /**
     * 下单数量
     */
    @ApiParam(name = "num", value = "num", required = true)
//    @QueryParam(value = "num")
    private Integer num;

    @ApiParam(name = "reason", value = "reason", required = true)
//    @QueryParam(value = "reason")
    private String reason;

    @ApiParam(name = "price", value = "price", required = true)
//    @QueryParam(value = "price")
    private String price;

    @ApiParam(name = "type", value = "type", required = true)
//    @QueryParam(value = "type")
    private String type;

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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
