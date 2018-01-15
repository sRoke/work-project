package net.kingsilk.qh.agency.admin

import net.kingsilk.qh.agency.repo.CompanyRepo
import net.kingsilk.qh.agency.server.QhAgencyServerApp
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder

import static org.assertj.core.api.Assertions.assertThat

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner)
@ActiveProfiles(["default", "base", "dev", "ut"])
@SpringBootTest(
        classes = [UtApp, QhAgencyServerApp],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
abstract class BaseTest {

    final String logPrefix = "█" * 40 + " "
    final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    EmbeddedWebApplicationContext server;

    @LocalServerPort
    int port;

    TestRestTemplate restTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES)
//    @Autowired
//    TestRestTemplate restTemplate

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    CompanyRepo companyRepo
/*
    @Autowired
    StaffRepo staffRepo*/

    @Autowired
    QhAgencyUtProperties ut


    @Configuration
    @EnableConfigurationProperties
    static class UtApp {

        @Bean
        QhAgencyUtProperties qhOAuthUtProperties() {
            return new QhAgencyUtProperties()
        }

    }

    /**
     * auth2退出登录。
     */
    void auth2Logout() {
        log.debug(logPrefix + "auth2Logout")


        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.ALL])

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String url = ut.oauth.wap.url + "/logout";
        ResponseEntity<String> respEntity = restTemplate.exchange(url,
                HttpMethod.POST, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()

        assertThat(uri.toString()).startsWith(ut.oauth.wap.url + "/logoutSuccess".toString())
    }

    /**
     * auth2登录。
     */
    void auth2Login(String username, String password) {
        log.debug(logPrefix + "auth2Login")

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.ALL])

        MultiValueMap reqMsg = new LinkedMultiValueMap()
        reqMsg.username = username
        reqMsg.password = password

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(reqMsg, headers);

        String url = ut.oauth.wap.url + "/login";
        ResponseEntity<String> respEntity = restTemplate.exchange(url,
                HttpMethod.POST, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()
        assertThat(uri.toString()).startsWith(ut.oauth.wap.url)
    }

    /**
     * auth2授权。
     * <code>
     *     GET /oauth/authorize
     *          ?response_type=token
     *          &client_id=xxx
     *          &redirect_uri=xxx
     *          &scope=xxx
     *          &state=xxx
     * </code>
     */
    Map<String, String> auth2ImplicitAuthorize(
            String clientId,
            String redirectUri,
            String scope
    ) {
        log.debug(logPrefix + "auth2ImplicitAuthorize")

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.ALL])


        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        String state = UUID.randomUUID().toString()

        String path = UriComponentsBuilder.fromHttpUrl(ut.oauth.wap.url + "/oauth/authorize")
                .queryParam("response_type", "token")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope)
                .queryParam("state", state)
                .build()
                .toUri()
                .toString()


        ResponseEntity<Void> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, Void.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
        URI uri = respEntity.headers.getLocation()
        assertThat(uri.toString()).startsWith(redirectUri + "#")
        assertThat(uri.getFragment())

        Map<String, String> tokenInfos = UriComponentsBuilder.newInstance()
                .query(uri.getFragment())
                .build()
                .getQueryParams()
                .toSingleValueMap()

        assertThat(tokenInfos)
                .containsKey("access_token")
                .containsEntry("token_type", "bearer")
                .containsEntry("state", state)
                .containsKey("expires_in")
                .containsKey("jti")

        return tokenInfos
    }

    ResponseEntity<String> requestGet(String url) {
        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()
        HttpEntity<String> reqEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, String.class);
        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK)
        return respEntity
    }

    ResponseEntity<String> requestPost(url, jsonObj) {
        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()
        HttpEntity<String> reqEntity = new HttpEntity<String>(jsonObj, headers);
        ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.POST, reqEntity, String.class);
        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK)
        return respEntity
    }

//
//    @Test
//    public void htmlSso01() {
//
//        TestRestTemplate clientRestTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
//        TestRestTemplate authRestTemplate = new TestRestTemplate(TestRestTemplate.HttpClientOption.ENABLE_COOKIES);
//
//        // "GET http://s.localhost:10004/login" —— 尚未登录
//        log.debug(logPrefix + "002")
//        URI authUri = client_localLoginWithoutSso(clientRestTemplate, clientLoginUri)
//
//        // "GET http://a.localhost:10004/oauth/authorize" —— 尚未登录
//        log.debug(logPrefix + "003")
//        URI authLoginUri = auth_authWithoutLogin(authRestTemplate, authUri)
//
//        // 省略了一步 ： 让显示 登录表单画面的请求
//        // "GET http://a.localhost:10004/login" —— 尚未登录
//
//        // "POST http://a.localhost:10004/oauth/authorize" —— 已经登录
//        log.debug(logPrefix + "004")
//        URI authUriWithLogin = auth_login(authRestTemplate, authLoginUri)
//
//        log.debug(logPrefix + "005")
//        URI clientLoginUriWithSso = auth_authWithLoginAndAutoAuth(authRestTemplate, authUriWithLogin)
//
//        // "GET http://a.localhost:10004/login" —— 已经登录
//        log.debug(logPrefix + "006")
//        URI secWithLocalLogin = client_localLoginWithSso(clientRestTemplate, clientLoginUriWithSso)
//
//        // "GET http://s.localhost:10004/sec" —— 已经登录
//        log.debug(logPrefix + "007")
//        client_accessSecWithLocalLogin(clientRestTemplate, secWithLocalLogin)
//    }
//
//    /** Client App : 第一次访问需登录的路径（此时尚未登录） */
//    private URI client_accessSecWithoutLocalLogin(TestRestTemplate restTemplate) {
//        log.debug(logPrefix + "client_accessSecWithoutLocalLogin")
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.ALL])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        String path = UriComponentsBuilder.fromHttpUrl("${clientUrl}/sec")
//                .build()
//                .toUri()
//                .toString()
//
//        ResponseEntity<String> respEntity = restTemplate.exchange(path,
//                HttpMethod.GET, reqEntity, String.class);
//
//        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
//        URI uri = respEntity.headers.getLocation()
//
//        assertThat(uri.toString()).startsWith("${clientUrl}/login".toString())
//        MultiValueMap<String, String> decodedQueryParams = UriComponentsBuilder.newInstance()
//                .query(uri.getQuery())
//                .build()
//                .getQueryParams()
//        assertThat(decodedQueryParams)
//                .isEmpty()
//        return uri
//    }
//    /** Client App : 第一次访问需登录的路径（此时尚未SSO） */
//    private URI client_localLoginWithoutSso(TestRestTemplate restTemplate, URI loginUri) {
//        log.debug(logPrefix + "client_localLoginWithoutSso")
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.ALL])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//
//        ResponseEntity<String> respEntity = restTemplate.exchange(loginUri,
//                HttpMethod.GET, reqEntity, String.class);
//
//        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
//        URI uri = respEntity.headers.getLocation()
//
//        // "http://a.localhost:10001/oauth/authorize
//        // ?client_id=MY_CLIENT
//        // &redirect_uri=http://s.localhost:10004/login
//        // &response_type=code
//        // &scope=LOGIN&state=IugS4H"
//        assertThat(uri.toString()).startsWith("${authUrl}/oauth/authorize".toString())
//        MultiValueMap<String, String> decodedQueryParams = UriComponentsBuilder.newInstance()
//                .query(uri.getQuery())
//                .build()
//                .getQueryParams()
//        assertThat(decodedQueryParams)
//                .containsEntry("client_id", [myOAuth2Props.client.id])
//                .containsEntry("redirect_uri", ["${clientUrl}/login".toString()])
//                .containsEntry("response_type", ["code"])
//                .containsEntry("scope", ["LOGIN"])
//                .containsKeys("state")
//        return uri
//    }
//
//    /** Authorization Server : 进行授权（但是此时尚未登录） */
//    private URI auth_authWithoutLogin(TestRestTemplate restTemplate, URI authUri) {
//        log.debug(logPrefix + "auth_authWithoutLogin")
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        ResponseEntity<String> respEntity = restTemplate.exchange(authUri,
//                HttpMethod.GET, reqEntity, String.class);
//
//        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
//        URI uri = respEntity.headers.getLocation()
//        assertThat(uri.toString()).isEqualTo("${authUrl}/login".toString())
//        return uri
//    }
//
//    /** Authorization Server : 登录 */
//    private URI auth_login(TestRestTemplate restTemplate, URI authLoginUri) {
//        log.debug(logPrefix + "auth_login")
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
//
//        MultiValueMap reqMsg = new LinkedMultiValueMap()
//        reqMsg.username = "a_admin"
//        reqMsg.password = "a_admin"
//
//        HttpEntity reqEntity = new HttpEntity(reqMsg, headers);
//
//        ResponseEntity<String> respEntity = restTemplate.exchange(authLoginUri,
//                HttpMethod.POST, reqEntity, String.class);
//
//        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
//        URI uri = respEntity.headers.getLocation()
//        assertThat(uri.toString()).startsWith("${authUrl}/oauth/authorize".toString())
//
//        MultiValueMap<String, String> decodedQueryParams = UriComponentsBuilder.newInstance()
//                .query(uri.getQuery())
//                .build()
//                .getQueryParams()
//        assertThat(decodedQueryParams)
//                .containsEntry("client_id", [myOAuth2Props.client.id])
//                .containsEntry("redirect_uri", ["${clientUrl}/login".toString()])
//                .containsEntry("response_type", ["code"])
//                .containsEntry("scope", ["LOGIN"])
//                .containsKeys("state")
//
//        return uri
//    }
//
//    /** Authorization Server : 再次访问授权网址（会自动授权） */
//    private URI auth_authWithLoginAndAutoAuth(TestRestTemplate restTemplate, URI authUri) {
//        log.debug(logPrefix + "auth_authWithLoginAndAutoAuth")
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.TEXT_HTML])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        ResponseEntity<String> respEntity = restTemplate.exchange(authUri,
//                HttpMethod.GET, reqEntity, String.class);
//
//        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
//        URI uri = respEntity.headers.getLocation()
//        assertThat(uri.toString()).startsWith("${clientUrl}/login".toString())
//
//        MultiValueMap<String, String> decodedQueryParams = UriComponentsBuilder.newInstance()
//                .query(uri.getQuery())
//                .build()
//                .getQueryParams()
//        assertThat(decodedQueryParams)
//                .containsKeys("code")
//                .containsKeys("state")
//
//        return uri
//
//    }
//
//    /** Client App : 第二次访问需登录的路径（此时已经SSO） */
//    private URI client_localLoginWithSso(TestRestTemplate restTemplate, URI loginUri) {
//        log.debug(logPrefix + "client_localLoginWithoutSso")
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.ALL])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//
//        ResponseEntity<String> respEntity = restTemplate.exchange(loginUri,
//                HttpMethod.GET, reqEntity, String.class);
//
//        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND)
//        URI uri = respEntity.headers.getLocation()
//
//        assertThat(uri.toString()).startsWith("${clientUrl}/sec".toString())
//        MultiValueMap<String, String> decodedQueryParams = UriComponentsBuilder.newInstance()
//                .query(uri.getQuery())
//                .build()
//                .getQueryParams()
//        assertThat(decodedQueryParams)
//                .isEmpty()
//
//        return uri
//    }
//
//    /** Client App : 第二次访问需登录的路径（此时已经登录） */
//    private void client_accessSecWithLocalLogin(TestRestTemplate restTemplate, URI secUri) {
//        log.debug(logPrefix + "client_accessSecWithLocalLogin")
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept([MediaType.ALL])
//
//        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);
//
//        ResponseEntity<String> respEntity = restTemplate.exchange(secUri,
//                HttpMethod.GET, reqEntity, String.class);
//
//        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK)
//        assertThat(respEntity.body).contains("client sso sec");
//        log.debug(respEntity.body)
//    }


}
