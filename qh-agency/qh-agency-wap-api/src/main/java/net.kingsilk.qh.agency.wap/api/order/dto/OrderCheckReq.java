package net.kingsilk.qh.agency.wap.api.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
public class OrderCheckReq {
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


}
