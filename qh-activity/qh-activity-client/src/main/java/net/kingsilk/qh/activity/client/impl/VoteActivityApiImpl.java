package net.kingsilk.qh.activity.client.impl;

import net.kingsilk.qh.activity.api.UniPage;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteActivity.VoteActivityApi;
import net.kingsilk.qh.activity.api.brandApp.vote.voteActivity.dto.VoteActivityPageResp;
import net.kingsilk.qh.activity.api.brandApp.vote.voteActivity.dto.VoteActivityReq;
import net.kingsilk.qh.activity.client.AbstractQhPlatformApi;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

public class VoteActivityApiImpl extends AbstractQhPlatformApi implements VoteActivityApi {

    public static final String API_URL_add = "http://localhost:16000/activity/rs/api/brandApp/{brandAppId}/voteActivity";
    public static final String API_URL_update = "http://localhost:16000/activity/rs/api/brandApp/{brandAppId}/voteActivity/{id}";

    private RestOperations restTemplate;

    private final Map<String, String> defaultApiUrls;

    public VoteActivityApiImpl(RestOperations restOperations) {
        final Map<String, String> map = new LinkedHashMap<>();
        map.put("add", API_URL_add);
        map.put("" +
                "" +
                "", API_URL_update);
        defaultApiUrls = Collections.unmodifiableMap(map);

    }

    @Override
    public UniResp<String> isForceFollow(String brandAppId, String activityId, String workId, String shareUrl) {
        return null;
    }

    @Override
    public UniResp<String> add(String brandAppId, VoteActivityReq voteActivityReq) {
        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("brandAppId", brandAppId);

        String apiUrl = getApiUrl("add");

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<VoteActivityReq> reqEntity = new HttpEntity<>(voteActivityReq, reqHeaders);

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
    public UniResp<String> update(
            String brandAppId,
            String id,
            VoteActivityReq voteActivityReq) {

        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("brandAppId", brandAppId);
        pathVars.put("id", id);

        String apiUrl = getApiUrl("update");

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<VoteActivityReq> reqEntity = new HttpEntity<>(voteActivityReq, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<String>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.PUT,
                reqEntity,
                new ParameterizedTypeReference<UniResp<String>>() {
                });

        UniResp<String> uniResp = respEntity.getBody();
        return uniResp;
    }

    @Override
    public UniResp<Void> delete(String brandAppId, String id) {
        return null;
    }

    @Override
    public UniResp<UniPage<VoteActivityPageResp>> page(String brandAppId, int size, int page, List<String> sort, String keyWord) {
        return null;
    }

    @Override
    public UniResp<VoteActivityPageResp> info(String brandAppId, String id) {
        return null;
    }

    @Override
    public UniResp<String> isForcePhone(String brandAppId, String id) {
        return null;
    }


    @Override
    public Map<String, String> getDefaultApiUrls() {
        return defaultApiUrls;
    }
}
