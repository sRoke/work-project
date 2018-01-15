package net.kingsilk.qh.agency.server.resource.brandApp.staff

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.api.UniPageResp
import net.kingsilk.qh.agency.api.UniResp
import net.kingsilk.qh.agency.api.brandApp.staff.StaffApi
import net.kingsilk.qh.agency.api.brandApp.staff.dto.StaffInfoResp
import net.kingsilk.qh.agency.api.brandApp.staff.dto.StaffMinInfo
import net.kingsilk.qh.agency.api.brandApp.staff.dto.StaffPageReq
import net.kingsilk.qh.agency.api.brandApp.staff.dto.StaffSaveReq
import net.kingsilk.qh.agency.core.PartnerTypeEnum
import net.kingsilk.qh.agency.domain.Partner
import net.kingsilk.qh.agency.domain.QStaff
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.domain.StaffGroup
import net.kingsilk.qh.agency.repo.PartnerRepo
import net.kingsilk.qh.agency.repo.StaffGroupRepo
import net.kingsilk.qh.agency.repo.StaffRepo
import net.kingsilk.qh.agency.server.resource.brandApp.staff.convert.StaffConvert
import net.kingsilk.qh.agency.service.StaffGroupService
import net.kingsilk.qh.oauth.api.UniPage
import net.kingsilk.qh.oauth.api.user.AddUserReq
import net.kingsilk.qh.oauth.api.user.UserApi
import net.kingsilk.qh.oauth.api.user.UserGetResp
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffAddReq
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi
import net.kingsilk.qh.oauth.core.OrgStaffStatusEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.convert.ConversionService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import org.springframework.util.Assert

/**
 *
 */

@Component
public class StaffResource implements StaffApi {

    @Autowired
    StaffRepo staffRepo
    @Autowired
    StaffGroupService staffGroupService
    @Autowired
    StaffGroupRepo staffGroupRepo

    @Autowired
    StaffConvert staffConvert

    @Autowired
    @Qualifier(value = "mvcConversionService")
    ConversionService conversionService

    @Autowired
    UserApi userApi

    @Autowired
    OrgStaffApi orgStaffApi

    @Autowired
    PartnerRepo partnerRepo

    @Override
    UniResp<StaffInfoResp> info(
            String brandAppId,
            String id
    ) {
        Staff staff = staffRepo.findOne(id)
        if (!staff) {
            return new UniResp(status: 10026, message: "员工信息不存在")
        }
        StaffInfoResp infoResp = staffConvert.staffInfoRespConvert(staff)

        staffGroupService.search(null, staff.getId()).each {
            StaffGroup staffGroup ->
                infoResp.staffGroupList.add(staffConvert.staffGroupModelConvert(staffGroup))
        }

        //TODO 需要oauth查其他信息
        net.kingsilk.qh.oauth.api.UniResp<UserGetResp> respUniResp =
                userApi.get(staff.getUserId())
        infoResp.setRealName(respUniResp.data.realName)
        infoResp.setPhone(respUniResp.data.phone)

        return new UniResp<StaffInfoResp>(status: 200, data: infoResp)
    }

    @Override
    UniResp<String> save(
            String brandAppId,
            StaffSaveReq staffSaveReq
    ) {
        Partner partner = partnerRepo.findOneByPartnerTypeEnumAndBrandAppId(PartnerTypeEnum.BRAND_COM, brandAppId)
        // TODO userId 应该先在oauth表里新增一个员工 再根据其userId 新增品牌商员工
        AddUserReq addUserReq = new AddUserReq()
        addUserReq.setPhone(staffSaveReq.getPhone())
        addUserReq.setRealName(staffSaveReq.getRealName())
        net.kingsilk.qh.oauth.api.UniResp<String> resp = userApi.addUser(addUserReq)

        Staff staff = staffRepo.findOne(
                Expressions.allOf(
                        QStaff.staff.brandAppId.eq(brandAppId),
                        QStaff.staff.deleted.in(false),
                        QStaff.staff.userId.eq(resp.getData())
                ))

        if (staff) {
            return new UniResp<String>(status: 10077, data: "该手机号已经被使用")
        }
        staff = new Staff()
        staff.setDisabled(staffSaveReq.disabled)
        staff.setBrandAppId(brandAppId)
        staff.setUserId(resp.getData())

        staff = staffRepo.save(staff)

        //TODO  在oauth的组织表里面新增
        OrgStaffAddReq orgStaffAddReq = new OrgStaffAddReq()
        orgStaffAddReq.setUserId(staff.getUserId())

        //TODO 目前先以staff的id作为orgStaff的工号（orgStaff很多字段都没有）
        orgStaffAddReq.setJobNo(staff.getId())
        orgStaffAddReq.setOrgId(partner.getOrgId())
        orgStaffAddReq.setRealName(staffSaveReq.getRealName())
        orgStaffAddReq.setStatus(OrgStaffStatusEnum.ENABLED)
        net.kingsilk.qh.oauth.api.UniResp<String> uniResp =
                orgStaffApi.add(staff.getUserId(), staff.getBrandAppId(), orgStaffAddReq)
        Assert.notNull(uniResp, "新增组织失败")

        List<StaffGroup> newGroupList = new ArrayList<>()
        for (String groupId : staffSaveReq.staffGroupIds) {
            StaffGroup staffGroup = staffGroupRepo.findOne(groupId)
            LinkedHashSet<Staff> staffs = staffGroup.getStaffs()
            staffs.add(staff)
            staffGroup.setStaffs(staffs)
            newGroupList.add(staffGroup)
        }
        staffGroupRepo.save(newGroupList)
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @Override
    UniResp<String> update(
            String brandAppId,
            String id,
            StaffSaveReq staffSaveReq) {
        Staff staff = staffRepo.findOne(id)

        //TODO 在oauth里面更新userInfo
        AddUserReq addUserReq = new AddUserReq()
        addUserReq.setPhone(staffSaveReq.getPhone())
        addUserReq.setRealName(staffSaveReq.getRealName())
        addUserReq.setUserId(staffSaveReq.getUserId())
        net.kingsilk.qh.oauth.api.UniResp<String> resp = userApi.update(addUserReq)
        Assert.notNull(resp, "更新失败")

        List<StaffGroup> oldGroupList = staffGroupService.search(null, staff.getId())
        for (StaffGroup sg : oldGroupList) {
            sg.staffs.remove(staff)
        }
        staffGroupRepo.save(oldGroupList)
        List<StaffGroup> newGroupList = new ArrayList<>()
        for (String groupId : staffSaveReq?.staffGroupIds) {
            StaffGroup staffGroup = staffGroupRepo.findOne(groupId)
            LinkedHashSet<Staff> staffs = staffGroup.getStaffs()
            if (!staffs.contains(staff)) {
                staffs.add(staff)
                staffGroup.setStaffs(staffs)
                newGroupList.add(staffGroup)
            }
        }
        staffGroupRepo.save(newGroupList)
        staff.setDisabled(staffSaveReq.isDisabled())
        staffRepo.save(staff)
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @Override
    UniResp<UniPageResp<StaffMinInfo>> page(
            String brandAppId,
            StaffPageReq req) {
        PageRequest pageRequest = new PageRequest(req.page, req.size,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))

        List<String> userIds = req.idList

        Page<Staff> staffPage = staffRepo.findAll(
                Expressions.allOf(
                        QStaff.staff.deleted.in([null, false]),
                        QStaff.staff.brandAppId.eq(brandAppId),
                        userIds.size() > 0 ? QStaff.staff.userId.in(userIds) : null,
//                        Expressions.anyOf(
//                                req.keyWord ? QStaff.staff.userId.like("%" + req.keyWord + "%") : null
//                        )
                ), pageRequest)

        List<String> ids = new ArrayList<>()
        staffPage.content.each {
            ids.add(it.userId)
        }

        List<String> sort = new ArrayList<>()
        sort.add("dateCreated,desc")

        //TODO 根据userid去oauth查相关知识
        net.kingsilk.qh.oauth.api.UniResp<UniPage<UserGetResp>> pageUniResp =
                userApi.list(req.size, req.page, sort, ids)

        UniResp<UniPageResp<StaffMinInfo>> resp = new UniResp<UniPageResp<StaffMinInfo>>()
        resp.data = conversionService.convert(staffPage, UniPageResp)
        staffPage.content.each {
            StaffMinInfo staffMinInfo = staffConvert.staffMinInfoConvert(it)
            pageUniResp.data.content.each {
                if (staffMinInfo.getUserId().equals(it.getId())) {
                    staffMinInfo.setRealName(it.getRealName())
                    staffMinInfo.setPhone(it.getPhone())
                }
            }
            resp.data.content.add(staffMinInfo)
        }
        resp.setStatus(200)
        return resp
    }

    @Override
    UniResp<String> enable(
            String brandAppId,
            String id,
            boolean status
    ) {
        Staff staff = staffRepo.findOne(id)
        if (!staff) {
            return new UniResp(status: 10026, data: "员工信息不存在")
        }
        staff.setDisabled(status)
        staffRepo.save(staff)
        return new UniResp<String>(status: 200, data: "操作成功")
    }

//    @ApiOperation(
//            value = "查询手机号是否重复",
//            nickname = "查询手机号是否重复",
//            notes = "查询手机号是否重复"
//    )
//    @Path("/queryPhone")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    UniResp<Boolean> queryPhone(@QueryParam(value = "phone") String phone, @PathParam(value = "id") String id) {
//        Staff staff
//        Boolean isRepeat = false
//        if (id) {
//            staff = staffRepo.findOne(id)
//            if (!staff) {
//                Iterable<Staff> staffs = staffRepo.findAllByPhone(phone)
//                if (staffs.size() > 0) {
//                    isRepeat = true
//                }
//            } else {
//                if (phone == staff.phone) {
//                    isRepeat = false
//                } else {
//                    Iterable<Staff> members = staffRepo.findAllByPhone(phone)
//                    if (members.size() > 0) {
//                        isRepeat = true
//                    }
//                }
//            }
//        } else {
//            Iterable<Staff> staffs = staffRepo.findAllByPhone(phone)
//            if (staffs.size() > 0) {
//                isRepeat = true
//            }
//        }
//        return new UniResp<Boolean>(status: 200, data: isRepeat)
//    }


}
