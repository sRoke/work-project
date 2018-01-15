package net.kingsilk.qh.oauth.api.s.login;

import io.swagger.annotations.*;

import java.util.*;

/**
 *
 */
@ApiModel
public class LoginResp {


    @ApiModelProperty("用户Id")
    private String userId;

    @ApiModelProperty(
            value = "手机号码",
            notes = "可能是尚未绑定的手机号"
    )
    private String phone;

    @ApiModelProperty(
            value = "手机号绑定时间",
            notes = "如果为空，则需要先绑定手机号"
    )
    private Date phoneVerifiedAt;

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

    public Date getPhoneVerifiedAt() {
        return phoneVerifiedAt;
    }

    public void setPhoneVerifiedAt(Date phoneVerifiedAt) {
        this.phoneVerifiedAt = phoneVerifiedAt;
    }
}
