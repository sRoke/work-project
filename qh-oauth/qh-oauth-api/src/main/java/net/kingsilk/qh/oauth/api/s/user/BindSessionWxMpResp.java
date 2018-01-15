package net.kingsilk.qh.oauth.api.s.user;

import io.swagger.annotations.*;

import java.util.*;

@ApiModel
public class BindSessionWxMpResp {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "微信公众号APP ID")
    private String wxMpAppId;

    @ApiModelProperty(value = "用户的微信 Open Id")
    private String openId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWxMpAppId() {
        return wxMpAppId;
    }

    public void setWxMpAppId(String wxMpAppId) {
        this.wxMpAppId = wxMpAppId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
