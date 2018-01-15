package net.kingsilk.qh.oauth.client.impl.user;

import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.AddUserReq;
import net.kingsilk.qh.oauth.api.user.UserApi;
import net.kingsilk.qh.oauth.api.user.UserGetResp;
import net.kingsilk.qh.oauth.client.AbstractQhOAuthApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

/**
 *
 */
public class UserApiImpl extends AbstractQhOAuthApi implements UserApi {


    public static final String API_URL_get = "/user/{userId}";
    public static final String API_URL_list = "/user";
    public static final String API_URL_add = "/user/oauth/addUser";
    public static final String API_URL_info = "/user/info";
    public static final String API_URL_search = "/user/search";
    public static final String API_URL_getInfo = "/user/getInfoByUserId";
    public static final String API_URL_getInfoByPhone = "/user/getInfoByPhone";

    private final Map<String, String> defaultApiUrls;

    @Qualifier("restTemplate")
    private RestOperations restTemplate;
    private String oauthUrl;

//    private ConversionService conversionService;

    public UserApiImpl(RestOperations restOperations, String oauthUrl) {

        final Map<String, String> map = new LinkedHashMap<>();
        map.put("get", API_URL_get);
        map.put("list", API_URL_list);
        map.put("add", API_URL_add);
        map.put("info", API_URL_info);
        map.put("search", API_URL_search);
        map.put("getInfo", API_URL_getInfo);
        map.put("getInfoByPhone", API_URL_getInfoByPhone);
        defaultApiUrls = Collections.unmodifiableMap(map);

        this.restTemplate = restOperations;
        this.oauthUrl = oauthUrl;
//        this.conversionService = conversionService;
    }


    @Override
    public UniResp<UserGetResp> get(String userId) {

        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);


        String apiUrl = oauthUrl + getApiUrl("get");


        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();


        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        //reqHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<Void> reqEntity = new HttpEntity<>(null, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<UserGetResp>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<UniResp<UserGetResp>>() {
                });

        UniResp<UserGetResp> uniResp = respEntity.getBody();
        return uniResp;
    }

    @Override
    public UniResp<UniPage<UserGetResp>> list(
            Integer size,
            Integer page,
            List<String> sort,
            List<String> userIds
    ) {

        // 构建 API URL （含 path 变量和 query param）
        String apiUrl = oauthUrl + getApiUrl("list");

        Map<String, Object> pathVars = new HashMap<>();

        MultiValueMap<String, String> queryParasm = new LinkedMultiValueMap<>();


        if (size != null && size > 0) {
            queryParasm.set("size", Integer.toString(size));
        }
        if (page != null && page >= 0) {
            queryParasm.set("page", Integer.toString(page));
        }
        if (userIds != null && !userIds.isEmpty()) {
            queryParasm.put("userIds", userIds);
        }

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParams(queryParasm)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<Void> reqEntity = new HttpEntity<>(null, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<UniPage<UserGetResp>>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<UniResp<UniPage<UserGetResp>>>() {
                });

        UniResp<UniPage<UserGetResp>> uniResp = respEntity.getBody();
        return uniResp;
    }

    @Override
    public UniResp<UniPage<UserGetResp>> search(
            Integer size,
            Integer page,
            List<String> sort,
            String q
    ) {
        // 构建 API URL （含 path 变量和 query param）
        String apiUrl = oauthUrl + getApiUrl("search");

        Map<String, Object> pathVars = new HashMap<>();

        MultiValueMap<String, String> mup = new LinkedMultiValueMap<>();

        if (size != null && size > 0) {
            mup.set("size", Integer.toString(size));
        }
        if (page != null && page >= 0) {
            mup.set("page", Integer.toString(page));
        }
        if (!StringUtils.isEmpty(q)) {
            mup.set("q", q);
        }

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParams(mup)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<Void> reqEntity = new HttpEntity<>(null, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<UniPage<UserGetResp>>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<UniResp<UniPage<UserGetResp>>>() {
                });

        UniResp<UniPage<UserGetResp>> uniResp = respEntity.getBody();
        return uniResp;
    }

    @Override
    public UniResp<String> addUser(AddUserReq addUserReq) {

        String apiUrl = oauthUrl + getApiUrl("add");
        MultiValueMap<String, String> queryParasm = new LinkedMultiValueMap<>();
        if (!StringUtils.isEmpty(addUserReq.getPhone())) {
            queryParasm.set("phone", addUserReq.getPhone());
        }
        if (!StringUtils.isEmpty(addUserReq.getRealName())) {
            queryParasm.set("realName", addUserReq.getRealName());
        }

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParams(queryParasm)
                .build()
                .encode()
                .toUri();

        HttpHeaders reqHeaders = new HttpHeaders();

        reqHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity<>(addUserReq, reqHeaders);

        ResponseEntity<UniResp<String>> infoResp = restTemplate.exchange(
                apiUri,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<UniResp<String>>() {
                });

        return infoResp.getBody();
    }

    @Override
    public UniResp<UserGetResp> info() {
        String url = oauthUrl + getApiUrl("info");
        URI apiUri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .encode()
                .toUri();

        HttpHeaders reqHeaders = new HttpHeaders();

        reqHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity<>(reqHeaders);

        ResponseEntity<UniResp<UserGetResp>> infoResp = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<UserGetResp>>() {
                });

        return infoResp.getBody();
    }

    @Override
    public UniResp<String> update(AddUserReq addUserReq) {
        String url = oauthUrl + getApiUrl("list");
        MultiValueMap<String, String> queryParasm = new LinkedMultiValueMap<>();
        if (!StringUtils.isEmpty(addUserReq.getPhone())) {
            queryParasm.set("phone", addUserReq.getPhone());
        }
        if (!StringUtils.isEmpty(addUserReq.getRealName())) {
            queryParasm.set("realName", addUserReq.getRealName());
        }
        URI apiUri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(queryParasm)
                .build()
                .encode()
                .toUri();

        HttpHeaders reqHeaders = new HttpHeaders();

        reqHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity requestEntity = new HttpEntity<>(addUserReq, reqHeaders);

        ResponseEntity<UniResp<String>> infoResp = restTemplate.exchange(
                apiUri,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<UniResp<String>>() {
                });

        return infoResp.getBody();
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        return defaultApiUrls;
    }

    @Override
    public UniResp<UserGetResp> getInfoByUserId(String userId) {

        String apiUrl = oauthUrl + getApiUrl("getInfo");


        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("userId", userId)
                .build()
                .encode()
                .toUri();


        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        //reqHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<Void> reqEntity = new HttpEntity<>(null, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<UserGetResp>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<UniResp<UserGetResp>>() {
                });

        UniResp<UserGetResp> uniResp = respEntity.getBody();
        return uniResp;


    }

    @Override
    public UniResp<UserGetResp> getInfoByPhone(String phone) {
        String apiUrl = oauthUrl + getApiUrl("getInfoByPhone");


        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("phone", phone)
                .build()
                .encode()
                .toUri();


        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        //reqHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<Void> reqEntity = new HttpEntity<>(null, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<UserGetResp>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<UniResp<UserGetResp>>() {
                });

        UniResp<UserGetResp> uniResp = respEntity.getBody();
        return uniResp;
    }

    @Override
    public UniResp<String> getOpenIdByUserId(String userId) {
        return null;
    }
}
