package net.kingsilk.qh.oauth.api.s.user.dto;

import io.swagger.annotations.ApiParam;

import java.util.Set;

public class UserInfoReq {

    @ApiParam(value = "用户id")
    private String userId;

    @ApiParam(value = "身份证号码")
    private String idNo;

    @ApiParam(value = "真实姓名")
    private String realName;

    @ApiParam(value = "联系电话")
    private Set<String> phones;

    @ApiParam(value = "头像URL")
    private String avatar;

    @ApiParam(value = "微信号")
    private String wxNo;

    @ApiParam(value = "QQ号")
    private String qq;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getWxNo() {
        return wxNo;
    }

    public void setWxNo(String wxNo) {
        this.wxNo = wxNo;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }
}
