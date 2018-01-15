package net.kingsilk.qh.oauth.api.s.user.dto;

import io.swagger.annotations.*;

@ApiModel
public class UserInfoResp {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "身份证号码")
    private String idNo;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "头像URL")
    private String avatar;

    @ApiModelProperty(value = "微信号")
    private String wxNo;

    @ApiModelProperty(value = "QQ号")
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
