package net.kingsilk.qh.oauth.api.s.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

@ApiModel
public class UserAddreq extends UserInfoReq {

    @ApiParam(value = "用户名")
    private String username;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}
