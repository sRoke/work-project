package net.kingsilk.qh.shop.server.controller;


import net.kingsilk.qh.shop.server.QhShopServerApp;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({"default", "base", "dev", "ut"})
@SpringBootTest(
        classes = {BaseTest.UtApp.class, QhShopServerApp.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
public abstract class BaseTest {

    public final String logPrefix = "************************";
    public final Logger log = LoggerFactory.getLogger(getClass());

    //final String brandAppId=5a0e724c46e0fb00082d605d
    //final String shopId=5a0e72e71794266d27a4eb08

    @Autowired
    public EmbeddedWebApplicationContext server;

    @LocalServerPort
    public int port;

    @Autowired
    public MongoTemplate mongoTemplate;

    @Autowired
    public QhShopUtProperties qhShopProperties;


    public TestRestTemplate restTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);

    @Configuration
    @EnableConfigurationProperties
    public static class UtApp {

        @Bean
        QhShopUtProperties qhOAuthUtProperties() {
            return new QhShopUtProperties();
        }

    }


    /**
     * auth2登录。
     */
    public void auth2Login(String username, String password) {
        log.debug(logPrefix + "auth2Login");

        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.ALL);
        headers.setAccept(mediaTypeList);

        MultiValueMap reqMsg = new LinkedMultiValueMap();
        reqMsg.put("username", username);
        reqMsg.put("password", password);

        HttpEntity<?> reqEntity = new HttpEntity<>(reqMsg, headers);

        String url = qhShopProperties.getOauth().getWap().getUrl() + "/login";
        ResponseEntity<String> respEntity = restTemplate.exchange(url,
                HttpMethod.POST, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        URI uri = respEntity.getHeaders().getLocation();
        assertThat(uri.toString()).startsWith(qhShopProperties.getOauth().getWap().getUrl());
    }


    /**
     * auth2授权。
     * <code>
     * GET /oauth/authorize
     * ?response_type=token
     * &client_id=xxx
     * &redirect_uri=xxx
     * &scope=xxx
     * &state=xxx
     * </code>
     */
    public Map<String, String> auth2ImplicitAuthorize(
            String clientId,
            String redirectUri,
            String scope
    ) {
        log.debug(logPrefix + "auth2ImplicitAuthorize");

        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.ALL);
        headers.setAccept(mediaTypeList);


        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String state = UUID.randomUUID().toString();

        String path = UriComponentsBuilder.fromHttpUrl(qhShopProperties.getOauth().getWap().getUrl() + "/oauth/authorize")
                .queryParam("response_type", "token")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .queryParam("state", state)
                .build()
                .toUri()
                .toString();


        ResponseEntity<Void> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, Void.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        URI uri = respEntity.getHeaders().getLocation();
        assertThat(uri.toString()).startsWith(redirectUri + "#");
        assertThat(!StringUtils.isEmpty(uri.getFragment()));

        Map<String, String> tokenInfos = UriComponentsBuilder.newInstance()
                .query(uri.getFragment())
                .build()
                .getQueryParams()
                .toSingleValueMap();

        assertThat(tokenInfos)
                .containsKey("access_token")
                .containsEntry("token_type", "bearer")
                .containsEntry("state", state)
                .containsKey("expires_in")
                .containsKey("jti");

        return tokenInfos;
    }


    public ResponseEntity<String> requestGet(String url) {
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.ALL);
        headers.setAccept(mediaTypeList);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString();
        HttpEntity<String> reqEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, String.class);
        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        return respEntity;
    }

    public ResponseEntity<String> requestPost(String url, Object jsonObj) {
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.ALL);
        headers.setAccept(mediaTypeList);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString();
        HttpEntity<?> reqEntity = new HttpEntity<>(jsonObj, headers);
        ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.POST, reqEntity, String.class);
        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        return respEntity;
    }
}
