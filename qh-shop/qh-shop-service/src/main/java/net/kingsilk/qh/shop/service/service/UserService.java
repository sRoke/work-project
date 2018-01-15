package net.kingsilk.qh.shop.service.service;

import net.kingsilk.qh.agency.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.service.QhShopProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

/**
 * Created by lit on 17/9/1.
 */
@Component
public class UserService {

    @Autowired
    private SecService secService;

    @Autowired
    private QhShopProperties prop;

    @Autowired
    @Qualifier("oauthRestTemplate")
    private RestTemplate restTemplate;

    String getCurUser() {
        return secService.curUserId();
    }

    /**
     * 获取用户在oauth系统中的用户信息
     *
     * @return
     */
    public Map getOauthUserInfo(HttpServletRequest request) {

        String at = request.getHeader("Authorization");
        String url = prop.getQhOAuth().getWap().getUserinfoApi();

        HttpHeaders headers = new HttpHeaders();

        List<MediaType> type = new ArrayList<>();
        type.add(MediaType.ALL);
        headers.setAccept(type);
        //headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, "")
        headers.set("Authorization", at);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString();
        HttpEntity<Map> reqEntity = new HttpEntity<>(null, headers);
        System.out.println("------------------------------------" + url);
        ResponseEntity<Map> respEntity = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, Map.class);
        out.println("respEntity: " + respEntity.getBody());
        return (Map) respEntity.getBody().get("data");
    }

    public String getAppId(String brandAppId) {

//        String at = request.getHeader("Authorization")
        String url = prop.getQhPay().getWap().getApi().getUrl() + brandAppId;

        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.ALL);
        headers.setAccept(mediaTypes);
        //headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, "")
//        headers.set("Authorization", at);
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString();

        HttpEntity<Map> reqEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UniResp> respEntity = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, UniResp.class);
        System.out.println("respEntity: ${respEntity.body}");
        if (respEntity.getBody().getData() == null) {
            throw new ErrStatusException(ErrStatus.PARTNER_500, "没有设置支付信息");
        }
        Map data = (Map) respEntity.getBody().getData();
        Map wxPayInfo = (Map) data.get("wxPayInfo");
        if (respEntity.getBody().getData() == null || wxPayInfo == null || wxPayInfo.get("appId") == null) {
            throw new ErrStatusException(ErrStatus.PARTNER_500, "没有设置支付信息");
        }
        return String.valueOf(wxPayInfo.get("appId"));
    }

    public Map getOauthUserInfoByPhone(String phone) {

//        String at = request.getHeader("Authorization")
        String url = prop.getQhOAuth().getWap().getUserinfoByPhoneApi();

        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.ALL);
        headers.setAccept(mediaTypes);
        //headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, "")
//        headers.set("Authorization", at);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("phone", phone);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(map)
                .build()
                .toUri()
                .toString();

        HttpEntity<Map> reqEntity = new HttpEntity<Map>(null, headers);
        ResponseEntity<UniResp> respEntity = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, UniResp.class);
        System.out.println("respEntity: ${respEntity.body}");
        return (Map) respEntity.getBody().getData();
    }

    public Map getOauthUserInfoByUserId(String userId) {

        String url = prop.getQhOAuth().getWap().getGetUserInfoByUserIdApi();

        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.ALL);
        headers.setAccept(mediaTypes);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("userId", userId);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(map)
                .build()
                .toUri()
                .toString();

        HttpEntity<Map> reqEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UniResp> respEntity = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, UniResp.class);
        System.out.println("respEntity: ${respEntity.body}");
        return (Map) respEntity.getBody().getData();
    }

    public Map getWxMpUser(String wxComAppId, String wxMpAppId, String openId) {
        QhShopProperties prop = this.prop;
        StringBuffer sb = new StringBuffer(prop.getWx4j().getWap().getApi().getUrl());
        StringBuffer url = sb.append("/wxCom/").append(wxComAppId).append("/mp/").append(wxMpAppId).append("/user/auth/").append(openId);
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.ALL);
        headers.setAccept(mediaTypes);
        String uri = UriComponentsBuilder.fromHttpUrl(url.toString())
                .build()
                .toUri()
                .toString();

        HttpEntity<Map> reqEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UniResp> respEntity = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, UniResp.class);
        System.out.println("respEntity: ${respEntity.body}");
        if (null == respEntity.getBody()) {
            throw new ErrStatusException(net.kingsilk.qh.shop.api.ErrStatus.UNLOGIN, "重新登录");
        }
        return (Map) respEntity.getBody().getData();
    }
}
