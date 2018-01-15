package net.kingsilk.qh.agency.client;

import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.item.ItemApi;
import net.kingsilk.qh.agency.api.brandApp.item.dto.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ItemApiImpl extends AbstractApi implements ItemApi {

    final String API_URL_PAGE
            = "/brandApp/{brandAppId}/item";


    final String API_URL_INFO
            = "/brandApp/{brandAppId}/item/{id}";

    String agencyUrl;
    RestOperations restTemplate;

    public ItemApiImpl(RestOperations restTemplate, String agencyUrl) {
        this.restTemplate = restTemplate;
        this.agencyUrl = agencyUrl;
    }

    @Override
    public UniResp<String> save(
            String brandAppId,
            ItemSaveReq itemSaveReq) {
        //TODO 暂时用不到
        throw new RuntimeException("error");
    }

    @Override
    public UniResp<ItemInfoResp> info(String brandAppId, String id) {

        //设置http请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //构造请求url
        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);

        String url = agencyUrl + getApiUrl("info");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", brandAppId);
        map.put("id", id);
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();

        //发送请求
        ResponseEntity<UniResp<ItemInfoResp>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<ItemInfoResp>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public UniResp<UniPageResp<ItemMinInfo>> page(
            String brandAppId,
            ItemPageReq itemPageReq
    ) {

        // 设置 HTTP 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ItemPageReq> requestEntity = new HttpEntity<>(itemPageReq, headers);

        // 构造请求URL
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", brandAppId);
        MultiValueMap<String, String> mup = new LinkedMultiValueMap<>();
        if (!StringUtils.isEmpty(itemPageReq.getStatus())) {
            mup.set("keyWord", itemPageReq.getStatus());
        }
        if (!StringUtils.isEmpty(itemPageReq.getTitle())) {
            mup.set("type", itemPageReq.getTitle());
        }
        String url = agencyUrl + getApiUrl("page");
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(mup)
                .buildAndExpand(map)
                .toUri();
        System.out.println("1111111111111");
        // 发送请求
        ResponseEntity<UniResp<UniPageResp<ItemMinInfo>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<UniPageResp<ItemMinInfo>>>() {
                }
        );
        return responseEntity.getBody();
    }

    @Override
    public UniResp<String> delete(
            String brandAppId,
            String id) {
        //TODO 暂时用不到
        throw new RuntimeException("error");
    }

    @Override
    public UniResp<String> changeStatus(
            String brandAppId,
            String id,
            String status) {
        //TODO 暂时用不到
        throw new RuntimeException("error");
    }

    @Override
    public UniResp<String> update(
            String brandAppId,
            String id,
            ItemSaveReq itemSaveReq) {
        //TODO 暂时用不到

        throw new RuntimeException("error");
    }

    @Override
    public UniResp<ItemInfoModel> detail(
            String brandAppId,
            String id,
            String type) {
        //TODO 暂时用不到
        throw new RuntimeException("error");
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        final Map<String, String> m = new HashMap<>();
        m.put("page", API_URL_PAGE);
        m.put("info", API_URL_INFO);
        final Map<String, String> defaultApiUrls = Collections.unmodifiableMap(m);
        return defaultApiUrls;
    }
}
