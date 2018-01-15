//package net.kingsilk.qh.agency.admin.controller.authorities
//
//import io.swagger.annotations.Api
//import io.swagger.annotations.ApiOperation
//import net.kingsilk.qh.agency.admin.api.UniResp
//import net.kingsilk.qh.agency.repo.CompanyRepo
//import net.kingsilk.qh.agency.repo.StaffRepo
//import net.kingsilk.qh.agency.service.SecService
//import net.kingsilk.qh.agency.service.StaffUserDetailsService
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.MediaType
//import org.springframework.security.access.prepost.PreAuthorize
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestMethod
//import org.springframework.web.bind.annotation.ResponseBody
//import org.springframework.web.bind.annotation.RestController
//
///**
// *
// */
//@RestController()
//@RequestMapping("/api/authorities")
//@Api( // 用在类上，用于设置默认值
//        tags = "authorities",
//        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
//        protocols = "http,https",
//        description = "权限管理相关API"
//)
//class AuthoritiesController {
//
//    @Autowired
//    StaffRepo staffRepo
//
//    @Autowired
//    SecService secService
//
//    @Autowired
//    CompanyRepo companyRepo
//
//    @Autowired
//    StaffUserDetailsService staffUserDetailsService
//
//
//    @RequestMapping(path = "/getAuthorities",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "当前用户的权限信息",
//            nickname = "当前用户的权限信息",
//            notes = "当前用户的权限信息"
//    )
//    @ResponseBody
//    @PreAuthorize("isAuthenticated()")
//    UniResp<UserDetails> getAuthorities() {
//        String userId = secService.curUserId();
//        UserDetails user = staffUserDetailsService.loadUserByUsername(userId)
//        return new UniResp(status: 200, data: user);
//    }
//}
