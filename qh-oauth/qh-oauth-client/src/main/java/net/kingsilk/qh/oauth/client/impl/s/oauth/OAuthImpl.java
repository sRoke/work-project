package net.kingsilk.qh.oauth.client.impl.s.oauth;

import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.oauth.*;
import net.kingsilk.qh.oauth.client.impl.s.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

import java.net.*;
import java.util.*;

public class OAuthImpl extends AbstractQhOAuthStatefulApi implements OAuthApi {

    private final Map<String, String> defaultApiUrls;
    private RestOperations restTemplate;
    private String oauthUrl;


    public OAuthImpl(RestOperations restOperations, String oauthUrl) {
        this.restTemplate = restOperations;
        this.oauthUrl = oauthUrl;
        final Map<String, String> map = new LinkedHashMap<>();
        map.put("authorize", oauthUrl + API_PATH_authorize);
        defaultApiUrls = Collections.unmodifiableMap(map);

    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        return defaultApiUrls;
    }

    @Override
    public UniResp<AuthorizeResp> authorize(
            String responseType,
            String clientId,
            String redirectUri,
            String scope,
            String state
    ) {
        // 构造请求URL
        String apiUrl = getApiUrl("authorize");
        Map<String, Object> pathVars = new HashMap<>();

        URI apiUri = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .buildAndExpand(pathVars)
                .encode()
                .toUri();

        // 设置请求头
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        reqHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));

        // 构造请求参数

        MultiValueMap<String, String> reqFrom = new LinkedMultiValueMap<>();
        reqFrom.set("response_type", responseType);
        reqFrom.set("client_id", clientId);
        reqFrom.set("redirect_uri", redirectUri);
        reqFrom.set("scope", scope);
        reqFrom.set("state", state);
        HttpEntity<MultiValueMap<String, String>> reqEntity = new HttpEntity<>(reqFrom, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<AuthorizeResp>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.POST,
                reqEntity,
                new ParameterizedTypeReference<UniResp<AuthorizeResp>>() {
                }
        );

        Assert.isTrue(respEntity.getStatusCode().is2xxSuccessful(),
                "调用 qh-oauth API 出错。响应状态码是 : " + respEntity.getStatusCode());

        UniResp<AuthorizeResp> uniResp = respEntity.getBody();

        Assert.isTrue(uniResp != null,
                "调用 qh-oauth API 出错。响应内容为空");

        return uniResp;
    }

}
