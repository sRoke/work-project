package net.kingsilk.qh.agency.wap.controller

import net.kingsilk.qh.agency.domain.Company
import net.kingsilk.qh.agency.repo.SkuRepo
import net.kingsilk.qh.agency.security.BrandIdFilter
import net.kingsilk.qh.agency.wap.BaseTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
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
class OrderControllerTest extends BaseTest {
    Company company

    @Autowired
    SkuRepo skuRepository

    @Before
    void before() {
        company = companyRepo.findAll().last()
    }

    @After
    void after() {
    }

    /**
     * 添加购物车
     */
    @Test
    void check() {

        assertThat(company.id).isNotNull()

        auth2Logout()
        auth2Login("a_user", "a_user")
        Map<String, String> tokenInfos = auth2ImplicitAuthorize(
                ut.agency.wap.front.clientId,
                ut.agency.wap.front.url,
                "LOGIN"
        )
        String url = ut.agency.wap.url + "/api/order/check";

        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, company.id)
        headers.set("Authorization", OAuth2AccessToken.BEARER_TYPE + " " + tokenInfos.access_token);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()

        List list = [
                [skuId: skuRepository.findAll().last().id, num: 2]
        ]
        HttpEntity<List> reqEntity = new HttpEntity<List>(list, headers);
        ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.POST, reqEntity, String);
        println("-----------------------------------resp")
        println(respEntity.getStatusCode())
        println(respEntity.getBody())
    }

    @Test
    void pay() {
        assertThat(company.id).isNotNull()

        auth2Logout()
        auth2Login("a_user", "a_user")
        Map<String, String> tokenInfos = auth2ImplicitAuthorize(
                ut.agency.wap.front.clientId,
                ut.agency.wap.front.url,
                "LOGIN"
        )
        String url = ut.agency.wap.url + "/api/order/pay";

        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, company.id)
        headers.set("Authorization", OAuth2AccessToken.BEARER_TYPE + " " + tokenInfos.access_token);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()

        HttpEntity reqEntity = new HttpEntity(null, headers);
        ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.POST, reqEntity, String);
        println("-----------------------------------resp")
        println(respEntity.getStatusCode())
        println(respEntity.getBody())
    }

}
