package net.kingsilk.qh.oauth.client.impl.user.addr;

import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.addr.AddrAddReq;
import net.kingsilk.qh.oauth.api.user.addr.AddrApi;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.oauth.api.user.addr.AddrUpdateReq;
import net.kingsilk.qh.oauth.client.AbstractQhOAuthApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

/**
 *
 */
public class AddrApiImpl extends AbstractQhOAuthApi implements AddrApi {


    public static final String API_URL_add = "/user/{userId}/addr";
    public static final String API_URL_get = "/user/{userId}/addr/{addrId}";
    public static final String API_URL_del = API_URL_get;
    public static final String API_URL_update = API_URL_get;
    public static final String API_URL_list = API_URL_add;
    public static final String API_URL_Default = API_URL_add + "/default";


    private final Map<String, String> defaultApiUrls;
    @Qualifier("restTemplate")
    private RestOperations restTemplate;
    private String oauthUrl;

//    private ConversionService conversionService;

    public AddrApiImpl(RestOperations restOperations, String oauthUrl) {

        final Map<String, String> map = new LinkedHashMap<>();
        map.put("add", API_URL_add);
        map.put("get", API_URL_get);
        map.put("del", API_URL_del);
        map.put("update", API_URL_update);
        map.put("list", API_URL_list);
        map.put("default", API_URL_Default);
        defaultApiUrls = Collections.unmodifiableMap(map);
        this.oauthUrl = oauthUrl;
        this.restTemplate = restOperations;
//        this.conversionService = conversionService;
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        return defaultApiUrls;
    }

    @Override
    public UniResp<String> add(
            String userId,
            AddrAddReq addrReq
    ) {

        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);

        String apiUrl = oauthUrl + getApiUrl("add");

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<AddrAddReq> reqEntity = new HttpEntity<>(addrReq, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<String>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.POST,
                reqEntity,
                new ParameterizedTypeReference<UniResp<String>>() {
                });

        UniResp<String> uniResp = respEntity.getBody();
        return uniResp;
    }

    @Override
    public UniResp<AddrGetResp> get(
            String userId,
            String addrId
    ) {

        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);
        pathVars.put("addrId", addrId);

        String apiUrl = oauthUrl + getApiUrl("get");

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<Void> reqEntity = new HttpEntity<>(null, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<AddrGetResp>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<UniResp<AddrGetResp>>() {
                });

        UniResp<AddrGetResp> uniResp = respEntity.getBody();
        return uniResp;
    }

    @Override
    public UniResp<Void> del(
            String userId,
            String addrId
    ) {

        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);
        pathVars.put("addrId", addrId);

        String apiUrl = oauthUrl + getApiUrl("del");

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<Void> reqEntity = new HttpEntity<>(null, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<Void>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.DELETE,
                reqEntity,
                new ParameterizedTypeReference<UniResp<Void>>() {
                });

        UniResp<Void> uniResp = respEntity.getBody();
        return uniResp;
    }

    @Override
    public UniResp<Void> update(
            String userId,
            String addrId,
            AddrUpdateReq addrReq
    ) {

        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);
        pathVars.put("addrId", addrId);

        String apiUrl = oauthUrl + getApiUrl("update");

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<AddrUpdateReq> reqEntity = new HttpEntity<>(addrReq, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<Void>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.PATCH,
                reqEntity,
                new ParameterizedTypeReference<UniResp<Void>>() {
                });

        UniResp<Void> uniResp = respEntity.getBody();
        return uniResp;
    }

    @Override
    public UniResp<UniPage<AddrGetResp>> list(
            int size,
            int page,
            List<String> sort,
            String userId,
            List<String> addrIds
    ) {
        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);


        String apiUrl = oauthUrl + getApiUrl("list");

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        if (page >= 0) {
            queryParams.set("page", Integer.toString(page));
        }
        if (size > 0) {
            queryParams.set("size", Integer.toString(size));
        }
        if (sort != null && !sort.isEmpty()) {
            queryParams.put("sort", sort);
        }
        if (addrIds != null && !addrIds.isEmpty()) {
            queryParams.put("addrIds", addrIds);
        }

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParams(queryParams)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<Void> reqEntity = new HttpEntity<>(null, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<UniPage<AddrGetResp>>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<UniResp<UniPage<AddrGetResp>>>() {
                });

        UniResp<UniPage<AddrGetResp>> uniResp = respEntity.getBody();
        return uniResp;
    }

    @Override
    public UniResp<AddrGetResp> getDefault(String userId, String addrType) {
        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);

        String apiUrl = oauthUrl + getApiUrl("default");

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("addrType", addrType)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(addrType, headers);

        ResponseEntity<UniResp<AddrGetResp>> responseEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<UniResp<AddrGetResp>>() {
                }
        );

        UniResp<AddrGetResp> uniResp = responseEntity.getBody();
        return uniResp;
    }

    @Override
    public UniResp<Void> setDefault(String userId, String addrId, String addrType) {
        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);
        pathVars.put("addrId", addrId);

        String apiUrl = oauthUrl + getApiUrl("default") + "/{addrId}";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.set("addrType", addrType);

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParams(queryParams)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(addrType, headers);

        ResponseEntity<UniResp<Void>> responseEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.PUT,
                req,
                new ParameterizedTypeReference<UniResp<Void>>() {
                }
        );

        UniResp<Void> uniResp = responseEntity.getBody();
        return uniResp;
    }

}
