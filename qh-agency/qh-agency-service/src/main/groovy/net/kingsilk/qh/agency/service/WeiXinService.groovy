package net.kingsilk.qh.agency.service

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.util.Assert

/**
 * Created by lit on 17/8/31.
 */
public class WeiXinService {


    @Autowired
    OAuth2RestTemplate oClientCommonAdminRestTemplate

    private static List defaultJsApiList = [
            "onMenuShareTimeline",
            "onMenuShareAppMessage",
            "onMenuShareQQ",
            "onMenuShareWeibo",
            "onMenuShareQZone",
            "startRecord",
            "stopRecord",
            "onVoiceRecordEnd",
            "playVoice",
            "pauseVoice",
            "stopVoice",
            "onVoicePlayEnd",
            "uploadVoice",
            "downloadVoice",
            "chooseImage",
            "previewImage",
            "uploadImage",
            "downloadImage",
            "translateVoice",
            "getNetworkType",
            "openLocation",
            "getLocation",
            "hideOptionMenu",
            "showOptionMenu",
            "hideMenuItems",
            "showMenuItems",
            "hideAllNonBaseMenuItem",
            "showAllNonBaseMenuItem",
            "closeWindow",
            "scanQRCode",
            "chooseWXPay",
            "openProductSpecificView",
            "addCard",
            "chooseCard",
            "openCard"
    ]

    /**
     * 微信：公众平台开发者文档：微信 JS-SDK：微信JS-SDK说明文档：JSSDK使用步骤：步骤三：通过config接口注入权限验证配置
     *
     * 生成一个默认的微信 JS API 的配置对象。
     * JS 客户端在获取到该对象的JSON数据后，应根据需要，重新设置其中的 debug, jsApiList，其他值不要修改。
     *
     * @param appId
     * @param appSecret
     * @param url 当前网页的URL
     * @return 微信 jsapi 的配置对象，转换为 JSON返回即可使用
     */
    Map genJsApiConfig(String url) {

        String appId = properties.wxPub.appId
        Assert.isTrue url as boolean, "参数URL不能为空，请通过Ajax上传当前网页的URL(不含 `#` 后面的部分)"
        // 获取 jsapi_ticket
        String jsApiTicket = getJsApiTicket(appId)

        // 按照 "附录1-JS-SDK使用权限签名算法" 中的规则计算签名
        // 按照 参数key进行排序，因此使用 TreeMap
        def signParams = new TreeMap()
        signParams.url = url
        signParams.noncestr = this.getRandomString(40)
        signParams.timestamp = (long) (System.currentTimeMillis() / 1000)
        signParams.jsapi_ticket = jsApiTicket

        // 按照 "key1=value1&key2=value2" 的格式连接成字符串，key和value都不要 URLEncode，保持原值即可
        def buf = new StringBuilder()
        signParams.each { k, v ->
            buf.append(k).append('=').append(v).append('&')
        }
        buf.length = buf.size() - 1
        def string1 = buf.toString()
        // 计算签名
        def signature = Hex.encodeHexString(DigestUtils.sha(string1))

        println("计算微信js Api 所需的签名： string1 = ${string1} , signature = ${signature}")
        return [
                debug    : false,
                appId    : appId,
                timestamp: signParams.timestamp,
                nonceStr : signParams.noncestr,
                signature: signature,
                jsApiList: defaultJsApiList
        ]
    }

    /**
     * 微信：公众平台开发者文档：微信 JS-SDK：微信JS-SDK说明文档：附录1-JS-SDK使用权限签名算法：jsapi_ticket
     *
     * jsapi_ticket的有效期为2小时
     *
     * @param appId
     * @return jsapi_ticket
     */
    private String getJsApiTicket(String appId) {
        println("getJsApiTicket ===>  appId  ==>  ${appId}")
        Map resp = oClientCommonAdminRestTemplate.getForObject(properties.oauth2.qhCommon.admin.api.jsTicket + "?appId=${appId}", Map)
        println("jsAT ====>   ${resp}")
        return resp.data?.jsApiTicket as String
    }

    /**
     * 随机生成字符串
     * @param length 字符串长度
     * @return
     */
    String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789"
        Random random = new Random()
        StringBuffer sb = new StringBuffer()
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length())
            sb.append(base.charAt(number))
        }
        return sb.toString()
    }
}
