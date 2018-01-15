package net.kingsilk.qh.agency.wap.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import java.security.Principal

/**
 * 示例 controller
 */
@RestController()
@RequestMapping("/xxx")
@Api( // 用在类上，用于设置默认值
        tags = "xxx",
        consumes = MediaType.APPLICATION_ATOM_XML_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http",
        description = "测试的数据"
)
class XxxController {

    @RequestMapping(path = "/pub", method = RequestMethod.GET)
    @ResponseBody
    Object pub(Principal principal, @AuthenticationPrincipal Object curUser) {
        return [
                msg      : "qh-agency-wap pub",
                date     : new Date(),
                principal: principal.toString(),
                curUser  : curUser.toString()

        ]
    }

    // ---------------------------------------------  人员登录后的功能
    @RequestMapping(path = "/userSec", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("isAuthenticated() and hasAnyAuthority('USER')")
    Object userSec(Principal principal, @AuthenticationPrincipal Object curUser) {
        return [
                msg      : "qh-agency-wap userSec",
                date     : new Date(),
                principal: principal.toString(),
                curUser  : curUser.toString()
        ]
    }

    // OAuth2SecurityExpressionMethods
    @RequestMapping(path = "/clientSec", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("isAuthenticated() and #oauth2.hasAnyScope('LOGIN') ")
    Object clientSec(Principal principal, @AuthenticationPrincipal Object curUser) {
        return [
                msg      : "qh-agency-wap clientSec",
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
