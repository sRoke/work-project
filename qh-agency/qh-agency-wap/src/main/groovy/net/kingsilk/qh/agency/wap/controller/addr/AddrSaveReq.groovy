package net.kingsilk.qh.agency.wap.controller.addr

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
class AddrSaveReq {

    /**
     * Address id
     */
    @ApiParam(value = "id", required = false)
    @ApiModelProperty(value = "id")
    String id;

    /**
     * adc No
     */
    @ApiParam(value = "adcNo", required = true)
    @ApiModelProperty(value = "adcNo")
    String adcNo;

    /**
     * 街道
     */
    @ApiParam(value = "街道", required = true)
    @ApiModelProperty(value = "街道")
    String street

    /**
     * 收货人
     */
    @ApiParam(value = "收货人", required = true)
    @ApiModelProperty(value = "收货人")
    String receiver

    /**
     * 手机号
     */
    @ApiParam(value = "手机号", required = true)
    @ApiModelProperty(value = "手机号")
    String phone;

    /**
     * 备注
     */
    @ApiParam(value = "备注", required = true)
    @ApiModelProperty(value = "备注")
    String memo

    /**
     * 是否默认收货地址
     */
    @ApiParam(value = "是否默认收货地址", required = true)
    @ApiModelProperty(value = "是否默认收货地址")
    boolean isDefault;
}
