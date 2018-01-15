package net.kingsilk.qh.agency.api.brandApp.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel(value = "订单修改提供信息")
public class OrderAddressReq {

    @ApiParam(value = "收货人")
    private String receiver;

    @ApiParam(value = "收货人手机号")
    private String phone;

    @ApiParam(value = "地址编号")
    private String countyNo;

    @ApiParam(value = "详细地址")
    private String street;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountyNo() {
        return countyNo;
    }

    public void setCountyNo(String countyNo) {
        this.countyNo = countyNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
