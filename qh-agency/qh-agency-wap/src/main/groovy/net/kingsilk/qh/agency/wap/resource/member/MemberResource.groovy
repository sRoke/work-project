package net.kingsilk.qh.agency.wap.resource.member

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.repo.PartnerStaffRepo
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import net.kingsilk.qh.agency.service.MemberService
import net.kingsilk.qh.agency.service.UserService
import net.kingsilk.qh.agency.wap.api.UniResp
import net.kingsilk.qh.agency.wap.api.member.dto.MemberInfoResp
import net.kingsilk.qh.agency.wap.resource.member.convert.MemberConvert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType

/**
 *
 */
@Api(
        tags = "member",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "会员相关API"
)
@Path("/member")
@Component
class MemberResource {
    @Autowired
    UserService userService

    @Autowired
    PartnerStaffRepo partnerStaffRepo

    @Autowired
    MemberConvert memberConvert

    @Autowired
    MemberService memberService

    @ApiOperation(
            value = "注册用户",
            nickname = "注册用户",
            notes = "注册用户"
    )
    @Path("/register")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> register(@Context HttpServletRequest request) {

//        String brandId = BrandAppIdFilter.getBrandAppId()
//        def oauthUser = userService.getOauthUserInfo(request)
//        PartnerStaff member = memberService.getCurPartnerStaff()
//        if (member) {
//            return new UniResp<String>(status: 4001, data: "注册失败，手机号已被注册")
//        } else {
//            member = new PartnerStaff()
//            member.dateCreated = new Date()
//            member.brandId = brandId
//            member.userId = oauthUser.userId as String
//            member.partner = null
////            member.phone = oauthUser.phone as String
//            member.authorities = null
////            member.realName = null
////            member.idNumber = null
//            partnerStaffRepo.save(member)
//        }
//        return new UniResp<String>(status: 200, data: "注册成功")
    }

    @ApiOperation(
            value = "注册用户",
            nickname = "注册用户",
            notes = "注册用户"
    )
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<MemberInfoResp> info() {
        PartnerStaff member = memberService.getCurPartnerStaff()
        if (!member){
            return new UniResp<MemberInfoResp>(status: 301, data: "未获取到用户信息")
        }
        MemberInfoResp resp = memberConvert.memberInfoRespConvert(member, member.partner)
        return new UniResp<MemberInfoResp>(status: 200, data: resp)
    }

}
