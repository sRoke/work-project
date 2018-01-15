package net.kingsilk.qh.shop.service.service;

import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.addr.AddrAddReq;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.oauth.api.user.addr.AddrUpdateReq;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.addr.dto.AddrModel;
import net.kingsilk.qh.shop.domain.Adc;
import net.kingsilk.qh.shop.repo.AdcRepo;
import net.kingsilk.qh.shop.repo.AddressRepo;
import net.kingsilk.qh.shop.service.QhShopProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Service
public class AddrService {

    @Autowired
    private AdcRepo adcRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private net.kingsilk.qh.oauth.api.user.addr.AddrApi addrApi;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private QhShopProperties properties;

    public static List<Map<String, Object>> getAddr;

    public void addrModelAdc(AddrModel addrModel, Adc adc) {
        Optional.ofNullable(adc).ifPresent(addrAdc ->
                {
                    addrModel.setCountyNo(addrAdc.getNo());
                    addrModel.setArea(addrAdc.getName());
                    Optional.ofNullable(adc.getParent()).ifPresent(adcParent ->
                            {
                                addrModel.setCityNo(adcParent.getNo());
                                addrModel.setCity(adcParent.getName());
                                Optional.ofNullable(adcParent.getParent()).ifPresent(adcParents ->
                                        {
                                            addrModel.setProvince(adcParents.getName());
                                            addrModel.setProvinceNo(adcParents.getNo());
                                        }
                                );
                            }
                    );
                }
        );
    }

    public List<Map<String, Object>> getAddrList() {
        List<Map<String, Object>> arrayList = new ArrayList<>();
        List<Adc> adc = adcRepo.findAll();
        List<Adc> provincials = new ArrayList<>();
        adc.forEach(
                adc1 -> {
                    if (StringUtils.isEmpty(adc1.getParent())) {
                        provincials.add(adc1);
                    }
                }
        );

        provincials.forEach(
                provincial -> {
                    Map<String, Object> provincialMap = new HashMap<>();
                    List<Map<String, Object>> cityMapList = new ArrayList<>();
                    List<Adc> citys = new ArrayList<>();
                    adc.forEach(
                            adc1 -> {
                                if (!StringUtils.isEmpty(adc1.getParent())) {
                                    if (provincial.getId().equals(adc1.getParent().getId())) {
                                        citys.add(adc1);
                                    }
                                }
                            }
                    );
                    if (citys.isEmpty()) {
                        Map<String, Object> cityMap = new HashMap<>();
                        Map<String, String> countyMap = new HashMap<>();
                        List<Map<String, String>> countyMapList = new ArrayList<>();
                        countyMap.put("label", provincial.getName());
                        countyMap.put("value", provincial.getNo());
                        countyMapList.add(countyMap);
                        cityMap.put("label", provincial.getName());
                        cityMap.put("value", provincial.getNo());
                        cityMap.put("children", countyMapList);
                        cityMapList.add(cityMap);
                    } else {
                        citys.forEach(
                                city -> {
                                    Map<String, Object> cityMap = new HashMap<>();
                                    List<Map<String, String>> countyMapList = new ArrayList<>();
                                    List<Adc> countys = new ArrayList<>();
                                    adc.forEach(
                                            adc2 -> {
                                                if (!StringUtils.isEmpty(adc2.getParent())) {
                                                    if (city.getId().equals(adc2.getParent().getId())) {
                                                        countys.add(adc2);
                                                    }
                                                }
                                            }
                                    );
                                    if (countys.isEmpty()) {
                                        Map<String, String> countyMap = new HashMap<>();
                                        countyMap.put("label", city.getName());
                                        countyMap.put("value", city.getNo());
                                        countyMapList.add(countyMap);
                                    } else {
                                        countys.forEach(
                                                adc1 -> {
                                                    Map<String, String> countyMap = new HashMap<>();
                                                    countyMap.put("label", adc1.getName());
                                                    countyMap.put("value", adc1.getNo());
                                                    countyMapList.add(countyMap);
                                                });
                                    }
                                    cityMap.put("children", countyMapList);
                                    cityMap.put("label", city.getName());
                                    cityMap.put("value", city.getNo());
                                    cityMapList.add(cityMap);
                                });
                    }
                    provincialMap.put("label", provincial.getName());
                    provincialMap.put("value", provincial.getNo());
                    provincialMap.put("children", cityMapList);
                    arrayList.add(provincialMap);
                });

        return arrayList;
    }

    public String getAdcInfo(String no) {
        Adc adc = adcRepo.findOneByNo(no);
        if (adc != null) {
            String area = adc.getName();
            Adc parent = adc.getParent();
            String city = parent == null ? null : parent.getName();
            Adc parent2 = adc.getParent();
            Adc parent1 = parent2 == null ? null : parent2.getParent();
            String province = parent1 == null ? null : parent1.getName();
            String resp;
            if (!StringUtils.isEmpty(city)) {
                if (!StringUtils.isEmpty(province)) {
                    resp = province + " " + city + " " + area;
                } else {
                    resp = city + " " + area;
                }
            } else {
                resp = area;
            }
            return resp;
        } else {
            return "";
        }
    }

    public UniResp<String> add(
            String userId,
            AddrAddReq addrReq
    ) {

        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);

        String apiUrl = properties.getQhOAuth().getWap().getUrl() + "/api/user/{userId}/addr";

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<AddrAddReq> reqEntity = new HttpEntity<>(addrReq, reqHeaders);

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

    public UniResp<AddrGetResp> get(
            String userId,
            String addrId
    ) {

        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);
        pathVars.put("addrId", addrId);

        String apiUrl = properties.getQhOAuth().getWap().getUrl() + "/api/user/{userId}/addr/{addrId}";

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<Void> reqEntity = new HttpEntity<>(null, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<AddrGetResp>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<UniResp<AddrGetResp>>() {
                });

        UniResp<AddrGetResp> uniResp = respEntity.getBody();
        return uniResp;
    }

    public UniResp<Void> del(
            String userId,
            String addrId
    ) {

        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);
        pathVars.put("addrId", addrId);

        String apiUrl = properties.getQhOAuth().getWap().getUrl() + "/api/user/{userId}/addr/{addrId}";

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<Void> reqEntity = new HttpEntity<>(null, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<Void>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.DELETE,
                reqEntity,
                new ParameterizedTypeReference<UniResp<Void>>() {
                });

        UniResp<Void> uniResp = respEntity.getBody();
        return uniResp;
    }

    public UniResp<Void> update(
            String userId,
            String addrId,
            AddrUpdateReq addrReq
    ) {

        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);
        pathVars.put("addrId", addrId);

        String apiUrl = properties.getQhOAuth().getWap().getUrl() + "/api/user/{userId}/addr/{addrId}";

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<AddrUpdateReq> reqEntity = new HttpEntity<>(addrReq, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<Void>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.PATCH,
                reqEntity,
                new ParameterizedTypeReference<UniResp<Void>>() {
                });

        UniResp<Void> uniResp = respEntity.getBody();
        return uniResp;
    }

    public UniResp<UniPage<AddrGetResp>> list(
            int size,
            int page,
            List<String> sort,
            String userId,
            List<String> addrIds
    ) {
        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);


        String apiUrl = properties.getQhOAuth().getWap().getUrl() + "/api/user/{userId}/addr";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        if (page >= 0) {
            queryParams.set("page", Integer.toString(page));
        }
        if (size > 0) {
            queryParams.set("size", Integer.toString(size));
        }
        if (sort != null && !sort.isEmpty()) {
            queryParams.put("sort", sort);
        }
        if (addrIds != null && !addrIds.isEmpty()) {
            queryParams.put("addrIds", addrIds);
        }

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParams(queryParams)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        //
        HttpEntity<Void> reqEntity = new HttpEntity<>(null, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<UniPage<AddrGetResp>>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                reqEntity,
                new ParameterizedTypeReference<UniResp<UniPage<AddrGetResp>>>() {
                });

        UniResp<UniPage<AddrGetResp>> uniResp = respEntity.getBody();
        return uniResp;
    }


    public UniResp<AddrGetResp> getDefault(String userId, String addrType) {
        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);

        String apiUrl = properties.getQhOAuth().getWap().getUrl() + "/api/user/{userId}/addr/default";

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("addrType", addrType)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(addrType, headers);

        ResponseEntity<UniResp<AddrGetResp>> responseEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.GET,
                req,
                new ParameterizedTypeReference<UniResp<AddrGetResp>>() {
                }
        );

        UniResp<AddrGetResp> uniResp = responseEntity.getBody();
        return uniResp;
    }


    public UniResp<Void> setDefault(String userId, String addrId, String addrType) {
        Map<String, Object> pathVars = new HashMap<>();
        pathVars.put("userId", userId);
        pathVars.put("addrId", addrId);
        String apiUrl = properties.getQhOAuth().getWap().getUrl() + "/api/user/{userId}/addr/default/{addrId}";

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.set("addrType", addrType);

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParams(queryParams)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> req = new HttpEntity<>(addrType, headers);

        ResponseEntity<UniResp<Void>> responseEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.PUT,
                req,
                new ParameterizedTypeReference<UniResp<Void>>() {
                }
        );

        UniResp<Void> uniResp = responseEntity.getBody();
        return uniResp;
    }
}
