package net.kingsilk.qh.vote.client.impl;

import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.authorities.AuthoritiesApi;
import net.kingsilk.qh.vote.client.AbstractQhPlatformApi;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

public class AuthoritiesApiImpl extends AbstractQhPlatformApi implements AuthoritiesApi {

    final String API_URL_ADD
            = "/voteApp/{voteAppId}/authorities/userId/{userId}/orgId/{orgId}";

    String activityUrl;
    RestOperations restTemplate;

    public AuthoritiesApiImpl(RestOperations restTemplate, String activityUrl) {
        this.restTemplate = restTemplate;
        this.activityUrl = activityUrl;
    }

    @Override
    public UniResp<Set<String>> getAuthorities(String brandAppId) {
        return null;
    }

    @Override
    public UniResp<String> setSAStaff(String voteAppId, String userId, String orgId) {
        // 设置 HTTP 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);

        // 构造请求URL
        Map<String, String> map = new LinkedHashMap<>();
        map.put("voteAppId", voteAppId);
        map.put("userId", userId);
        map.put("orgId", orgId);

        String url = activityUrl + getApiUrl("add");

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
