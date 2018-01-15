package net.kingsilk.qh.oauth.core.wap.controller.user.model;

import io.swagger.annotations.*;

@ApiModel
public class UserModel {

    @ApiModelProperty(value = "userId")
    private String userId;
    @ApiModelProperty(value = "phone")
    private String phone;
    @ApiModelProperty(value = "openId")
    private String openId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

}
