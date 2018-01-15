//package net.kingsilk.qh.agency.admin.controller.company
//
//import io.swagger.annotations.Api
//import io.swagger.annotations.ApiOperation
//import io.swagger.annotations.ApiResponse
//import io.swagger.annotations.ApiResponses
//import net.kingsilk.qh.agency.QhAgencyProperties
//import net.kingsilk.qh.agency.admin.api.UniResp
//import net.kingsilk.qh.agency.domain.Company
//import net.kingsilk.qh.agency.domain.Staff
//import net.kingsilk.qh.agency.repo.PartnerStaffRepo
//import net.kingsilk.qh.agency.repo.StaffRepo
//import net.kingsilk.qh.agency.service.CommonService
//import net.kingsilk.qh.agency.service.SecService
//import net.kingsilk.qh.agency.service.UserService
//import net.kingsilk.qh.agency.util.Page
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.web.client.RestTemplateBuilder
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.data.mongodb.core.query.Criteria
//import org.springframework.data.mongodb.core.query.Query
//import org.springframework.http.MediaType
//import org.springframework.security.access.prepost.PreAuthorize
//import org.springframework.util.Assert
//import org.springframework.web.bind.annotation.*
//import org.springframework.web.client.RestTemplate
//
//import javax.servlet.http.HttpServletRequest
//
///**
// * Created by tpx on 17-3-21.
// */
//@RestController()
//@RequestMapping("/api/company")
//@Api( // 用在类上，用于设置默认值
//        tags = "company",
//        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
//        protocols = "http,https",
//        description = "公司相关API"
//)
//class CompanyController {
//
//    @Autowired
//    MongoTemplate mongoTemplate
//
//
//    @Autowired
//    CommonService commonService
//
//    @Autowired
//    PartnerStaffRepo memberRepository
//
//    @Autowired
//    SecService secService
//
//    @Autowired
//    UserService userService
//
//    @Autowired
//    StaffRepo staffRepo
//
//    @Autowired
//    QhAgencyProperties prop
//
//    RestTemplate restTemplate
//
//    @Autowired
//    void confRestTemplate(RestTemplateBuilder restTemplateBuilder) {
//        restTemplate = restTemplateBuilder.build()
//    }
//
//    @RequestMapping(path = "/info",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "公司信息",
//            nickname = "公司信息",
//            notes = "公司信息"
//    )
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
//    UniResp<CompanyInfoResp> info(String id) {
//        Company company = mongoTemplate.findById(id, Company);
//        if (!company) {
//            return new UniResp(status: 10026, message: "公司信息不存在")
//        }
//        CompanyInfoResp infoResp = new CompanyInfoResp();
//        return new UniResp<CompanyInfoResp>(status: 200, data: infoResp.convertToResp(company))
//    }
//
//    @RequestMapping(path = "/save",
//            method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "保存或更新公司信息",
//            nickname = "保存或更新公司信息",
//            notes = "保存或更新公司信息"
//    )
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
//    UniResp<String> save(@RequestBody CompanySaveReq companySaveReq) {
//        Company company = mongoTemplate.findById(companySaveReq.getId(), Company);
//        if (!company) {
//            company = new Company();
//        }
//        company = companySaveReq.convertReqToCompany(company)
//        mongoTemplate.save(company)
//        return new UniResp<CompanyInfoResp>(status: 200, data: "保存成功")
//    }
//
//    @RequestMapping(path = "/page",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "公司分页信息",
//            nickname = "公司分页信息",
//            notes = "公司分页信息"
//    )
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
//    UniResp<CompanyPageResp> page(CompanyPageReq companyPageReq) {
//        Criteria criteria = new Criteria()
//        if (companyPageReq.keyWord) {
//            criteria.andOperator(
//                    Criteria.where("name").regex(companyPageReq.keyWord),
//                    criteria.andOperator(Criteria.where('delete').in([null, false]))
//            )
//        }
//        Page<Company> companys = commonService.domainPages(Company, new Query(criteria), companyPageReq.curPage, companyPageReq.pageSize);
//        CompanyPageResp companyPageResp = new CompanyPageResp().convertToResp(companys)
//        return new UniResp(status: 200, data: companyPageResp);
//    }
//
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
//        // 获取用户绑定的手机号码
//        // 查询 partnerStaff 表，where userid= null and phone = 上一步返回的手机号码
//        // 更新上述每条记录的 userid 为当前用户的 userid
//        // 重新查询数据库 partnerStaff 表，where userid= curUserId
//
//        def oauthUser = userService.getOauthUserInfo(request)
//        Assert.notNull(oauthUser.phone, "未绑定手机号")
//        List<Staff> tmpList = staffRepo.findAllByPhoneAndDisabledAndDeleted(oauthUser.phone as String, false, false)
//        tmpList.each { it ->
//            if (!it.userId) {
//                it.userId = curUserId
//                staffRepo.save(it)
//            }
//        }
//            List<Staff> memberList = staffRepo.findAllByUserIdAndDisabledAndDeleted(curUserId, false, false)
//        def resp = new BindPhoneAndListResp()
//        resp.convert(memberList)
//        Assert.notNull(resp.recList, "暂无组织")
//        return new UniResp<BindPhoneAndListResp>(status: 200, data: resp)
//    }
//}
