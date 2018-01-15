package net.kingsilk.qh.oauth.service;

import com.mongodb.*;
import com.mongodb.util.*;
import net.kingsilk.qh.oauth.*;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

import java.net.*;
import java.util.*;

/**
 *
 */
@Service
public class WxService {

    private Logger log = LoggerFactory.getLogger(WxService.class);

    public static final String USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo";

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private QhOAuthProperties authProperties;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 微信：开放平台：网站应用开发：微信登录功能：网站应用微信登录开发指南
     *
     * 根据 code 获取代表用户授权信息的 access_token
     *
     * @param code
     * @return
     */
    public User getUserAtByCode(String code) {

        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("secret", authProperties.getQhOAuth().getWap().getWx().getAppSecret())
                .queryParam("code", code)
                .queryParam("grant_type", "authorization_code")
                .build()
                .encode()
                .toUri();

        ResponseEntity<String> respEntity = restTemplate.getForEntity(uri, String.class);
        String respStr = respEntity.getBody();
        DBObject jsonObj = (DBObject) JSON.parse(respStr);
        if (jsonObj.get("openid") == null) {
            return null;
        }
        String openId = (String) jsonObj.get("openid");
        String unionid = (String) jsonObj.get("unionid");
        User user = null; // FIXME
//        User user = userRepository.findOne(Expressions.allOf(
//                QUser.user.openId.eq(openId)
//        ));
//
//        if (user == null) {
//            user = new User();
//            user.setDateCreated(new Date());
//            user.setOpenId(openId);
//            user.setUsername("wx_" + openId);
//        }
//        user.setCode(code);
//        userRepository.save(user);
        return user;
    }

    public Map<String, Object> requestUserInfo(String userAt, String openId) {
        return requestUserInfo(userAt, openId, "zh_CN");
    }

    /**
     * 获取用户的信息。
     * @param userAt 用户授权的accessToken
     * @param openId 用户的openId
     * @param lang 返回国家地区语言版本（默认为 "zh_CN"），zh_CN 简体，zh_TW 繁体，en 英语
     * @return 用户信息。 包含的key 为：
     *      openid
     *      nickname
     *      sex
     *      province
     *      city
     *      country
     *      headimgurl
     *      privilege
     *      unionid
     */
    public Map<String, Object> requestUserInfo(String userAt, String openId, String lang) {
        Assert.notNull(openId, "openId 不能为空");
        Assert.notNull(userAt, "userAt 不能为空");

        URI uri = UriComponentsBuilder.fromHttpUrl(USERINFO_URL)
                .queryParam("access_token", userAt)
                .queryParam("openid", openId)
                .queryParam("lang", lang)
                .build()
                .encode()
                .toUri();

        ResponseEntity<String> respEntity = restTemplate.getForEntity(uri, String.class);

        Assert.isTrue(HttpStatus.OK == respEntity.getStatusCode(),
                "获取微信信息失败， 响应状态码:  " + respEntity.getStatusCode().value() + " ");
        Assert.isTrue(respEntity.getBody() != null, "获取微信信息失败，响应内容为空");

        DBObject json = (DBObject) JSON.parse(respEntity.getBody());

        Assert.notNull(json.get("errcode"), "获取微信信息失败 : " + json.get("errcode") + " - " + json.get("errmsg"));

        // FIMXE appId
        // log.info("获取微信用户 "+json.get("openid")+" 在 "+appId+" 的用户信息");
        log.info("获取微信用户 " + json.get("openid") + " 用户信息");

        Map<String, Object> m = new LinkedHashMap<>();
        m.put("openid", json.get("openid"));
        m.put("nickname", json.get("nickname"));
        m.put("sex", json.get("sex"));
        m.put("province", json.get("province"));
        m.put("city", json.get("city"));
        m.put("country", json.get("country"));
        m.put("headimgurl", json.get("headimgurl"));
        m.put("privilege", json.get("privilege"));
        m.put("unionid", json.get("unionid"));

        return m;
    }

}
