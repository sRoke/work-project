//package net.kingsilk.qh.agency.admin.controller.member
//
//import com.querydsl.core.types.dsl.Expressions
//import io.swagger.annotations.Api
//import io.swagger.annotations.ApiOperation
//import net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.admin.controller.UniResp
//import net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.admin.service.ExcelRead
//import net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.admin.service.ExcelUtil
//import net.kingsilk.qh.net.kingsilk.qh.agency.net.kingsilk.qh.net.kingsilk.qh.agency.admin.service.ExcelWrite
//import net.kingsilk.qh.net.kingsilk.qh.agency.core.PartnerTypeEnum
//import net.kingsilk.qh.net.kingsilk.qh.agency.domain.Company
//import net.kingsilk.qh.net.kingsilk.qh.agency.domain.PartnerStaff
//import net.kingsilk.qh.net.kingsilk.qh.agency.domain.QMember
//import net.kingsilk.qh.net.kingsilk.qh.agency.repo.CompanyRepo
//import net.kingsilk.qh.net.kingsilk.qh.agency.repo.MemberRepo
//import net.kingsilk.qh.net.kingsilk.qh.agency.security.CompanyIdFilter
//import net.kingsilk.qh.net.kingsilk.qh.agency.service.CommonService
//import org.apache.poi.hssf.usermodel.HSSFSheet
//import org.apache.poi.hssf.usermodel.HSSFWorkbook
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.domain.Page
//import org.springframework.data.domain.PageRequest
//import org.springframework.data.domain.Sort
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.http.MediaType
//import org.springframework.security.access.prepost.PreAuthorize
//import org.springframework.util.Assert
//import org.springframework.web.bind.annotation.*
//import org.springframework.web.multipart.MultipartFile
//
//import javax.servlet.http.HttpServletResponse
//import java.text.SimpleDateFormat
//
///**
// * Created by tpx on 17-3-20.
// */
//@RestController()
//@RequestMapping("/api/partnerStaff")
//@Api( // 用在类上，用于设置默认值
//        tags = "partnerStaff",
//        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
//        protocols = "http,https",
//        description = "会员管理相关API"
//)
//class MemberController {
//
//    @Autowired
//    MongoTemplate mongoTemplate
//
//    @Autowired
//    CommonService commonService
//
//    @Autowired
//    CompanyRepo companyRepo
//    @Autowired
//    MemberRepo memberRepo
//    @Autowired
//    ExcelWrite excelWrite
//
//    @RequestMapping(path = "/info",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "会员信息",
//            nickname = "会员信息",
//            notes = "会员信息"
//    )
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_R')")
//    UniResp<MemberInfoResp> info(String id) {
//        PartnerStaff partnerStaff = mongoTemplate.findById(id, PartnerStaff);
//        if (!partnerStaff) {
//            return new UniResp(status: 10027, message: "会员信息不存在")
//        }
//        MemberInfoResp infoResp = new MemberInfoResp();
//        return new UniResp<MemberInfoResp>(status: 200, data: infoResp.convertMemberToResp(partnerStaff))
//    }
//
//    @RequestMapping(path = "/save",
//            method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "保存或更新会员信息",
//            nickname = "保存或更新会员信息",
//            notes = "保存或更新会员信息"
//    )
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_C','MEMBER_U')")
//    UniResp<String> save(@RequestBody MemberSaveReq memberSaveReq) {
//
//
//        String companyId = CompanyIdFilter.getCompanyId()
//        Company company = companyRepo.findOne(companyId)
//        Assert.notNull(company, "公司 ${companyId} 不存在")
//        PartnerStaff partnerStaff
//        if (memberSaveReq.getId() != null) {
//            partnerStaff = memberRepo.findOne(memberSaveReq.getId())
//        }
//        if (!partnerStaff) {
//            partnerStaff = new PartnerStaff();
//        }
//        partnerStaff.company = company
//        partnerStaff = memberSaveReq.convertReqToMember(partnerStaff)
//        memberRepo.save(partnerStaff)
//        return new UniResp<MemberInfoResp>(status: 200, data: "保存成功")
//    }
//
//    @RequestMapping(path = "/page",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "会员分页信息",
//            nickname = "会员分页信息",
//            notes = "会员分页信息"
//    )
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_R')")
//    UniResp<MemberPageResp> page(MemberPageReq memberPageReq) {
//
//        PageRequest pageRequest = new PageRequest(memberPageReq.curPage, memberPageReq.pageSize,
//                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
//        Date startDate = null
//        Date endDate = null
//        if (memberPageReq.startDate) {
//            startDate = sdf.parse(memberPageReq.startDate);
//        }
//        if (memberPageReq.endDate) {
//            endDate = sdf.parse(memberPageReq.endDate);
//        }
//        Page<PartnerStaff> page = memberRepo.findAll(
//                Expressions.allOf(
//                        Expressions.anyOf(
//                                memberPageReq.keyWord ? QMember.partnerStaff.realName.contains(memberPageReq.keyWord) : null,
//                                memberPageReq.keyWord ? QMember.partnerStaff.phone.contains(memberPageReq.keyWord) : null,
//                        ),
//                        startDate ? QMember.partnerStaff.dateCreated.gt(startDate) : null,
//                        endDate ? QMember.partnerStaff.lastModifiedDate.lt(endDate) : null,
//                        QMember.partnerStaff.deleted.in([false, null])
//                ), pageRequest)
////
////
//        Page<MemberPageResp.MemberMinInfo> infoPage = page.map({ PartnerStaff partnerStaff ->
//            MemberPageResp.MemberMinInfo info = new MemberPageResp.MemberMinInfo();
//            info.id = partnerStaff.id
//            info.realName = partnerStaff.realName
//            info.phone = partnerStaff.phone
//            info.tags = partnerStaff.tags[0]
//            info.disabled = partnerStaff.disabled
//            return info
//        });
//        MemberPageResp resp = new MemberPageResp()
//        resp.memberMinInfoPage = infoPage
//        return new UniResp(status: 200, data: resp);
//    }
//
//    @RequestMapping(path = "/enable",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "禁用或启用会员",
//            notes = "禁用或启用会员"
//    )
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_C','MEMBER_U')")
//    UniResp<String> enable(MemberEnableReq memberEnableReq) {
//        PartnerStaff partnerStaff = memberRepo.findOne(memberEnableReq.id)
//        if (!partnerStaff) {
//            return new UniResp(status: 10026, data: "会员信息不存在")
//        }
//        //partnerStaff.disabled = memberEnableReq.disabled()
//        partnerStaff.disabled = memberEnableReq.disabled
//        memberRepo.save(partnerStaff);
//        return new UniResp<MemberInfoResp>(status: 200, data: "操作成功")
//    }
//
////    @RequestMapping(path = "/type",
////            method = RequestMethod.GET,
////            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
////    @ApiOperation(
////            value = "获取会员类型",
////            nickname = "获取会员类型",
////            notes = "获取会员类型"
////    )
////    @ResponseBody
////    //@Secured(["isAuthenticated() && hasAnyRole('MEMBER_ENABLE','SUPER_ADMIN')"])
////    UniResp<String> type() {
////        List<String> typeEnums = new ArrayList<>()
////        typeEnums.add(PartnerTypeEnum.AGENCY.name())
////        typeEnums.add(PartnerTypeEnum.LEAGUE.name())
////        return new UniResp<List<PartnerTypeEnum>>(status: 200, data: typeEnums)
////    }
//
//    @RequestMapping(path = "/export",
//            method = RequestMethod.GET,
//            produces = MediaType.ALL_VALUE)
////            produces = "application/vnd.ms-excel;charset=UTF-8")
//    @ApiOperation(
//            value = "禁用或启用会员",
//            nickname = "禁用或启用会员",
//            notes = "禁用或启用会员"
//    )
//    @ResponseBody
////    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_E')")
//    void exportExcel(HttpServletResponse response) throws Exception {
//
//        String title = "经销后台管理系统客户导入模板"
//        // 输出Excel文件
//        OutputStream out = response.getOutputStream();
//        response.reset();
//        String fileName = new String(title.getBytes("UTF-8"), "ISO-8859-1");
//        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xls");
//        response.setContentType("application/x-xls");
//        response.setCharacterEncoding("UTF-8");
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet();
//        sheet = excelWrite.putOneRowInExcel(sheet, ['名称', '类型', '手机号', '联系人'], 0)
//        sheet = excelWrite.putOneRowInExcel(sheet, ["谢小环", "加盟商", "13616899456", "小王"], 1)
//        workbook.write(out)
//        out.close()
//        out.flush()
//    }
//
//
//    @RequestMapping(path = "/upload",
//            method = RequestMethod.POST,
//            produces = MediaType.ALL_VALUE)
//    @ApiOperation(
//            value = "上传会员信息",
//            nickname = "上传会员信息",
//            notes = "上传会员信息"
//    )
//    @ResponseBody
////    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_E')")
//    UniResp<String> readExcel(@RequestParam(value = "excelFile") MultipartFile file) throws IOException {
//        //判断文件是否为空
//        if (file == null) {
//            Assert.notNull(file, "文件 ${file} 不存在")
//        }
//        String name = file.getOriginalFilename();
//        long size = file.getSize();
//        if (name == null || ExcelUtil.EMPTY == name && size == 0) {
//            Assert.notNull(file, "文件 ${file} 内容为空")
//        }
//        //读取Excel数据到List中
//        List<ArrayList<String>> list = new ExcelRead().readExcel(file);
//        //list中存的就是excel中的数据，可以根据excel中每一列的值转换成你所需要的值（从0开始），如：
//        PartnerStaff partnerStaff
//        for (ArrayList<String> arr : list) {
//            partnerStaff = memberRepo.findOne(
//                    QMember.partnerStaff.phone.eq(arr.get(2))
//            )
//            if (!partnerStaff) {
//                partnerStaff = new PartnerStaff()
//                partnerStaff.setDateCreated(new Date())
//            }
//            partnerStaff.setRealName(arr.get(0));//每一行的第一个单元格
//            Set<PartnerTypeEnum> tags = new HashSet<PartnerTypeEnum>()
//            if (arr.get(1) == "加盟商") {
//                tags.add(PartnerTypeEnum.LEAGUE)
//            } else if (arr.get(1) == "代理商") {
//                tags.add(PartnerTypeEnum.AGENCY)
//            }
//            partnerStaff.setLastModifiedDate(new Date())
//            partnerStaff.setTags(tags)
//            partnerStaff.setPhone(arr.get(2));
//            partnerStaff.setContacts(arr.get(3));
//            memberRepo.save(partnerStaff)
//        }
//
//        return new UniResp<String>(status: 10001, data: 'Excel文件读取失败')
//    }
//
//    @RequestMapping(path = "/queryPhone",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ApiOperation(
//            value = "查询手机号是否重复",
//            nickname = "查询手机号是否重复",
//            notes = "查询手机号是否重复"
//    )
//    @ResponseBody
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
//    UniResp<Boolean> queryPhone(String phone, String id) {
//        Boolean isRepeata = false
//        PartnerStaff partnerStaff
//        if (id) {
//            partnerStaff = memberRepo.findOne(id)
//            if (!partnerStaff) {
//                Iterable<PartnerStaff> members = memberRepo.findAllByPhone(phone)
//                if (members.size() > 0) {
//                    isRepeat = true
//                }
//            } else {
//                if (phone == partnerStaff.phone) {
//                    isRepeat = false
//                } else {
//                    Iterable<PartnerStaff> members = memberRepo.findAllByPhone(phone)
//                    if (members.size() > 0) {
//                        isRepeat = true
//                    }
//                }
//            }
//        } else {
//            Iterable<PartnerStaff> members = memberRepo.findAllByPhone(phone)
//            if (members.size() > 0) {
//                isRepeat = true
//            }
//        }
//        return new UniResp<Boolean>(status: 200, data: isRepeat)
//    }
//
//}
