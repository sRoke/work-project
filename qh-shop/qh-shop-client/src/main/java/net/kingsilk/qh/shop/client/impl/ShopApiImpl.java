package net.kingsilk.qh.shop.client.impl;

import net.kingsilk.qh.shop.api.UniPageResp;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.ShopApi;
import net.kingsilk.qh.shop.api.brandApp.shop.dto.ShopCreateRep;
import net.kingsilk.qh.shop.api.brandApp.shop.dto.ShopResp;
import net.kingsilk.qh.shop.client.AbstractQhShopApi;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

public class ShopApiImpl extends AbstractQhShopApi implements ShopApi {

    final String API_URL_INFO
            = "/brandApp/{brandAppId}/shop/{shopId}";

    String shopUrl;
    RestOperations restTemplate;

    public ShopApiImpl(RestOperations restTemplate, String shopUrl) {
        this.restTemplate = restTemplate;
        this.shopUrl = shopUrl;
    }

    @Override
    public UniResp<String> create(String brandAppId, ShopCreateRep shopCreateRep) {
        return null;
    }

    @Override
    public UniResp<ShopResp> info(String brandAppId, String shopId) {

        // 设置 HTTP 请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);

        // 构造请求URL
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", brandAppId);
        map.put("shopId", shopId);

        String url = shopUrl + getApiUrl("info");

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();

        System.out.println("url" + url + "," + uri + "uri");

        ResponseEntity<UniResp<ShopResp>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<ShopResp>>() {
                }
        );

        return responseEntity.getBody();
    }

    @Override
    public UniResp<String> delet(String brandAppId, String shopId) {
        return null;
    }

    @Override
    public UniResp<String> enable(String brandAppId, String shopId, Boolean enable) {
        return null;
    }

    @Override
    public UniResp<String> update(String brandAppId, String shopId, ShopCreateRep shopCreateRep) {
        return null;
    }

    @Override
    public UniResp<UniPageResp<ShopResp>> page(String brandAppId, int size, int page, List<String> sort, String keyWord) {
        return null;
    }

    @Override
    public UniResp<List<Map<String, String>>> getShopList(String brandAppId, String userId) {
        return null;
    }

    @Override
    public UniResp<Map<String, Long>> getShopOrderNum(String brandAppId, String shopId) {
        return null;
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        final Map<String, String> m = new HashMap<>();
        m.put("info", API_URL_INFO);
        final Map<String, String> defaultApiUrls = Collections.unmodifiableMap(m);
        return defaultApiUrls;
    }
}
