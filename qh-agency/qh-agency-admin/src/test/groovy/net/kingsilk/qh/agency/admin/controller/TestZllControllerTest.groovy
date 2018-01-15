package net.kingsilk.qh.agency.admin.controller

import net.kingsilk.qh.agency.admin.BaseTest
import net.kingsilk.qh.agency.core.StaffAuthorityEnum
import net.kingsilk.qh.agency.domain.Company
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.repo.StaffRepo
import net.kingsilk.qh.agency.security.BrandIdFilter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.web.util.UriComponentsBuilder

import static org.assertj.core.api.Assertions.assertThat

/**
 *
 */
class TestZllControllerTest extends BaseTest {

    @Autowired
    StaffRepo staffRepo

    Company company

    Staff staff1

    Staff staff2

    @Before
    void before() {

        after()

        // 添加公司
        company = new Company()
        company.name = "般若加盟店"
        company.bizLicenseNo = "999"
        company.legalPerson = "般若"

        company.officeAddr = "东方电子商务园15幢1楼"
        company.contacts = "般若"
        company.phone = "18069855776"
        companyRepo.save(company)

        // 添加员工
        staff1 = new Staff()
        staff1.company = company
        staff1.userId = "a_admin"
        staff1.realName = "a_admin_real_name"
        staff1.nickName = "a_admin_nick_name"
        staff1.authorities = [
                StaffAuthorityEnum.XXX.name(),
                StaffAuthorityEnum.YYY.name()
        ] as Set;
        staffRepo.save(staff1)

        staff2 = new Staff()
        staff2.company = company
        staff2.userId = "a_user"
        staff2.realName = "a_user_real_name"
        staff2.nickName = "a_user_nick_name"
        staff2.authorities = [
                StaffAuthorityEnum.XXX.name(),
                StaffAuthorityEnum.ZZZ.name()
        ] as Set;
        staffRepo.save(staff2)

    }

    @After
    void after() {
        staffRepo.deleteAll()
        companyRepo.deleteAll()
    }

    /**
     * 正常授权，有权限
     */
    @Test
    void sec01() {

        assertThat(company.id).isNotNull()

        auth2Logout()
        auth2Login("a_user", "a_user")
        Map<String, String> tokenInfos = auth2ImplicitAuthorize(
                ut.agency.admin.front.clientId,
                ut.agency.admin.front.url,
                "LOGIN"
        )


        String url = ut.agency.admin.url + "/api/testZll/sec";

        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, company.id)
        headers.set("Authorization", OAuth2AccessToken.BEARER_TYPE + " " + tokenInfos.access_token);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()

        HttpEntity<String> reqEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(uri,
                HttpMethod.GET, reqEntity, String.class);


        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.OK)
        assertThat(respEntity.body)
                .contains("SUCCESS")
                .contains("sec")

    }

    /**
     * 未登录
     */
    @Test
    void sec02() {

        assertThat(company.id).isNotNull()

        String url = ut.agency.admin.url + "/api/testZll/sec";

        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, company.id)
        //headers.set("Authorization", OAuth2AccessToken.BEARER_TYPE+" " + tokenInfos.access_token);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()

        HttpEntity<String> reqEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(uri,
                HttpMethod.GET, reqEntity, String.class);


        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED)
    }

    /**
     * 已授权，但没权限
     */
    @Test
    void sec03() {

        assertThat(company.id).isNotNull()

        auth2Logout()
        auth2Login("a_admin", "a_admin")
        Map<String, String> tokenInfos = auth2ImplicitAuthorize(
                ut.agency.admin.front.clientId,
                ut.agency.admin.front.url,
                "LOGIN"
        )


        String url = ut.agency.admin.url + "/api/testZll/sec";

        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, company.id)
        headers.set("Authorization", OAuth2AccessToken.BEARER_TYPE + " " + tokenInfos.access_token);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()

        HttpEntity<String> reqEntity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(uri,
                HttpMethod.GET, reqEntity, String.class);


        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN)

    }

//    @Test
//    void getAccessToken01() {
//        auth2Logout()
//        auth2Login("a_admin", "a_admin")
//        auth2ImplicitAuthorize(
//                ut.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.admin.front.clientId,
//                ut.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.admin.front.url,
//                "LOGIN"
//        )
//    }


}
