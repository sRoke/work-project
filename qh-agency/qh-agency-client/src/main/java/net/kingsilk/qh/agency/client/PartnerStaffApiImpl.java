package net.kingsilk.qh.agency.client;

import net.kingsilk.qh.agency.api.UniPageResp;
import net.kingsilk.qh.agency.api.UniResp;

import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto.PartnerStaffInfoResp;
import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto.PartnerStaffPageReq;
import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto.PartnerStaffSaveReq;

import net.kingsilk.qh.agency.api.brandApp.partnerStaff.PartnerStaffApi;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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


public class PartnerStaffApiImpl extends AbstractApi implements PartnerStaffApi {

    final String API_URL_PAGE
            = "/brandApp/{brandAppId}/partner/{partnerId}/partnerStaff";

    final String API_URL_INFO
            = "/brandApp/{brandAppId}/partner/{partnerId}/partnerStaff/{id}";

    final String API_URL_FIND
            = "/brandApp/{brandAppId}/partner/{partnerId}/partnerStaff/find/{userId}";
    RestOperations restTemplate;
    String agencyUrl;

    public PartnerStaffApiImpl(RestOperations restTemplate, String agencyUrl) {
        this.restTemplate = restTemplate;
        this.agencyUrl = agencyUrl;
    }


    @Override
    public UniResp<PartnerStaffInfoResp> info(
            String brandAppId,
            String id) {

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);

        //构建url参数
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", brandAppId);
        map.put("id", id);

        String url = agencyUrl + getApiUrl("info");
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();

        //发送请求
        ResponseEntity<UniResp<PartnerStaffInfoResp>> infoResp = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<PartnerStaffInfoResp>>() {
                });

        return infoResp.getBody();
    }


    @Override
    public UniResp<String> update(
            String brandAppId,
            String id,
            PartnerStaffSaveReq req) {
        //TODO 暂时没有到
        throw new RuntimeException("error");
    }

    @Override
    public UniResp<UniPageResp<PartnerStaffInfoResp>> page(
            String brandAppId,
            PartnerStaffPageReq partnerStaffPageReq) {

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PartnerStaffPageReq> requestEntity = new HttpEntity<>(partnerStaffPageReq, headers);

        //构建请求Path参数
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", brandAppId);
        String url = agencyUrl + getApiUrl("page");

        //构建请求url后面的参数
        MultiValueMap<String, String> mup = new LinkedMultiValueMap<>();
        if (!StringUtils.isEmpty(partnerStaffPageReq.getKeyWord())) {
            mup.set("keyWord", partnerStaffPageReq.getKeyWord());
        }
        if (!StringUtils.isEmpty(partnerStaffPageReq.getStartDate())) {
            mup.set("startDate", partnerStaffPageReq.getStartDate());
        }
        if (!StringUtils.isEmpty(partnerStaffPageReq.getEndDate())) {
            mup.set("endDate", partnerStaffPageReq.getEndDate());
        }
        if (partnerStaffPageReq.getIdList() != null && partnerStaffPageReq.getIdList().size() > 0) {
            mup.put("idList", partnerStaffPageReq.getIdList());
        }
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(mup)
                .buildAndExpand(map)
                .toUri();

        //发送请求
        ResponseEntity<UniResp<UniPageResp<PartnerStaffInfoResp>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<UniPageResp<PartnerStaffInfoResp>>>() {
                });
        return responseEntity.getBody();
    }

    @Override
    public UniResp<String> enable(
            String brandAppId,
            String id,
            Boolean disabled) {
        //TODO 暂时没用到
        throw new RuntimeException("error");
    }

    @Override
    public UniResp<PartnerStaffInfoResp> find(
            String brandAppId,
            String userId) {

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestEntity = new HttpEntity<>(null, headers);

        //构建请求path参数
        Map<String, String> map = new LinkedHashMap<>();
        map.put("brandAppId", brandAppId);
        map.put("userId", userId);
        String url = agencyUrl + getApiUrl("find");
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();
        ResponseEntity<UniResp<PartnerStaffInfoResp>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<UniResp<PartnerStaffInfoResp>>() {
                }
        );
        return responseEntity.getBody();
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        final Map<String, String> m = new HashMap<>();
        m.put("page", API_URL_PAGE);
        m.put("info", API_URL_INFO);
        m.put("find", API_URL_FIND);
        final Map<String, String> defaultApiUrls = Collections.unmodifiableMap(m);
        return defaultApiUrls;
    }
}
