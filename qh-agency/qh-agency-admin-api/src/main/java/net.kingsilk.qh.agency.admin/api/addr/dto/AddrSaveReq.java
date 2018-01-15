package net.kingsilk.qh.agency.admin.api.addr.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * Created by zcw on 3/16/17.
 */
@ApiModel
public class AddrSaveReq {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdcNo() {
        return adcNo;
    }

    public void setAdcNo(String adcNo) {
        this.adcNo = adcNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * Address id
     */
    @ApiParam(value = "id", required = false)
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * adc No
     */
    @ApiParam(value = "adcNo", required = true)
    @ApiModelProperty(value = "adcNo")
    private String adcNo;
    /**
     * 街道
     */
    @ApiParam(value = "街道", required = true)
    @ApiModelProperty(value = "街道")
    private String street;
    /**
     * 收货人
     */
    @ApiParam(value = "收货人", required = true)
    @ApiModelProperty(value = "收货人")
    private String receiver;
    /**
     * 手机号
     */
    @ApiParam(value = "手机号", required = true)
    @ApiModelProperty(value = "手机号")
    private String phone;
    /**
     * 备注
     */
    @ApiParam(value = "备注", required = true)
    @ApiModelProperty(value = "备注")
    private String memo;
    /**
     * 是否默认收货地址
     */
    @ApiParam(value = "是否默认收货地址", required = true)
    @ApiModelProperty(value = "是否默认收货地址")
    private boolean isDefault;
}
