package net.kingsilk.qh.agency.client;

import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemMinInfo;
import net.kingsilk.qh.agency.api.brandApp.item.dto.ItemSearchReq;
import net.kingsilk.qh.agency.api.brandApp.partner.skuStore.SkuStoreApi;
import net.kingsilk.qh.agency.api.brandApp.partner.skuStore.dto.SkuStoreInfoResp;
import net.kingsilk.qh.agency.api.brandApp.partner.skuStore.dto.SkuStorePageReq;
import net.kingsilk.qh.agency.api.brandApp.partner.skuStore.dto.SkuStorePageResp;
import net.kingsilk.qh.agency.api.common.dto.SkuInfoModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by lit on 17/8/24.
 */
@Component
public class SkuStoreApiImpl extends AbstractApi implements SkuStoreApi {

    final String API_URL_PAGE
            = "/brandApp/{brandAppId}/partner/{partnerId}/skuStore";


    final String API_URL_INFO
            = "/brandApp/{brandAppId}/partner/{partnerId}/skuStore/{id}";


    final String API_URL_SKU
            = "/brandApp/{brandAppId}/partner/{partnerId}/skuStore/skuDetail";


    final String API_URL_SEARCH
            = "/brandApp/{brandAppId}/partner/{partnerId}/skuStore/search";

    RestOperations restTemplate;
    String agencyUrl;

    public SkuStoreApiImpl(RestOperations restTemplate, String agencyUrl) {
        this.restTemplate = restTemplate;
        this.agencyUrl = agencyUrl;
    }

    @Override
    public UniResp<SkuStoreInfoResp> info(
            String brandAppId,
            String partnerId,
            String id) {

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);

        String url = agencyUrl + getApiUrl("info");

        //构建请求参数
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", brandAppId);
        map.put("partnerId", partnerId);
        map.put("id", id);
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();

        //发送请求
        ResponseEntity<UniResp<SkuStoreInfoResp>> resp = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<SkuStoreInfoResp>>() {
                });

        return resp.getBody();
    }

    @Override
    public UniResp<UniPageResp<SkuStorePageResp>> page(
            String brandAppId,
            String partnerId,
            SkuStorePageReq req) {

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SkuStorePageReq> requestEntity = new HttpEntity<>(req, headers);

        //构建请求path参数
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", brandAppId);
        map.put("partnerId", partnerId);

        //构建url后面的参数
        MultiValueMap<String, String> mup = new LinkedMultiValueMap<>();
        if (!StringUtils.isEmpty(req.getKeyWord())) {
            mup.set("keyWord", req.getKeyWord());
        }
        if (!StringUtils.isEmpty(req.getType())) {
            mup.set("type", req.getType());
        }
        if (!StringUtils.isEmpty(req.getCategoryId())) {
            mup.set("categoryId", req.getCategoryId());
        }
        String url = agencyUrl + getApiUrl("page");
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(mup)
                .buildAndExpand(map)
                .toUri();

        //发送请求
        ResponseEntity<UniResp<UniPageResp<SkuStorePageResp>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<UniPageResp<SkuStorePageResp>>>() {
                });
        return responseEntity.getBody();
    }


    @Override
    public UniResp<UniPageResp<ItemMinInfo>> searchSkuStore(
            String brandAppId,
            String partnerId,
            ItemSearchReq req) {

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ItemSearchReq> requestEntity = new HttpEntity<>(req, headers);


        //构建请求path参数
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", brandAppId);
        map.put("partnerId", partnerId);

        //构建请求url后面的参数
        MultiValueMap<String, String> mup = new LinkedMultiValueMap<>();
        if (!StringUtils.isEmpty(req.getKeyWord())) {
            mup.set("keyWord", req.getKeyWord());
        }
        if (!StringUtils.isEmpty(req.getType())) {
            mup.set("type", req.getType());
        }
        if (!StringUtils.isEmpty(req.getCategoryId())) {
            mup.set("categoryId", req.getCategoryId());
        }
        String url = agencyUrl + getApiUrl("search");
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(mup)
                .buildAndExpand(map)
                .toUri();

        //发送请求
        ResponseEntity<UniResp<UniPageResp<ItemMinInfo>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<UniPageResp<ItemMinInfo>>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public UniResp<SkuInfoModel> skuDetail(
            String brandAppId,
            String partnerId,
            String skuId,
            String code) {

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);

        String url = agencyUrl + getApiUrl("sku");

        //构建请求path参数
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", brandAppId);
        map.put("partnerId", partnerId);

        //构建请求url后面的参数
        MultiValueMap<String, String> mup = new LinkedMultiValueMap<>();
        if (!StringUtils.isEmpty(skuId)) {
            mup.set("skuId", skuId);
        }
        if (!StringUtils.isEmpty(code)) {
            mup.set("code", code);
        }
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(mup)
                .buildAndExpand(map)
                .toUri();

        //发送请求
        ResponseEntity<UniResp<SkuInfoModel>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<SkuInfoModel>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        final Map<String, String> m = new HashMap<>();
        m.put("page", API_URL_PAGE);
        m.put("info", API_URL_INFO);
        m.put("sku", API_URL_SKU);
        m.put("search", API_URL_SEARCH);
        final Map<String, String> defaultApiUrls = Collections.unmodifiableMap(m);
        return defaultApiUrls;
    }
}
