package net.kingsilk.qh.oauth.api.s.reg.phone;

import io.swagger.annotations.*;

/**
 *
 */
@ApiModel
public class RegResp {

    @ApiModelProperty("用户Id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
