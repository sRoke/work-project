package net.kingsilk.qh.oauth.client.impl.user.org.orgStaff;

import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffAddReq;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffGetResp;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffUpdateReq;
import net.kingsilk.qh.oauth.client.AbstractQhOAuthApi;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OrgStaffApiImpl extends AbstractQhOAuthApi implements OrgStaffApi {

    private RestOperations restTemplate;

    private String oauthUrl;

    private final Map<String, String> defaultApiUrls;

    public static final String API_URL_get = "/user/{userId}/org/{orgId}/orgStaff/{staffId}";
    public static final String API_URL_list = "/user/{userId}/org/{orgId}/orgStaff";
    public static final String API_URL_add = API_URL_list;
    public static final String API_URL_search = "/user/{userId}/org/{orgId}/orgStaff/search";
    public static final String API_URL_check = "/user/{userId}/org/{orgId}/orgStaff/check";

    public OrgStaffApiImpl(RestOperations restOperations, String oauthUrl)

    {
        final Map<String, String> map = new LinkedHashMap<>();
        map.put("get", API_URL_get);
        map.put("list", API_URL_list);
        map.put("add", API_URL_add);
        map.put("search", API_URL_search);
        map.put("check",API_URL_check);
        defaultApiUrls = Collections.unmodifiableMap(map);

        this.restTemplate = restOperations;
        this.oauthUrl = oauthUrl;
    }

    @Override
    public UniResp<String> add(String userId, String orgId, OrgStaffAddReq addReq) {

        Map<String, String> map = new LinkedHashMap<>();
        map.put("userId", userId);
        map.put("orgId", orgId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OrgStaffAddReq> httpEntity = new HttpEntity<>(addReq, headers);


        String url = oauthUrl + getApiUrl("add");

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();

        ResponseEntity<UniResp<String>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                httpEntity,
                new ParameterizedTypeReference<UniResp<String>>() {
                }
        );
        return responseEntity.getBody();
    }

    @Override
    public UniResp<Void> del(String userId, String orgId, String staffId) {
        return null;
    }

    @Override
    public UniResp<OrgStaffGetResp> get(String userId, String orgId, String staffId) {
        return null;
    }

    @Override
    public UniResp<String> check(String userId, String orgId) {

        String url = oauthUrl + getApiUrl("check");

        Map<String, String> map = new LinkedHashMap<>();
        map.put("userId", userId);
        map.put("orgId", orgId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(null, headers);

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .buildAndExpand(map)
                .toUri();

        ResponseEntity<UniResp<String>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<UniResp<String>>() {
                }
        );


        return responseEntity.getBody();
    }

    @Override
    public UniResp<Void> update(String userId, String orgId, String staffId, OrgStaffUpdateReq orgStaffUpdateReq) {
        return null;
    }

    @Override
    public UniResp<UniPage<OrgStaffGetResp>> list(int size, int page, List<String> sort, String userId, String orgId, String staffIds) {
        return null;
    }

    @Override
    public UniResp<UniPage<OrgStaffGetResp>> search(int size, int page, List<String> sort, String userId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(userId, headers);

        String url = oauthUrl + getApiUrl("search");

        MultiValueMap<String, String> mup = new LinkedMultiValueMap<>();
        if (!StringUtils.isEmpty(userId)) {
            mup.set("userId", userId);
        }
        if (page >= 0) {
            mup.set("page", Integer.toString(page));
        }
        if (size > 0) {
            mup.set("size", Integer.toString(size));
        }
        if (sort != null && !sort.isEmpty()) {
            mup.put("sort", sort);
        }


        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(mup)
                .build()
                .toUri();

        ResponseEntity<UniResp<UniPage<OrgStaffGetResp>>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                new ParameterizedTypeReference<UniResp<UniPage<OrgStaffGetResp>>>() {
                }
        );
        return responseEntity.getBody();
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        return defaultApiUrls;
    }
}
