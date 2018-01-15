package net.kingsilk.qh.agency.wap.controller.addr.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.domain.Address

/**
 * Created by zcw on 3/20/17.
 */
@ApiModel
class AddrModel {

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
     * 省
     */
    @ApiParam(value = "省", required = true)
    @ApiModelProperty(value = "省")
    String province

    /**
     * 市
     */
    @ApiParam(value = "市", required = true)
    @ApiModelProperty(value = "市")
    String city

    /**
     * 区
     */
    @ApiParam(value = "区", required = true)
    @ApiModelProperty(value = "区")
    String area;

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
     * 是否默认收货地址
     */
    @ApiParam(value = "是否默认收货地址", required = true)
    @ApiModelProperty(value = "是否默认收货地址")
    boolean isDefault;


    void convert(Address address) {
        if (!address) {
            return;
        }
        id = address.id
        adcNo = address.adc.no
        street = address.street
        receiver = address.receiver
        phone = address.phone
        isDefault = address.defaultAddr
        area = address.adc.name
        city = address.adc.parent?.name
        province = address.adc.parent?.parent?.name
    }
}
