package net.kingsilk.qh.agency.wap.controller.user

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import net.kingsilk.qh.agency.QhAgencyProperties
import net.kingsilk.qh.agency.wap.api.UniResp
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.service.CommonService
import net.kingsilk.qh.agency.service.MemberService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * Created by zcw on 17-3-16.
 */
@RestController()
@RequestMapping("/api/user")
@Api(
        tags = "user",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "用户相关API"
)
class UserController {

    @Autowired
    CommonService commonService

    @Autowired
    MemberService memberService

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    QhAgencyProperties prop

    @RequestMapping(path = "/info",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "用户信息",
            nickname = "用户信息",
            notes = "用户信息"
    )
    @ApiResponses([
            @ApiResponse(
                    code = 200,
                    message = "正常结果")
    ])
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('MEMBER')")
    UniResp<UserInfoResp> info() {
        PartnerStaff member = memberService.getCurPartnerStaff()
        UserInfoResp resp = new UserInfoResp()
        resp.convert(member)
        return new UniResp<UserInfoResp>(status: 200, data: resp)
    }
}
