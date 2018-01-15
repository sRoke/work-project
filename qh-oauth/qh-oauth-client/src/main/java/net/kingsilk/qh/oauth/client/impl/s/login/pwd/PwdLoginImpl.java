package net.kingsilk.qh.oauth.client.impl.s.login.pwd;

import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.login.*;
import net.kingsilk.qh.oauth.api.s.login.pwd.*;
import net.kingsilk.qh.oauth.client.impl.s.*;
import org.springframework.core.*;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

import java.net.*;
import java.util.*;

public class PwdLoginImpl extends AbstractQhOAuthStatefulApi implements PwdLoginApi {

    private final Map<String, String> defaultApiUrls;

    private RestOperations restTemplate;
    private String oauthUrl;


    public PwdLoginImpl(RestOperations restOperations, String oauthUrl) {
        this.restTemplate = restOperations;
        this.oauthUrl = oauthUrl;
        final Map<String, String> map = new LinkedHashMap<>();
        map.put("login", oauthUrl + API_PATH_login);
        defaultApiUrls = Collections.unmodifiableMap(map);
    }

    @Override
    public Map<String, String> getDefaultApiUrls() {
        return defaultApiUrls;
    }

    @Override
    public UniResp<LoginResp> login(
            String username,
            String password
    ) {

        // 构造请求URL
        String apiUrl = getApiUrl("login");
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
        reqFrom.set("username", username);
        reqFrom.set("password", password);
        HttpEntity<MultiValueMap<String, String>> reqEntity = new HttpEntity<>(reqFrom, reqHeaders);

        // 发送请求
        ResponseEntity<UniResp<LoginResp>> respEntity = restTemplate.exchange(
                apiUri,
                HttpMethod.POST,
                reqEntity,
                new ParameterizedTypeReference<UniResp<LoginResp>>() {
                }
        );

        Assert.isTrue(respEntity.getStatusCode().is2xxSuccessful(),
                "调用 qh-oauth API 出错。响应状态码是 : " + respEntity.getStatusCode());

        UniResp<LoginResp> uniResp = respEntity.getBody();

        Assert.isTrue(uniResp != null,
                "调用 qh-oauth API 出错。响应内容为空");

        return uniResp;

    }

}
