package net.kingsilk.qh.agency.service

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.QhAgencyProperties
import net.kingsilk.qh.agency.api.ErrStatus
import net.kingsilk.qh.agency.api.ErrStatusException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

import javax.servlet.http.HttpServletRequest

/**
 * 发送短信服务
 */
@Service
@CompileStatic
class UserService {

    @Autowired
    SecService secService

    @Autowired
    QhAgencyProperties prop

    @Autowired
    RestTemplate oClientOauthWapRestTemplate

    @Autowired
    RestOperations restTemplate

    /**
     * 获取当前用户
     * 使用 secService.curUserId()
     * @return
     */
    @Deprecated
    String getCurUser() {
//        def userDetails = (UserDetails) SecurityContextHolder.getContext()
//                .getAuthentication()
//                .getPrincipal()
//        return userDetails
        return secService.curUserId()
    }

    /**
     * 获取用户在oauth系统中的用户信息
     * @param request
     * @return
     */
    Map getOauthUserInfo(HttpServletRequest request) {

        String at = request.getHeader("Authorization")
        String url = prop.qhOAuth.wap.userinfoApi

        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        //headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, "")
        headers.set("Authorization", at);
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()
        HttpEntity<Map> reqEntity = new HttpEntity<Map>(null, headers);
        ResponseEntity<Map> respEntity = restTemplate.exchange(uri, HttpMethod.GET, reqEntity, Map);
        println("respEntity: ${respEntity.body}")
        return (Map)respEntity.body.data
    }

    Map getOauthUserInfoByUserId(String userId) {

//        String at = request.getHeader("Authorization")
        String url = prop.qhOAuth.wap.getUserInfoByUserIdApi

        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        //headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, "")
//        headers.set("Authorization", at);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("userId",userId)
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(map)
                .build()
                .toUri()
                .toString()

        HttpEntity<Map> reqEntity = new HttpEntity<Map>(null, headers);
        ResponseEntity<Map> respEntity = oClientOauthWapRestTemplate.exchange(uri, HttpMethod.GET, reqEntity, Map);
        println("respEntity: ${respEntity.body}")
        return (Map)respEntity.body.data
    }

    Map getOpenIdByUserId(String userId) {

//        String at = request.getHeader("Authorization")
        String url = prop.qhOAuth.wap.getOpenIdByUserId

        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        //headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, "")
//        headers.set("Authorization", at);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("userId",userId)
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParams(map)
                .build()
                .toUri()
                .toString()

        HttpEntity<Map> reqEntity = new HttpEntity<Map>(null, headers);
        ResponseEntity<Map> respEntity = oClientOauthWapRestTemplate.exchange(uri, HttpMethod.GET, reqEntity, Map);
        println("respEntity: ${respEntity.body}")
        return (Map)respEntity.body.data
    }

    String getAppId(String brandAppId) {

//        String at = request.getHeader("Authorization")
        String url = prop.qhPay.wap.api.create + brandAppId

        HttpHeaders headers = new HttpHeaders()
        headers.setAccept([MediaType.ALL])
        //headers.set(BrandIdFilter.COMPANY_ID_REQUEST_HEADER, "")
//        headers.set("Authorization", at);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        String uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .toUri()
                .toString()

        HttpEntity<Map> reqEntity = new HttpEntity<Map>(null, headers);
        ResponseEntity<Map> respEntity = oClientOauthWapRestTemplate.exchange(uri, HttpMethod.GET, reqEntity, Map);
        println("respEntity: ${respEntity.body}")
        if (!respEntity.body.data){
            throw new ErrStatusException(ErrStatus.PARTNER_500, "没有设置支付信息")
        }
        Map data = (Map)respEntity.body.data
        def wxPayInfo = (Map)data.get("wxPayInfo")
        def appId = wxPayInfo.get("appId")
        return appId
    }
}
