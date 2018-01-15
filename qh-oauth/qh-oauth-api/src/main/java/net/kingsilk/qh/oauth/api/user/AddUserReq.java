package net.kingsilk.qh.oauth.api.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel
public class AddUserReq {

    private String userId;

    private String realName;

    @ApiParam(value = "密码")
    private String password;

    @ApiParam(value = "手机号码")
    private String phone;

    @ApiParam(value = "支付密码")
    private String payPassword;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
