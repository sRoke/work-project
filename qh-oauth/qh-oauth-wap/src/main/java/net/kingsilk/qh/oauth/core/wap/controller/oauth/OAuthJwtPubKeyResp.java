package net.kingsilk.qh.oauth.core.wap.controller.oauth;

import io.swagger.annotations.*;

/**
 *
 */
@ApiModel
@Deprecated
public class OAuthJwtPubKeyResp {


    @ApiModelProperty(value = "JWT 的校验用的公钥")
    private String pubKey;


    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }

}
