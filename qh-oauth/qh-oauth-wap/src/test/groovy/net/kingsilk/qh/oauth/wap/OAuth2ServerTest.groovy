package net.kingsilk.qh.oauth.wap

import net.kingsilk.qh.oauth.QhOAuthUtProperties
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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

// 使用独立运行的服务进行测试
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(["default", "ut"])
@SpringBootTest(classes = [App], webEnvironment = SpringBootTest.WebEnvironment.NONE)
class OAuth2ServerTest {

    @Configuration
    @EnableConfigurationProperties
    static class App {

        @Bean
        QhOAuthUtProperties qhOAuthUtProperties() {
            return new QhOAuthUtProperties()
        }
    }

    @Autowired
    QhOAuthUtProperties ut

    @Autowired
    void sss(
            @Value('${net.kingsilk.qh.oauth.ut.oauth.wap.url}') String p0,
            @Value('${net.kingsilk.qh.oauth.ut.oauth.wap.accessTokenUri}') String p1,
            @Value('${my.oauth2.auth.url}') String p2
    ) {
        println("----------------sssssssssssssssssssssss  p0 : " + p0)
        println("----------------sssssssssssssssssssssss  p1 : " + p1)
        println("----------------sssssssssssssssssssssss  p2 : " + p2)
    }


    final String logPrefix = "█" * 40 + " "
    final Logger log = LoggerFactory.getLogger(getClass());

    /*
    # 命令行获取AT。注意，这里配合了 Http Basic 认证来认证 client
curl http://MY_CLIENT:secret@a.localhost:10001/oauth/token \
    -d grant_type=client_credentials \
    -d scope=read \
    --trace-ascii /dev/stdout

响应如为：{
    "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiTVlfUlNDIl0sInNjb3BlIjpbInJlYWQiXSwiZXhwIjoxNDgyMDA0MTk1LCJhdXRob3JpdGllcyI6WyJST0xFX0NMSUVOVCJdLCJqdGkiOiIzYWY3NDVlZS1jYWIyLTRjNzctYjdmNi1jOWU4NDNhYzEzZDkiLCJjbGllbnRfaWQiOiJNWV9DTElFTlQifQ.T4Lq5vkNPNE6tDjCIf8NtPjzV6T15pU3WaFoHqnCtv8",
    "token_type": "bearer",
    "expires_in": 43199,
    "scope": "read",
    "jti": "3af745ee-cab2-4c77-b7f6-c9e843ac13d9"
}


    static final
     */
    /** Client 认证模式测试 */
    @Test
    void client01() {

        log.debug(logPrefix + "client01")

        String clientUsername = "CLIENT_ID_qh-agency-admin"
        String clientPassword = "CLIENT_PWD_qh-agency-admin_123456"

        TestRestTemplate restTemplate = new TestRestTemplate()

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.ALL])
        headers.set("Authorization", "Basic " + U.basicAuth(clientUsername, clientPassword));

        String path = UriComponentsBuilder.fromHttpUrl(ut.oauth.wap.accessTokenUri)
                .build()
                .toUri()
                .toString()

        MultiValueMap reqMsg = new LinkedMultiValueMap()
        // 这里只能是 string 值(true.toString)，否则用 multipart/form-data 提交数据
        // 可选：明确指明要使用的 content-type
        reqMsg.grant_type = "client_credentials"
        reqMsg.scope = "LOGIN"

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(reqMsg, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.POST, reqEntity, String.class);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK)
        assertThat(respEntity.body).contains('"access_token":"')
        assertThat(respEntity.body).contains('"jti":')

    }
}
