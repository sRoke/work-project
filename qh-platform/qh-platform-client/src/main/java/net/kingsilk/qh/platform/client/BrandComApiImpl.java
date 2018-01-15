package net.kingsilk.qh.platform.client;

import net.kingsilk.qh.platform.api.*;
import net.kingsilk.qh.platform.api.brandCom.*;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComAddReq;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComGetResp;
import net.kingsilk.qh.platform.api.brandCom.dto.BrandComUpdateReq;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

import java.net.*;
import java.util.*;

public class BrandComApiImpl extends AbstractApi implements BrandComApi {

    public static final String API_URL_add = "http://localhost:10200/platform/rs/api/brandCom";
    public static final String API_URL_del = "http://localhost:10200/platform/rs/api/brandCom/{brandComId}";

    private final Map<String, String> defaultApiUrls;

    RestOperations restTemplate;

    public BrandComApiImpl(RestOperations restTemplate) {

        final Map<String, String> m = new HashMap<>();
        m.put("add", API_URL_add);
        m.put("del", API_URL_del);
        defaultApiUrls = Collections.unmodifiableMap(m);

        this.restTemplate = restTemplate;
    }


    @Override
    public UniResp<String> add(BrandComAddReq brandComAddReq) {
        String apiUrl = getApiUrl("add");
        URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .build()
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity requestEntity = new RequestEntity(brandComAddReq,
                httpHeaders, null, null);

        ResponseEntity<UniResp<String>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<UniResp<String>>() {
                });

        return responseEntity.getBody();
    }


    @Override
    public UniResp<Void> del(String bandComId) {
        String url = getApiUrl("del");

        Map<String, String> pathVars = new LinkedHashMap<>();
        pathVars.put("brandComId", bandComId);

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(pathVars)
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity req = new RequestEntity(bandComId, httpHeaders, null, null);

        ResponseEntity<UniResp<Void>> resp = restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                req,
                new ParameterizedTypeReference<UniResp<Void>>() {
                });

        //Map<String,String> params = new HashMap<>();
        //params.put("id", bandComId);
        //restTemplate.delete(uri,params);
        // UniResp<Void> uniResp=new UniResp<>();
        //uniResp.setStatus(200);
        return resp.getBody();
    }


    @Override
    public UniResp<Void> update(String bandComId, BrandComUpdateReq brandComUpdateReq) {

        // url = "http://localhost:10200/brandCom/{brandComId}"
        String url = getApiUrl("update");


        Map<String, String> pathVars = new LinkedHashMap<>();
        pathVars.put("brandComId", bandComId);

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(pathVars)
                .toUri();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity requestEntity = new RequestEntity(brandComUpdateReq
                , headers
                , null
                , null);

        ResponseEntity<UniResp<Void>> resp = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<UniResp<Void>>() {
                });

        return resp.getBody();
    }


    @Override
    public UniResp<BrandComGetResp> get(String bandComId) {
        String url = getApiUrl("get");

        Map<String, String> pathVars = new LinkedHashMap<>();
        pathVars.put("brandComId", bandComId);

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(pathVars)
                .toUri();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity req = new RequestEntity(bandComId, httpHeaders, null, null);

        ResponseEntity<UniResp<BrandComGetResp>> resp = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<UniResp<BrandComGetResp>>() {
                });
        //ResponseEntity<UniResp<BrandComGetResp>> resp =restTemplate.getForEntity(uri,bandComId,
        // UniResp<BrandComGetResp>.class);
        return resp.getBody();


    }


    @Override
    public UniResp<UniPage<BrandComGetResp>> list(
            int size,
            int page,
            // { "age,desc", "name,asc" }
            List<String> sort,
            List<String> bandComIds) {

        String apiUrl = getApiUrl("list");

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        if (size > 0) {
            queryParams.set("size", Integer.toString(size));
        }
        if (page > 0) {
            queryParams.set("page", Integer.toString(page));
        }
        if (sort != null && sort.size() > 0) {
            queryParams.set("sort", sort.toArray().toString());
        }
        if (bandComIds != null && bandComIds.size() > 0) {
//            queryParams.set("bandComIds", bandComIds.toArray().toString());
            queryParams.put("bandComIds", bandComIds);
        }
        // apiUrl =  http://localhost:10200/xx/xx/9999/list?k1=v1&k2=v2
        URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParams(queryParams)
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity req = new RequestEntity(null, headers, null, null);

        //ResponseEntity<UniResp<Page<BrandComGetResp>>> un =
        //   restTemplate.getForEntity(uri,UniResp<Page<BrandComGetResp>>.class);
        ResponseEntity<UniResp<UniPage<BrandComGetResp>>> un = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<UniResp<UniPage<BrandComGetResp>>>() {
                }
        );

        return un.getBody();
    }

    @Override
    public UniResp<UniPage<BrandComGetResp>> search(Integer size, Integer page, List<String> sort, String q) {
        return null;
    }

    @Override
    public UniResp<List<Map<String, String>>> getBrandAppList(String brandAppId) {
        return null;
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        return defaultApiUrls;
    }
}
