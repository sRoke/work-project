package net.kingsilk.qh.shop.api.brandApp.shop.mall.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.ws.rs.QueryParam;
import java.util.LinkedHashSet;
@ApiModel
public class MemberMinReq {
    @ApiModelProperty("真名")
    @QueryParam("realName")
    private String realName;
    @ApiModelProperty("手机号")
    @QueryParam("phone")
    private String phone;
    @ApiModelProperty("联系方式")
    @QueryParam("contacts")
    private LinkedHashSet contacts;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LinkedHashSet getContacts() {
        return contacts;
    }

    public void setContacts(LinkedHashSet contacts) {
        this.contacts = contacts;
    }
}
