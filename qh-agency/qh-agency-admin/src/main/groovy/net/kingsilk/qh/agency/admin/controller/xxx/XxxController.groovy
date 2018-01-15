package net.kingsilk.qh.agency.admin.controller.xxx

import io.swagger.annotations.*
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.request.WebRequest
import org.springframework.web.util.UriComponentsBuilder

import java.security.Principal

/**
 * 示例 controller
 */
@RestController
@RequestMapping("/api/xxx")
@Api( // 用在类上，用于设置默认值
        tags = "xxx",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http",
        description = "测试的数据"
)
class XxxController {
    RestTemplate restTemplate;

    XxxController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping(path = "/pub", method = RequestMethod.GET)
    @ResponseBody
    Object pub(Principal principal, @AuthenticationPrincipal Object curUser) {

        println "-----------principal.details.tokenValue = " + principal?.details?.tokenValue
        return [
                msg      : "qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin /xxx/pub",
                date     : new Date(),
                principal: principal.toString(),
                curUser  : curUser.toString()

        ]
    }

    // ---------------------------------------------  人员登录后的功能
    @RequestMapping(path = "/userSec", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("isAuthenticated() and hasAnyAuthority('STAFF')")
    Object userSec(Principal principal, @AuthenticationPrincipal Object curUser) {
        println "-----------principal.details.tokenValue = " + principal?.details?.tokenValue
        return [
                msg      : "qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin /xxx/userSec",
                date     : new Date(),
                principal: principal.toString(),
                curUser  : curUser.toString()
        ]
    }


    @RequestMapping("/passAt")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    Object passAt(WebRequest req, Principal principal) {
        String at = principal?.details?.tokenValue



        HttpHeaders headers = new HttpHeaders();
        headers.setAccept([MediaType.ALL])
        headers.set("Authorization", "Bearer " + at);

        String path = UriComponentsBuilder.fromHttpUrl("http://localhost:10040/xxx/userSec")
                .build()
                .toUri()
                .toString()

//        MultiValueMap reqMsg = new LinkedMultiValueMap()
//        // 这里只能是 string 值(true.toString)，否则用 multipart/form-data 提交数据
//        // 可选：明确指明要使用的 content-type
//        reqMsg.grant_type = "client_credentials"
//        reqMsg.scope = "LOGIN"

        HttpEntity<Void> reqEntity = new HttpEntity<Void>(null, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(path,
                HttpMethod.GET, reqEntity, String.class);

        println "respEntity : "+ respEntity

        return respEntity.getBody();
    }

    // OAuth2SecurityExpressionMethods
    @RequestMapping(path = "/clientSec", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("isAuthenticated() and #oauth2.hasAnyScope('LOGIN') ")
    Object clientSec(Principal principal, @AuthenticationPrincipal Object curUser) {
        return [
                msg      : "qh-net.kingsilk.qh.agency-wap clientSec",
                date     : new Date(),
                principal: principal.toString(),
                curUser  : curUser.toString()

        ]
    }

    @RequestMapping(path = "/swagger",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "swagger-test",
            nickname = "测试swagger",
            notes = "测试swagger-test"
    )
    @ResponseBody
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    ])
    Object swagger(@ApiParam(value = "用户名", required = true) @ModelAttribute("name") String name,
                   @ApiParam(value = "密码", required = true) @ModelAttribute("password") String password) {
        return [
                msg     : "SUCCESS",
                name    : name,
                password: password
        ]
    }

}
