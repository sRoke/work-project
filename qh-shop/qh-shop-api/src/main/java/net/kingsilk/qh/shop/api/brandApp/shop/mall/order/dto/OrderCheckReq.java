package net.kingsilk.qh.shop.api.brandApp.shop.mall.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

@ApiModel
public class OrderCheckReq {
    /**
     * skuId
     */
    @ApiParam(name = "skuId", value = "skuId", required = true)
    private String skuId;
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
}
