package net.kingsilk.qh.shop.client.impl;

import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.sync.SyncItemApi;
import net.kingsilk.qh.shop.api.brandApp.sync.dto.ItemSyncReq;
import net.kingsilk.qh.shop.client.AbstractQhShopApi;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class SyncItemImpl extends AbstractQhShopApi implements SyncItemApi {

    final String API_URL_SYNC
            = "/brandApp/{brandAppId}/shop/{shopId}/SyncItem";

    @Override
    public UniResp<String> syncItem(String brandAppId,ItemSyncReq itemSyncReq) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity requestEntity = new RequestEntity(itemSyncReq,
                httpHeaders, null, null);
        Map<String, String> varmp = new LinkedHashMap<>();
        varmp.put("brandAppId", brandAppId);

        String url = shopUrl + getApiUrl("sync");
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(varmp)
                .toUri();

        ResponseEntity<UniResp<String>> responseEntity = restTemplate.exchange(uri,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<UniResp<String>>() {
                });

        return responseEntity.getBody();
    }

    String shopUrl;
    RestOperations restTemplate;


    public SyncItemImpl(RestOperations restTemplate, String shopUrl) {
        this.restTemplate = restTemplate;
        this.shopUrl = shopUrl;
    }


    @Override
    public Map<String, String> getDefaultApiUrls() {
        final Map<String, String> m = new HashMap<>();
        m.put("sync", API_URL_SYNC);
        final Map<String, String> defaultApiUrls = Collections.unmodifiableMap(m);
        return defaultApiUrls;
    }
}
