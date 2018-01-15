package net.kingsilk.qh.agency.client.partner;

import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.api.brandApp.addr.dto.AddrSaveReq;
import net.kingsilk.qh.agency.api.brandApp.partner.sysConf.SysConfApi;
import net.kingsilk.qh.agency.api.brandApp.sysConf.dto.SysConfInfoResp;
import net.kingsilk.qh.agency.client.AbstractApi;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component(value = "sysConf")
public class SysConfApiImpl extends AbstractApi implements SysConfApi {

    String agencyUrl;
    RestOperations restTemplate;

    final String API_URL_SYS
            = "/brandApp/{brandAppId}/partner/{partnerId}/sysConf/{id}";

    public SysConfApiImpl(RestOperations restTemplate, String agencyUrl) {
        this.restTemplate = restTemplate;
        this.agencyUrl = agencyUrl;
    }

    @Override
    public UniResp<SysConfInfoResp> info(String brandAppId, String id) {
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);

        String url = agencyUrl + getApiUrl("info");

        //构建请求path参数
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", brandAppId);
        map.put("partnerId","111111111");
        map.put("id", id);

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();
        //发送请求
        ResponseEntity<UniResp<SysConfInfoResp>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<SysConfInfoResp>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public UniResp<String> setBrandAppAddr(String brandAppId, String partnerId, AddrSaveReq req) {
        return null;
    }

    @Override
    public UniResp<String> judgeBrandAppAddr(String brandAppId, String partnerId) {
        return null;
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        final Map<String, String> m = new HashMap<>();
        m.put("info", API_URL_SYS);
        final Map<String, String> defaultApiUrls = Collections.unmodifiableMap(m);
        return defaultApiUrls;
    }

}
