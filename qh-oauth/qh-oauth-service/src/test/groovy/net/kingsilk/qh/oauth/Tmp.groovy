package net.kingsilk.qh.oauth

import org.springframework.web.util.UriComponentsBuilder

/**
 * Created by zll on 01/04/2017.
 */
class Tmp {

    static final String WX_USER_AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize"


    static void main(String[] args) {
        String path = UriComponentsBuilder.fromHttpUrl(WX_USER_AUTH_URL)
                .queryParam("appid", "111")
                .queryParam("response_type", "code")
                .fragment("wechat_redirect")
                .build()
                .toUri()
                .toString()

        println path
    }
}
