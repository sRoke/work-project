package net.kingsilk.qh.oauth.api.user.addr;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Set;

/**
 *
 */
@ApiModel
public class AddrAddReq {


    @ApiModelProperty("行政区划代码(6位数字)")
    private String adc;

    @ApiModelProperty("街道")
    private String street;

    @ApiModelProperty("邮政编码")
    private String postCode;

    @ApiModelProperty("百度地图中经度")
    private String coorX;

    @ApiModelProperty("百度地图中纬度")
    private String coorY;

    @ApiModelProperty("联系人姓名")
    private String contact;

    @ApiModelProperty("联系人手机号")
    private Set<String> phones;

    @ApiModelProperty("是否是默认地址")
    private boolean defaultAddr;

    @ApiModelProperty("用户备注")
    private String memo;

    @ApiModelProperty("地址类型")
    private String addrType;

    // ------------------------ 自动生成的 getter、 setter

    public String getAdc() {
        return adc;
    }

    public void setAdc(String adc) {
        this.adc = adc;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCoorX() {
        return coorX;
    }

    public void setCoorX(String coorX) {
        this.coorX = coorX;
    }

    public String getCoorY() {
        return coorY;
    }

    public void setCoorY(String coorY) {
        this.coorY = coorY;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isDefaultAddr() {
        return defaultAddr;
    }

    public void setDefaultAddr(boolean defaultAddr) {
        this.defaultAddr = defaultAddr;
    }

    public String getAddrType() {
        return addrType;
    }

    public void setAddrType(String addrType) {
        this.addrType = addrType;
    }
}
