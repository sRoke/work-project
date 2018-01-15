package net.kingsilk.qh.oauth.client.impl.user.org;

import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.org.OrgAddReq;
import net.kingsilk.qh.oauth.api.user.org.OrgApi;
import net.kingsilk.qh.oauth.api.user.org.OrgGetResp;
import net.kingsilk.qh.oauth.api.user.org.OrgUpdateReq;
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

public class OrgApiImpl extends AbstractQhOAuthApi implements OrgApi {


    private String oauthUrl;
    @Qualifier("restTemplate")
    private RestOperations restTemplate;


    public static final String API_URL_get = "/user/{userId}/org/{orgId}";
    public static final String API_URL_list = "/user/{userId}/org";
    public static final String API_URL_add = API_URL_list;
    public static final String API_URL_delete = API_URL_get;
    public static final String API_URL_update = API_URL_get;
    private final Map<String, String> defaultApiUrls;

    public OrgApiImpl(RestOperations restOperations, String oauthUrl) {

        final Map<String, String> map = new LinkedHashMap<>();
        map.put("get", API_URL_get);
        map.put("list", API_URL_list);
        map.put("add", API_URL_add);
        map.put("delete", API_URL_delete);
        map.put("update", API_URL_update);
        defaultApiUrls = Collections.unmodifiableMap(map);

        this.restTemplate = restOperations;
        this.oauthUrl = oauthUrl;
//        this.conversionService = conversionService;
    }


    @Override
    public UniResp<String> add(String userId, OrgAddReq orgAddReq) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("userId", userId);

        String url = oauthUrl + getApiUrl("add");

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(orgAddReq, headers);

        ResponseEntity<UniResp<String>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<UniResp<String>>() {
                }
        );
        return responseEntity.getBody();
    }

    @Override
    public UniResp<Void> del(String userId, String orgId) {

        String url = oauthUrl + getApiUrl("delete");

        Map<String, String> map = new LinkedHashMap<>();
        map.put("userId", userId);
        map.put("orgId", orgId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(null, headers);

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();

        ResponseEntity<UniResp<Void>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                request,
                new ParameterizedTypeReference<UniResp<Void>>() {
                }
        );

        return responseEntity.getBody();
    }

    @Override
    public UniResp<OrgGetResp> get(String userId, String orgId) {
        return null;
    }

    @Override
    public UniResp<Void> update(String userId, String orgId, OrgUpdateReq orgUpdateReq) {
        String url = oauthUrl + getApiUrl("update");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("userId", userId);
        map.put("orgId", orgId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<>(orgUpdateReq, headers);

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();

        ResponseEntity<UniResp<Void>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.PATCH,
                request,
                new ParameterizedTypeReference<UniResp<Void>>() {
                }
        );

        return responseEntity.getBody();
    }

    @Override
    public UniResp<UniPage<OrgGetResp>> list(int size, int page, List<String> sort, String userId) {

        String url = oauthUrl + getApiUrl("list");
        MultiValueMap<String, String> queryParasm = new LinkedMultiValueMap<>();
        Map<String, Object> pathVars = new HashMap<>();
        if (size > 0) {
            queryParasm.set("size", Integer.toString(size));
        }
        if (page >= 0) {
            queryParasm.set("page", Integer.toString(page));
        }
        if (sort != null && sort.size() != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder = stringBuilder.append("[");
            for (int i = 0; i < sort.size() - 1; i++) {
                stringBuilder = stringBuilder.append(sort.get(i)).append(",");
            }
            stringBuilder = stringBuilder.append(sort.get(sort.size() - 1)).append("]");
            queryParasm.set("sort", stringBuilder.toString());
        }
        if (!StringUtils.isEmpty("userId")) {
            queryParasm.set("userId", userId);
        }

        URI apiUri = UriComponentsBuilder.fromHttpUrl(url)
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
        ResponseEntity<UniResp<UniPage<OrgGetResp>>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<UniResp<UniPage<OrgGetResp>>>() {
                });

        UniResp<UniPage<OrgGetResp>> uniResp = respEntity.getBody();
        return uniResp;
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        return defaultApiUrls;
    }
}
