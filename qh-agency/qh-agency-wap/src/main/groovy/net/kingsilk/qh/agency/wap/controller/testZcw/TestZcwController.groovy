//package net.kingsilk.qh.agency.wap.controller.testZcw
//
//import io.swagger.annotations.Api
//import net.kingsilk.qh.agency.domain.PartnerStaff
//import net.kingsilk.qh.agency.domain.Staff
//import net.kingsilk.qh.agency.repo.CompanyRepo
//import net.kingsilk.qh.agency.repo.PartnerStaffRepo
//import net.kingsilk.qh.agency.repo.StaffRepo
//import net.kingsilk.qh.agency.service.EnumService
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.MediaType
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestMethod
//import org.springframework.web.bind.annotation.ResponseBody
//import org.springframework.web.bind.annotation.RestController
//
///**
// * Created by zcw on 17-3-16.
// */
//@RestController()
//@RequestMapping("/testZcw")
//@Api(
//        tags = "user",
//        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
//        protocols = "http,https",
//        description = "用户相关API"
//)
//class TestZcwController {
//
//    @Autowired
//    EnumService enumService
//
//    @RequestMapping(path = "/getEnum",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseBody
//    def getEnum(String key) {
//        return enumService.getEnumMap(key)
//    }
//
//    @Autowired
//    CompanyRepo companyRepo
//
//    @Autowired
//    PartnerStaffRepo partnerStaffRepo
//
//    @Autowired
//    StaffRepo staffRepo
//
//    @RequestMapping(path = "/initData",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseBody
//    def initData() {
//        def company = companyRepo.findOne("58eae75b8158191de95cd06e")
//
//        def member = new PartnerStaff()
//        member.company = company
//        member.phone = "18270839486"
//        member.contacts = "paohui"
//        member.avatar = "https://baidu.com/"
//        member.realName = "炮灰"
//        partnerStaffRepo.save(member)
//
//        def staff = new Staff()
//        staff.phone = "18270839486"
//        staff.company = company
//        staff.avatar = "https://baidu.com/"
//        staff.idNumber = "360123200012120001"
//        staff.nickName = "炮灰"
//        staff.realName = "zcw"
//        staffRepo.save(staff)
//        return "SUCCESS"
//    }
//}
