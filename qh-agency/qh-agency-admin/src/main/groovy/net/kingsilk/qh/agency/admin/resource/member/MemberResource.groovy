package net.kingsilk.qh.agency.admin.resource.member

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.admin.api.member.MemberApi
import net.kingsilk.qh.agency.admin.api.member.dto.MemberInfoResp
import net.kingsilk.qh.agency.admin.api.member.dto.MemberPageReq
import net.kingsilk.qh.agency.admin.api.member.dto.MemberPageResp
import net.kingsilk.qh.agency.admin.api.member.dto.MemberSaveReq
import net.kingsilk.qh.agency.admin.resource.member.convert.MemberConvert
import net.kingsilk.qh.agency.admin.service.ExcelWrite
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.QPartnerStaff
import net.kingsilk.qh.agency.repo.PartnerStaffRepo
import net.kingsilk.qh.agency.service.MemberService
import org.codehaus.groovy.runtime.DefaultGroovyMethods
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import org.springframework.web.multipart.MultipartFile

import javax.servlet.http.HttpServletResponse
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import java.text.SimpleDateFormat

/**
 *
 */

@Api(
        tags = "partnerStaff",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "会员管理相关API"
)
@Component
@Path("/partnerStaff")
public class MemberResource implements MemberApi {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MemberService memberService;
    @Autowired
    private PartnerStaffRepo partnerStaffRepo;
    @Autowired
    private ExcelWrite excelWrite;

    @Autowired
    MemberConvert memberConvert;

    @ApiOperation(
            value = "会员信息",
            notes = "会员信息")
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_R')")
    @Override
    public UniResp<MemberInfoResp> info(
            @QueryParam(value = "id")
                    String id) {
        PartnerStaff member = mongoTemplate.findById(id, PartnerStaff.class);
        Assert.notNull(member, "会员信息不存在");

        return new UniResp<MemberInfoResp>(status: 200, data: memberConvert.convertMemberToResp(member))
    }

    @ApiOperation(
            value = "保存或更新会员信息",
            notes = "保存或更新会员信息"
    )
    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_C','MEMBER_U')")
    @Override
    public UniResp<String> save(MemberSaveReq memberSaveReq) {

        PartnerStaff member = null;
        if (memberSaveReq.getId() != null) {
            member = partnerStaffRepo.findOne(memberSaveReq.getId());
        }

        if (!(member == null)) {
            member = new PartnerStaff();
        }

        member = memberService.convertReqToMember(member, memberSaveReq);
        partnerStaffRepo.save(member);
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @Override
    public UniResp<String> update(MemberSaveReq req) {
        return null;
    }

    @ApiOperation(
            value = "会员分页信息",
            notes = "会员分页信息"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_R')")
    @Override
    public UniResp<MemberPageResp> page(@BeanParam MemberPageReq memberPageReq) {

        PageRequest pageRequest = new PageRequest(memberPageReq.curPage, memberPageReq.pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Date startDate = null
        Date endDate = null
        if (memberPageReq.startDate) {
            startDate = sdf.parse(memberPageReq.startDate);
        }
        if (memberPageReq.endDate) {
            endDate = sdf.parse(memberPageReq.endDate);
        }
        Page<PartnerStaff> page = partnerStaffRepo.findAll(
                Expressions.allOf(
                        Expressions.anyOf(
//                                memberPageReq.keyWord ? QPartnerStaff.partnerStaff.realName.contains(memberPageReq.keyWord) : null,
//                                memberPageReq.keyWord ? QPartnerStaff.partnerStaff.phone.contains(memberPageReq.keyWord) : null,
                        ),
                        startDate ? QPartnerStaff.partnerStaff.dateCreated.gt(startDate) : null,
                        endDate ? QPartnerStaff.partnerStaff.lastModifiedDate.lt(endDate) : null,
                        QPartnerStaff.partnerStaff.deleted.in([false, null])
                ), pageRequest)
//
//
        Page<MemberPageResp.MemberMinInfo> infoPage = page.map({ PartnerStaff member ->
            MemberPageResp.MemberMinInfo info = new MemberPageResp.MemberMinInfo();
            info.id = member.id
//            info.realName = member.realName
//            info.phone = member.phone
            info.partnerType=member?.partner?.partnerTypeEnum?.desp
            info.partnerSeq = member?.partner?.seq
            info.userId = member.userId
//            info.tags = partnerStaff.tags[0]
            info.disabled = member.disabled
            return info
        });
        MemberPageResp resp = new MemberPageResp();
        resp.setMemberMinInfoPage(infoPage);
        return new UniResp<MemberPageResp>(status: 200, data: resp)
    }

    @ApiOperation(
            value = "禁用或启用会员",
            notes = "禁用或启用会员"
    )
    @Path("/enable")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_C','MEMBER_U')")
    @Override
    public UniResp<String> enable(
            @QueryParam(value = "id") String id,
            @QueryParam(value = "disabled") Boolean disabled) {
        PartnerStaff member = partnerStaffRepo.findOne(id);
        if (!DefaultGroovyMethods.asBoolean(member)) {
            UniResp<String> resp = new UniResp<>();

            resp.setStatus(10026);
            resp.setData("会员信息不存在");
            return resp;
        }

        member.disabled = disabled
        partnerStaffRepo.save(member);
        return new UniResp<String>(status: 200, data: "操作成功")
    }

    @ApiOperation(
            value = "禁用或启用会员",
            notes = "禁用或启用会员"
    )
    @Path("/export")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public UniResp<String> exportExcel(@Context HttpServletResponse response) throws Exception {

//        String title = "经销后台管理系统客户导入模板";
//        // 输出Excel文件
//        OutputStream out = response.getOutputStream();
//        response.reset();
//        String fileName = new String(title.getBytes("UTF-8"), "ISO-8859-1");
//        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".xls");
//        response.setContentType("application/x-xls");
//        response.setCharacterEncoding("UTF-8");
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet();
//        sheet = excelWrite.putOneRowInExcel(sheet, new ArrayList<String>(asList("名称", "类型", "手机号", "联系人")), 0);
//        sheet = excelWrite.putOneRowInExcel(sheet, new ArrayList<String>(asList("谢小环", "加盟商", "13616899456", "小王")), 1);
//        workbook.write(out);
//        out.close();
//        out.flush();
//        UniResp<String> resp = new UniResp<String>();
//
//        resp.setStatus(200);
//        resp.setData("操作成功");
//        return resp;
    }

    //    @RequestMapping(path = "/upload", method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
//    @ApiOperation(value = "上传会员信息", nickname = "上传会员信息", notes = "上传会员信息")
//    @ResponseBody
    @Override
    public UniResp<String> readExcel(@BeanParam MultipartFile file) throws IOException {
        //判断文件是否为空
//        if (file == null) {
//            Assert.notNull(file, "文件 " + String.valueOf(file) + " 不存在");
//        }
//
//        String name = file.getOriginalFilename();
//        long size = file.getSize();
//        if (name == null || ExcelUtil.getEMPTY().equals(name) && size == 0) {
//            Assert.notNull(file, "文件 " + String.valueOf(file) + " 内容为空");
//        }
//
//        //读取Excel数据到List中
//        List<ArrayList<String>> list = new ExcelRead().readExcel(file);
//        //list中存的就是excel中的数据，可以根据excel中每一列的值转换成你所需要的值（从0开始），如：
//        PartnerStaff partnerStaff;
//        for (ArrayList<String> arr : list) {
////            partnerStaff = memberRepo.findOne(QMember.partnerStaff.phone.eq(arr.get(2)));
//            if (!DefaultGroovyMethods.asBoolean(partnerStaff)) {
//                partnerStaff = new PartnerStaff();
//                partnerStaff.setDateCreated(new Date());
//            }
//
//            partnerStaff.setRealName(arr.get(0));//每一行的第一个单元格
//            Set<PartnerTypeEnum> tags = new HashSet<PartnerTypeEnum>();
//            if (arr.get(1).equals("加盟商")) {
//                ((HashSet<PartnerTypeEnum>) tags).add(PartnerTypeEnum.LEAGUE);
//            } else if (arr.get(1).equals("代理商")) {
//                ((HashSet<PartnerTypeEnum>) tags).add(PartnerTypeEnum.AGENCY);
//            }
//
//            partnerStaff.setLastModifiedDate(new Date());
//            DefaultGroovyMethods.invokeMethod(partnerStaff, "setTags", new Object[]{tags});
//            partnerStaff.setPhone(arr.get(2));
//            DefaultGroovyMethods.invokeMethod(partnerStaff, "setContacts", new Object[]{arr.get(3)});
//            memberRepo.save(partnerStaff);
//        }


        UniResp<String> resp = new UniResp<String>();
        resp.setStatus(10001);
        resp.setData("Excel文件读取失败");

        return resp;
    }

    @ApiOperation(
            value = "查询手机号是否重复",
            nickname = "查询手机号是否重复",
            notes = "查询手机号是否重复"
    )
    @Path("/queryPhone")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public UniResp<Boolean> queryPhone(@PathParam(value = "phone") String phone, @PathParam(value = "id") String id) {
//        Boolean isRepeat = false
//        PartnerStaff member
//        if (id) {
//            member = partnerStaffRepo.findOne(id)
//            if (!member) {
//                Iterable<PartnerStaff> members = partnerStaffRepo.findAllByPhone(phone)
//                if (members.size() > 0) {
//                    isRepeat = true
//                }
//            } else {
//                if (phone == member.phone) {
//                    isRepeat = false
//                } else {
//                    Iterable<PartnerStaff> members = partnerStaffRepo.findAllByPhone(phone)
//                    if (members.size() > 0) {
//                        isRepeat = true
//                    }
//                }
//            }
//        } else {
//            Iterable<PartnerStaff> members = partnerStaffRepo.findAllByPhone(phone)
//            if (members.size() > 0) {
//                isRepeat = true
//            }
//        }
//        return new UniResp<Boolean>(status: 200, data: isRepeat)
    }


}
