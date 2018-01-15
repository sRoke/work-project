package net.kingsilk.qh.platform.client;

import net.kingsilk.qh.platform.api.*;
import net.kingsilk.qh.platform.api.brand.*;
import net.kingsilk.qh.platform.api.brand.dto.BrandAddReq;
import net.kingsilk.qh.platform.api.brand.dto.BrandGetResp;
import net.kingsilk.qh.platform.api.brand.dto.BrandUpdateReq;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

import java.net.*;
import java.util.*;

public class BrandApiImpl extends AbstractApi implements BrandApi {

    final String API_URL_base
            = "http://localhost:10200/platform/rs/api/brandComp/{bandComId}/brand";

    final String API_URL_base2
            = "http://localhost:10200/platform/rs/api/brandComp/{bandComId}/brand/{bandId}";

    RestOperations restTemplate;

    public BrandApiImpl(RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UniResp<String> add(BrandAddReq brandAddReq) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity requestEntity = new RequestEntity(brandAddReq,
                httpHeaders, null, null);
        Map<String, String> varmp = new LinkedHashMap<>();
        varmp.put("bandComId", brandAddReq.getBrandComId());

        String url = getApiUrl("base");
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

    @Override
    public UniResp<Void> del(String bandComId, String bandId) {
        String url = getApiUrl("base2");
        Map<String, String> varmp = new LinkedHashMap<>();
        varmp.put("bandComId", bandComId);
        varmp.put("bandId", bandId);
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(varmp)
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity requestEntity =
                new RequestEntity(null, headers, null, null);
        ResponseEntity<UniResp<Void>> un = restTemplate.exchange(
                uri,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<UniResp<Void>>() {
                });
        return un.getBody();
    }

    @Override
    public UniResp<Void> update(
            String bandComId,
            String bandId,
            BrandUpdateReq brandUpdateReq) {
        String url = getApiUrl("base2");
        Map<String, String> varmp = new LinkedHashMap<>();
        varmp.put("bandComId", bandComId);
        varmp.put("bandId", bandId);
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(varmp)
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity requestEntity = new RequestEntity(
                brandUpdateReq, headers, null, null);
        ResponseEntity<UniResp<Void>> un = restTemplate.exchange(
                uri,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<UniResp<Void>>() {
                });
        return un.getBody();
    }

    @Override
    public UniResp<BrandGetResp> get(String bandComId, String bandId) {
        String url = getApiUrl("base2");
        Map<String, String> varmp = new LinkedHashMap<>();
        varmp.put("bandComId", bandComId);
        varmp.put("bandId", bandId);
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(varmp)
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity requestEntity = new RequestEntity(
                null, headers, null, null);
        ResponseEntity<UniResp<BrandGetResp>> un = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<BrandGetResp>>() {
                });
        return un.getBody();
    }

    @Override
    public UniResp<UniPage<BrandGetResp>> list(
            int size,
            int page,
            List<String> sort,
            String bandComId,
            List<String> bandIds) {
        String url = getApiUrl("base");
        MultiValueMap<String, String> mup = new LinkedMultiValueMap<>();
        if (size > 0) {
            mup.set("size", Integer.toString(size));
        }
        if (page > 0) {
            mup.set("page", Integer.toString(page));
        }
        if (sort != null && sort.size() > 0) {
            mup.put("sort", sort);
        }
        if (bandIds != null && bandIds.size() > 0) {
            mup.put("bandIds", bandIds);
        }
        Map<String, String> mp = new LinkedHashMap<>();
        mp.put("bandComId", bandComId);
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(mup)
                .buildAndExpand(mp)
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity req = new RequestEntity(null, headers, null, null);

        ResponseEntity<UniResp<UniPage<BrandGetResp>>> resp = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<UniResp<UniPage<BrandGetResp>>>() {
                });
        return resp.getBody();


    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        final Map<String, String> m = new HashMap<>();
        m.put("base", API_URL_base);
        m.put("base2", API_URL_base2);
        final Map<String, String> defaultApiUrls = Collections.unmodifiableMap(m);
        return defaultApiUrls;
    }
}
