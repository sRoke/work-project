package net.kingsilk.qh.agency.wap.api.cart.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

@ApiModel
public class CartAddReq {


    @ApiParam(value = "skuId", required = true)
    @ApiModelProperty(value = "skuId")
    private String skuId;
    @ApiParam(value = "num", required = true)
    @ApiModelProperty(value = "数量，已存在则在原基础上追加")
    private int num;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
