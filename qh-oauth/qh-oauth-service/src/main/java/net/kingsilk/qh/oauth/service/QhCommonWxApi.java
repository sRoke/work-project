package net.kingsilk.qh.oauth.service;

import com.mongodb.*;
import com.mongodb.util.*;
import com.mysema.commons.lang.Assert;
import net.kingsilk.qh.oauth.*;
import net.kingsilk.qh.oauth.controller.*;
import net.kingsilk.qh.oauth.security.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.util.*;

import java.util.*;

;

/**
 * 调用 qh-common-admin WxController 上的方法
 */
@Service
@Deprecated
public class QhCommonWxApi {

    protected Logger log = LoggerFactory.getLogger(QhCommonWxApi.class);

    @Autowired
    private OAuth2RestTemplate qhCommonAdminCRT;

    @Autowired
    private SecService secUtils;

    @Autowired
    private QhOAuthProperties props;

    public String selfAt(String appId) {


        String url = props.getQhCommon().getAdmin().getApi().getWx_selfAt();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.ALL));
//        headers.set("Authorization", OAuth2AccessToken.BEARER_TYPE+" " + tokenInfos.access_token);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("appId", appId)
                .build()
                .toUri()
                .toString();

        HttpEntity<String> reqEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> respEntity = qhCommonAdminCRT.exchange(
                uri,
                HttpMethod.GET,
                reqEntity,
                String.class
        );

        DBObject respJson = (DBObject) JSON.parse(respEntity.getBody());

        if (!HttpStatus.OK.equals(respEntity.getStatusCode())
                || (respJson.get("status") != null && !UniResp.isSuccess((Integer) respJson.get("status")))
                || StringUtils.isEmpty(((DBObject) respJson.get("data")).get("accessToken"))
                ) {

            log.error("获取 accessToken 失败: " + respEntity.getBody());
            Assert.isTrue(false, "获取 accessToken 失败");
        }


        return (String) ((DBObject) respJson.get("data")).get("accessToken");
    }

    public Object selfUserAt(String appId, String code) {

        String url = props.getQhCommon().getAdmin().getApi().getWx_selfUserAt();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.ALL));
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("appId", appId)
                .queryParam("code", code)
                .build()
                .toUri()
                .toString();

        HttpEntity<String> reqEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> respEntity = qhCommonAdminCRT.exchange(
                uri,
                HttpMethod.GET,
                reqEntity,
                String.class
        );

        DBObject respJson = (DBObject) JSON.parse(respEntity.getBody());
        if (!HttpStatus.OK.equals(respEntity.getStatusCode())
                || (respJson.get("status") != null && !UniResp.isSuccess((Integer) respJson.get("status")))
                || StringUtils.isEmpty(((DBObject) respJson.get("data")).get("accessToken"))
                ) {

            log.error("获取 accessToken 失败: " + respEntity.getBody());
            Assert.isTrue(false, "获取 accessToken 失败");
        }

        return respJson;
    }

    public Object getSelfUserAt(String appId, String openId) {
        Assert.isTrue(false, "Not Implemented");
        return null;
    }

}
