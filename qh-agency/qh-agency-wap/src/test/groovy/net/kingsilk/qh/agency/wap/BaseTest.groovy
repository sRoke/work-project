package net.kingsilk.qh.agency.wap

import net.kingsilk.qh.agency.repo.CompanyRepo
import net.kingsilk.qh.agency.repo.StaffRepo
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
@ActiveProfiles(["default", "ut"])
@SpringBootTest(
        classes = [UtApp, QhAgencyWapApp],
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
    CompanyRepo companyRepo

    @Autowired
    StaffRepo staffRepo

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

}
