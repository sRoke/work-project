//package net.kingsilk.qh.agency.admin.controller.home
//
//import org.springframework.http.MediaType
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestMethod
//import org.springframework.web.bind.annotation.ResponseBody
//import org.springframework.web.bind.annotation.RestController
//
///**
// * 主页
// */
//@RestController()
//class HomeController {
//
//    @RequestMapping(path = "/", method = RequestMethod.GET, produces = [MediaType.TEXT_PLAIN_VALUE])
//    @ResponseBody
//    String index() {
//        return "qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin : " + new Date()
//    }
//
//    @RequestMapping(path = "/api", method = RequestMethod.GET, produces = [MediaType.TEXT_PLAIN_VALUE])
//    @ResponseBody
//    String api() {
//        return "qh-net.kingsilk.qh.agency-net.kingsilk.qh.net.kingsilk.qh.agency.admin : " + new Date()
//    }
//
//
//    @RequestMapping(path = "/swagger", method = RequestMethod.GET)
//    String swagger() {
//
//        // http://localhost:10070/admin/local/14300/rs/webjars/swagger-ui/3.0.19/index.html
//        // url=http://localhost:10070/admin/local/14300/rs/api/swagger.json
//        return "redirect:"
//    }
//
//
//}
