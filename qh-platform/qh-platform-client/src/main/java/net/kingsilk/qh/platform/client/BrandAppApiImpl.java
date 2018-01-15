package net.kingsilk.qh.platform.client;

import net.kingsilk.qh.platform.api.UniPage;
import net.kingsilk.qh.platform.api.UniResp;
import net.kingsilk.qh.platform.api.brandApp.BrandAppApi;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppPageReq;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppReq;
import net.kingsilk.qh.platform.api.brandApp.dto.BrandAppResp;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BrandAppApiImpl extends AbstractApi implements BrandAppApi {

    final String API_URL_base
            = "/brandApp/{id}";

    RestOperations restTemplate;

    private String platformUrl;

    public BrandAppApiImpl(RestOperations restTemplate, String platformUrl) {
        this.restTemplate = restTemplate;
        this.platformUrl = platformUrl;
    }

    @Override
    public UniResp<String> add(BrandAppReq brandAppReq) {
        return null;
    }

    @Override
    public UniResp<BrandAppResp> info(String id) {
        String url = platformUrl + getApiUrl("base");
        Map<String, String> varmp = new LinkedHashMap<>();
        varmp.put("id", id);
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(varmp)
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity requestEntity = new RequestEntity(
                null, headers, null, null);
        ResponseEntity<UniResp<BrandAppResp>> un = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<BrandAppResp>>() {
                });
        return un.getBody();
    }

    @Override
    public UniResp<UniPage<BrandAppResp>> page(BrandAppPageReq brandAppPageReq) {
        return null;
    }

    @Override
    public UniResp<String> del(String id) {
        return null;
    }

    @Override
    public UniResp<String> wxMpId(String brandAppId, String wxMpId) {
        return null;
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        final Map<String, String> m = new HashMap<>();
        m.put("base", API_URL_base);
        final Map<String, String> defaultApiUrls = Collections.unmodifiableMap(m);
        return defaultApiUrls;
    }
}
