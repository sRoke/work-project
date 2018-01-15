package net.kingsilk.qh.agency.wap.controller

import net.kingsilk.qh.agency.domain.Company
import net.kingsilk.qh.agency.domain.Item
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.security.BrandIdFilter
import net.kingsilk.qh.agency.wap.BaseTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.*
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.web.util.UriComponentsBuilder

import static org.assertj.core.api.Assertions.assertThat

/**
 * Created by zcw on 3/21/17.
 */
/**
 * 购物车相关测试用例
 */
class ItemControllerTest extends BaseTest {

    Company company

    Staff staff1

    @Autowired
    MongoTemplate mongoTemplate

    @Before
    void before() {
        after()
        company = companyRepo.findAll().last()
    }

    @After
    void after() {

    }

    /**
     * 正常授权，有权限
     */
    @Test
    void testList() {

        assertThat(company.id).isNotNull()

        auth2Logout()
        auth2Login("a_user", "a_user")
        Map<String, String> tokenInfos = auth2ImplicitAuthorize(
                ut.agency.wap.front.clientId,
                ut.agency.wap.front.url,
                "LOGIN"
        )
        String url = ut.agency.wap.url + "/api/item/search";

        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, company.id)
        headers.set("Authorization", OAuth2AccessToken.BEARER_TYPE + " " + tokenInfos.access_token);
        println("access_token:${tokenInfos.access_token}")
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()
        def map = [
                curPage : 1,
                pageSize: 5
        ]
        HttpEntity<String> reqEntity = new HttpEntity<String>(map.toMapString(), headers);
        ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, String);
        println("-----------------------------------resp")
        println(respEntity.getStatusCode())
        println(respEntity.getBody())
    }

    @Test
    void testInfo() {
        //itemId = 58d0ebad54229e696472631e
        String itemId = mongoTemplate.findAll(Item).last().id
        assertThat(company.id).isNotNull()

        auth2Logout()
        auth2Login("a_user", "a_user")
        Map<String, String> tokenInfos = auth2ImplicitAuthorize(
                ut.agency.wap.front.clientId,
                ut.agency.wap.front.url,
                "LOGIN"
        )
        String url = ut.agency.wap.url + "/api/item/detail?itemId=${itemId}";

        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, company.id)
        headers.set("Authorization", OAuth2AccessToken.BEARER_TYPE + " " + tokenInfos.access_token);
        println("access_token:${tokenInfos.access_token}")
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()
        HttpEntity<String> reqEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, String);
        println("-----------------------------------resp")
        println(respEntity.getStatusCode())
        println(respEntity.getBody())
    }

}
