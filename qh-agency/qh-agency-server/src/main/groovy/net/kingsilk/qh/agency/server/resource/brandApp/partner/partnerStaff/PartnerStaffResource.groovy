package net.kingsilk.qh.agency.server.resource.brandApp.partner.partnerStaff

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniPageResp
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.PartnerStaffApi
import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto.PartnerStaffInfoResp
import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto.PartnerStaffPageReq
import net.kingsilk.qh.agency.api.brandApp.partner.partnerStaff.dto.PartnerStaffSaveReq
import net.kingsilk.qh.agency.domain.Partner
import net.kingsilk.qh.agency.domain.PartnerStaff
import net.kingsilk.qh.agency.domain.QPartnerStaff
import net.kingsilk.qh.agency.repo.PartnerRepo
import net.kingsilk.qh.agency.repo.PartnerStaffRepo
import net.kingsilk.qh.agency.server.service.ExcelWrite
import net.kingsilk.qh.agency.service.PartnerService
import net.kingsilk.qh.agency.service.PartnerStaffService
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffAddReq
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi
import net.kingsilk.qh.oauth.core.OrgStaffStatusEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component
import org.springframework.util.Assert

import java.text.SimpleDateFormat

/**
 *
 */


@Component
public class PartnerStaffResource implements PartnerStaffApi {

    @Autowired
    private MongoTemplate mongoTemplate
    @Autowired
    private PartnerStaffService memberService
    @Autowired
    private PartnerStaffRepo partnerStaffRepo
    @Autowired
    private ExcelWrite excelWrite

    @Autowired
    PartnerStaffConvert partnerStaffConvert

    @Autowired
    @Qualifier(value = "mvcConversionService")
    ConversionService conversionService

    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    OrgStaffApi orgStaffApi

    @Autowired
    private PartnerService partnerService

    @Override
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_R')")
    public UniResp<PartnerStaffInfoResp> info(
            String brandAppId,
            String partnerId,
            String id) {
        partnerService.check()
        PartnerStaff partnerStaff = mongoTemplate.findById(id, PartnerStaff.class);
        Assert.notNull(partnerStaff, "会员信息不存在");
        PartnerStaffInfoResp infoResp = partnerStaffConvert.convertPartnerStaffToResp(partnerStaff)
        return new UniResp<PartnerStaffInfoResp>(status: 200, data: infoResp)
    }

    @Override
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_C','MEMBER_U')")
    public UniResp<String> save(
            String brandAppId,
            String partnerId,
            PartnerStaffSaveReq req) {
        partnerService.check()
        PartnerStaff partnerStaff = new PartnerStaff()
        partnerStaff = memberService.convertReqToMember(partnerStaff, req)
        partnerStaff.setBrandAppId(brandAppId)
        Partner partner = partnerRepo.findOne(partnerId)
        partnerStaff.setPartner(partner)

        //TODO 新增渠道商员工时在oauth里面也新增一份
        OrgStaffAddReq orgStaffAddReq = new OrgStaffAddReq()
        orgStaffAddReq.setUserId(partnerStaff.getUserId())

        //TODO 目前先以staff的id作为orgStaff的工号（orgStaff很多字段都没有）
        orgStaffAddReq.setJobNo(partnerStaff.getId())
        orgStaffAddReq.setOrgId(partnerStaff.getPartner().getOrgId())
        orgStaffAddReq.setRealName(req.getRealName())
        orgStaffAddReq.setStatus(OrgStaffStatusEnum.ENABLED)
        net.kingsilk.qh.oauth.api.UniResp<String> uniResp =
                orgStaffApi.add(partnerStaff.getUserId(), partnerStaff.getPartner().getOrgId(), orgStaffAddReq)
        Assert.notNull(uniResp, "新增组织失败")
        partnerStaffRepo.save(partnerStaff)
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @Override
    public UniResp<String> update(
            String brandAppId,
            String partnerId,
            String id,
            PartnerStaffSaveReq req
    ) {
        partnerService.check()
        PartnerStaff partnerStaff = partnerStaffRepo.findOne(id)
        Assert.notNull(!partnerStaff, "渠道商会员信息错误")
        partnerStaff = memberService.convertReqToMember(partnerStaff, req)
        partnerStaffRepo.save(partnerStaff);
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @Override
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_R')")
    public UniResp<UniPageResp<PartnerStaffInfoResp>> page(
            String brandAppId,
            String partnerId,
            PartnerStaffPageReq req) {
        partnerService.check()
        PageRequest pageRequest = new PageRequest(req.page, req.size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟
        Date startDate = null
        Date endDate = null

        List<String> userIds = req.idList

        if (req.startDate) {
            startDate = sdf.parse(req.startDate);
        }
        if (req.endDate) {
            endDate = sdf.parse(req.endDate);
        }
        Page<PartnerStaff> page = partnerStaffRepo.findAll(
                Expressions.allOf(
                        startDate ? QPartnerStaff.partnerStaff.dateCreated.gt(startDate) : null,
                        endDate ? QPartnerStaff.partnerStaff.lastModifiedDate.lt(endDate) : null,
                        QPartnerStaff.partnerStaff.brandAppId.eq(brandAppId),
                        QPartnerStaff.partnerStaff.deleted.in([false, null]),
                        userIds.size() > 0 ? QPartnerStaff.partnerStaff.userId.in(userIds) : null
                ), pageRequest)

        UniResp<UniPageResp<PartnerStaffInfoResp>> resp = new UniResp<UniPageResp<PartnerStaffInfoResp>>()
        resp.data = conversionService.convert(page, UniPageResp)
        page.content.each {
            PartnerStaff partnerStaff ->
                resp.data.content.add(partnerStaffConvert.convertPartnerStaffToResp(partnerStaff))
        }
        resp.status = 200

        return resp
    }

    @Override
//    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('MEMBER_C','MEMBER_U')")
    public UniResp<String> enable(
            String brandAppId,
            String partnerId,
            String id,
            Boolean disabled) {
        partnerService.check()
        PartnerStaff partnerStaff = partnerStaffRepo.findOne(id)
        if (!partnerStaff) {
            UniResp<String> resp = new UniResp<>()
            resp.setStatus(10026);
            resp.setData("会员信息不存在")
            return resp
        }
        partnerStaff.disabled = disabled
        partnerStaffRepo.save(partnerStaff)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

    @Override
    UniResp<PartnerStaffInfoResp> find(
            String brandAppId,
            String partnerId,
            String userId) {
        partnerService.check()
        Assert.notNull(userId, "userId不能为空")
        PartnerStaff partnerStaff = partnerStaffRepo.findOne(
                Expressions.allOf(
                        QPartnerStaff.partnerStaff.deleted.in([false]),
                        QPartnerStaff.partnerStaff.brandAppId.eq(brandAppId),
//                        QPartnerStaff.partnerStaff.partner.id.eq(partnerId),
                        QPartnerStaff.partnerStaff.userId.eq(userId)
                )
        )
        Assert.notNull(partnerStaff, "没有查到该渠道商")
        UniResp<PartnerStaffInfoResp> uniResp = new UniResp<>()
        uniResp.status = 200
        uniResp.setData(partnerStaffConvert.convertPartnerStaffToResp(partnerStaff))
        return uniResp
    }
//
//    @Override
//    public UniResp<String> exportExcel() throws Exception {

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
//    }

    //    @RequestMapping(path = "/upload", method = RequestMethod.POST, produces = MediaType.ALL_VALUE)
//    @ApiOperation(value = "上传会员信息", nickname = "上传会员信息", notes = "上传会员信息")
//    @ResponseBody
//    @Override
//    public UniResp<String> readExcel( MultipartFile file) throws IOException {
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

//        UniResp<String> resp = new UniResp<String>();
//        resp.setStatus(10001);
//        resp.setData("Excel文件读取失败");
//
//        return resp;
//    }

    // public UniResp<Boolean> queryPhone(String phone, String id) {
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
//    }


}
