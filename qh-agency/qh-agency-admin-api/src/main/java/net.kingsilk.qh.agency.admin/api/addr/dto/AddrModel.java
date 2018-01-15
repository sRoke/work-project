package net.kingsilk.qh.agency.admin.api.addr.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * Created by zcw on 3/20/17.
 */
@ApiModel
public class AddrModel {
//    public void convert(Address address) {
//        if (!address.asBoolean()) {
//            return;
//
//        }
//
//        id = ((String) (address.id));
//        adcNo = ((String) (address.adc.no));
//        street = ((String) (address.street));
//        receiver = ((String) (address.receiver));
//        phone = ((String) (address.phone));
//        isDefault = ((boolean) (address.invokeMethod("isDefault", new Object[0])));
//        area = ((String) (address.adc.name));
//        final Object parent = address.adc.parent;
//        city = ((String) ((parent == null ? null : parent.name)));
//        final Object parent2 = address.adc.parent;
//        final Object parent1 = (parent2 == null ? null : parent2.parent);
//        province = ((String) ((parent1 == null ? null : parent1.name)));
//    }

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
     * 省
     */
    @ApiParam(value = "省", required = true)
    @ApiModelProperty(value = "省")
    private String province;
    /**
     * 市
     */
    @ApiParam(value = "市", required = true)
    @ApiModelProperty(value = "市")
    private String city;
    /**
     * 区
     */
    @ApiParam(value = "区", required = true)
    @ApiModelProperty(value = "区")
    private String area;
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
     * 是否默认收货地址
     */
    @ApiParam(value = "是否默认收货地址", required = true)
    @ApiModelProperty(value = "是否默认收货地址")
    private boolean isDefault;
}
