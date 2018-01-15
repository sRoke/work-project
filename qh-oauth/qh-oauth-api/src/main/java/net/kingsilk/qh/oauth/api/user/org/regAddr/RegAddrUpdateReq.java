package net.kingsilk.qh.oauth.api.user.org.regAddr;

import io.swagger.annotations.*;

import java.util.*;

/**
 *
 */
@ApiModel
public class RegAddrUpdateReq {

    @ApiModelProperty("行政区划代码(6位数字)")
    private Optional<String> adc = Optional.empty();

    @ApiModelProperty("街道")
    private Optional<String> street = Optional.empty();

    @ApiModelProperty("邮政编码")
    private Optional<String> postCode = Optional.empty();

    @ApiModelProperty("百度地图中经度")
    private Optional<String> coorX = Optional.empty();

    @ApiModelProperty("百度地图中纬度")
    private Optional<String> coorY = Optional.empty();

    @ApiModelProperty("联系人姓名")
    private Optional<String> contact = Optional.empty();

    @ApiModelProperty("联系人手机号")
    private Optional<Set<String>> phones = Optional.empty();

    @ApiModelProperty("用户备注")
    private Optional<String> memo = Optional.empty();


    // ------------------------ 自动生成的 getter、 setter


    public Optional<String> getAdc() {
        return adc;
    }

    public void setAdc(Optional<String> adc) {
        this.adc = adc;
    }

    public Optional<String> getStreet() {
        return street;
    }

    public void setStreet(Optional<String> street) {
        this.street = street;
    }

    public Optional<String> getPostCode() {
        return postCode;
    }

    public void setPostCode(Optional<String> postCode) {
        this.postCode = postCode;
    }

    public Optional<String> getCoorX() {
        return coorX;
    }

    public void setCoorX(Optional<String> coorX) {
        this.coorX = coorX;
    }

    public Optional<String> getCoorY() {
        return coorY;
    }

    public void setCoorY(Optional<String> coorY) {
        this.coorY = coorY;
    }

    public Optional<String> getContact() {
        return contact;
    }

    public void setContact(Optional<String> contact) {
        this.contact = contact;
    }

    public Optional<Set<String>> getPhones() {
        return phones;
    }

    public void setPhones(Optional<Set<String>> phones) {
        this.phones = phones;
    }

    public Optional<String> getMemo() {
        return memo;
    }

    public void setMemo(Optional<String> memo) {
        this.memo = memo;
    }

}
