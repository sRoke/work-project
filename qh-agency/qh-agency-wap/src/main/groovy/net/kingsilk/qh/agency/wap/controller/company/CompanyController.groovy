//package net.kingsilk.qh.agency.wap.controller.company
//
//import io.swagger.annotations.Api
//import io.swagger.annotations.ApiOperation
//import io.swagger.annotations.ApiResponse
//import io.swagger.annotations.ApiResponses
//import net.kingsilk.qh.agency.QhAgencyProperties
//import net.kingsilk.qh.agency.wap.api.UniResp
//import net.kingsilk.qh.agency.domain.PartnerStaff
//import net.kingsilk.qh.agency.repo.PartnerStaffRepo
//import net.kingsilk.qh.agency.service.CommonService
//import net.kingsilk.qh.agency.service.SecService
//import net.kingsilk.qh.agency.service.UserService
//
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.http.MediaType
//import org.springframework.security.access.prepost.PreAuthorize
//import org.springframework.util.Assert
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestMethod
//import org.springframework.web.bind.annotation.ResponseBody
//import org.springframework.web.bind.annotation.RestController
//
//import javax.servlet.http.HttpServletRequest
//
//@RestController()
//@RequestMapping("/api/company")
//@Api(
//        tags = "user",
//        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
//        protocols = "http,https",
//        description = "用户相关API"
//)
//class CompanyController {
//
//    @Autowired
//    CommonService commonService
//
//    @Autowired
//    MongoTemplate mongoTemplate
//
//    @Autowired
//    QhAgencyProperties prop
//
//    @Autowired
//    PartnerStaffRepo partnerStaffRepo
//
//    @Autowired
//    SecService secService
//
//    @Autowired
//    UserService userService
//
//    @RequestMapping(path = "/bindPhoneAndList",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "绑定手机号并列出公司列表",
//            nickname = "绑定手机号并列出公司列表",
//            notes = "绑定手机号并列出公司列表"
//    )
//    @ApiResponses([
//            @ApiResponse(
//                    code = 200,
//                    message = "正常结果")
//    ])
//    @ResponseBody
//    @PreAuthorize("isAuthenticated()")
//    UniResp<BindPhoneAndListResp> bindPhoneAndList(
//            HttpServletRequest request) {
//        String curUserId = secService.curUserId()
//        Assert.notNull(curUserId, "用户不存在")
//
//        // 查询 partnerStaff 表，where userid= null and phone = 上一步返回的手机号码
//        // 更新上述每条记录的 userid 为当前用户的 userid
//        // 重新查询数据库 partnerStaff 表，where userid= curUserId
//
//        def oauthUser = userService.getOauthUserInfo(request)
//        Assert.notNull(oauthUser.phone, "未绑定手机号")
//        List<PartnerStaff> tmpList = partnerStaffRepo.findAllByPhoneAndDeleted(oauthUser.phone as String, false)
//        tmpList.each { it ->
//            if (!it.userId) {
//                it.userId = curUserId
//                partnerStaffRepo.save(it)
//            }
//        }
//        List<PartnerStaff> memberList = partnerStaffRepo.findAllByUserIdAndDisabledAndDeleted(curUserId, false, false)
//        def resp = new BindPhoneAndListResp()
//        resp.convert(memberList)
//        Assert.notNull(resp.recList, "暂无组织")
//        Assert.isTrue(resp.recList.size() > 0, "暂无组织")
//        return new UniResp<BindPhoneAndListResp>(status: 200, data: resp)
//    }
//}
