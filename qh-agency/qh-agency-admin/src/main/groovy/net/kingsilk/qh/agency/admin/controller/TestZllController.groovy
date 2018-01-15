package net.kingsilk.qh.agency.admin.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import javax.servlet.ServletRegistration
import javax.servlet.http.HttpServletRequest
import java.security.Principal

/**
 *
 */
@RestController()
@RequestMapping("/testZll")
@Api(
        tags = "testZll",
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        description = "般若的测试demo"
)
class TestZllController {

    @RequestMapping(
            path = "/l",
            method = RequestMethod.GET
    )
    Object l(HttpServletRequest request) {
        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext().getServletRegistrations();
        return [
                list: servletRegistrations,
                date: new Date()
        ]
    }


    @ApiOperation(
            value = "测试读取权限",
            nickname = "sec",
            notes = "测试读取权限"
    )
    @RequestMapping(
            path = "/sec",
            method = RequestMethod.GET
    )
    @PreAuthorize("hasAnyAuthority('ZZZ')")
    @ResponseBody
    Object sec(Principal principal, @AuthenticationPrincipal Object curUser) {
        return [
                msg : "SUCCESS",
                data: [
                        OK       : "sec",
                        date     : new Date(),
                        principal: principal,
                        curUser  : curUser
                ]
        ]
    }

}
