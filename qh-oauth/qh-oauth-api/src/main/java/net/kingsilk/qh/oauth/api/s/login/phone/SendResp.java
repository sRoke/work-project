package net.kingsilk.qh.oauth.api.s.login.phone;

import io.swagger.annotations.*;

/**
 *
 */
@ApiModel
public class SendResp {

    @ApiModelProperty("是否需要用先验证图片验证码")
    private boolean needCaptacha;

    @ApiModelProperty("是否已经发送短信验证码")
    private boolean sended;

    public boolean isNeedCaptacha() {
        return needCaptacha;
    }

    public void setNeedCaptacha(boolean needCaptacha) {
        this.needCaptacha = needCaptacha;
    }

    public boolean isSended() {
        return sended;
    }

    public void setSended(boolean sended) {
        this.sended = sended;
    }
}
