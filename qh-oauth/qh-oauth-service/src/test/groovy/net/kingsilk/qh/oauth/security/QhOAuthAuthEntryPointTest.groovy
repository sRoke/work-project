package net.kingsilk.qh.oauth.security

import net.kingsilk.qh.oauth.BaseTest
import net.kingsilk.qh.oauth.QhOAuthProperties
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import static org.assertj.core.api.Assertions.assertThat

/**
 *
 */
class QhOAuthAuthEntryPointTest extends BaseTest {


    @Before
    void before() {

        after()

    }

    @After
    void after() {

    }

    /**
     *
     */
    @Test
    void commence01() {

        QhOAuthAuthEntryPoint entryPoint = new QhOAuthAuthEntryPoint()
        QhOAuthProperties props = new QhOAuthProperties()
        props.wxLoginUrl = "/wxLogin"
        entryPoint.setProps(props)

        MockHttpServletRequest request = new MockHttpServletRequest()
        MockHttpServletResponse response = new MockHttpServletResponse()
        entryPoint.commence(request,response, null)

        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value())
        assertThat(response.getHeader(HttpHeaders.LOCATION)).isEqualTo("http://localhost/wxLogin")
//
//        auth2Logout()
//        auth2Login("a_user", "a_user")
//        Map<String, String> tokenInfos = auth2ImplicitAuthorize(
//                ut.agency.admin.front.clientId,
//                ut.agency.admin.front.url,
//                "LOGIN"
//        )
//
//
//        String url = ut.agency.admin.url + "/api/testZll/sec";
//
//        HttpHeaders headers = new HttpHeaders()
//        headers.setAccept([MediaType.ALL])
//        headers.set(CompanyIdFilter.COMPANY_ID_REQUEST_HEADER, company.id)
//        headers.set("Authorization", OAuth2AccessToken.BEARER_TYPE+" " + tokenInfos.access_token);
//        String uri = UriComponentsBuilder.fromHttpUrl(url)
//                .build()
//                .toUri()
//                .toString()
//
//        HttpEntity<String> reqEntity = new HttpEntity<String>(null, headers);
//
//        ResponseEntity<String> respEntity = wwwRestTemplate.exchange(uri,
//                HttpMethod.GET, reqEntity, String.class);
//
//
//        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK)
//        assertThat(respEntity.body)
//                .contains("SUCCESS")
//                .contains("sec")

    }


}
