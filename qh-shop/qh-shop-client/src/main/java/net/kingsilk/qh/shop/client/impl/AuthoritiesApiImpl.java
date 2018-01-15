package net.kingsilk.qh.shop.client.impl;


import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.authorities.AuthoritiesApi;
import net.kingsilk.qh.shop.client.AbstractQhShopApi;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

public class AuthoritiesApiImpl extends AbstractQhShopApi implements AuthoritiesApi {

    final String API_URL_ADD
            = "/brandApp/{brandAppId}/userId/{userId}/orgId/{orgId}";

    String shopUrl;
    RestOperations restTemplate;

    public AuthoritiesApiImpl(RestOperations restTemplate, String shopUrl) {
        this.restTemplate = restTemplate;
        this.shopUrl = shopUrl;
    }


    @Override
    public UniResp<Set<String>> admin(String brandAppId) {
        return null;
    }

    @Override
    public UniResp<String> setSAStaff(String brandAppId, String userId, String orgId) {
        // 设置 HTTP 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);

        // 构造请求URL
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", brandAppId);
        map.put("userId", userId);
        map.put("orgId", orgId);

        String url = shopUrl + getApiUrl("add");

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();

        System.out.println("url" + url + "," + uri + "uri");
        ResponseEntity<UniResp<String>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<String>>() {
                }
        );
        return responseEntity.getBody();
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        final Map<String, String> m = new HashMap<>();
        m.put("add", API_URL_ADD);
        final Map<String, String> defaultApiUrls = Collections.unmodifiableMap(m);
        return defaultApiUrls;
    }
}
