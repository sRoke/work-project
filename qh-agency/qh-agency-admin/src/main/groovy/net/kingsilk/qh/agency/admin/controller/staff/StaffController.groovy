package net.kingsilk.qh.agency.admin.controller.staff

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.domain.Company
import net.kingsilk.qh.agency.domain.QStaff
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.domain.StaffGroup
import net.kingsilk.qh.agency.repo.CompanyRepo
import net.kingsilk.qh.agency.repo.StaffGroupRepo
import net.kingsilk.qh.agency.repo.StaffRepo
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import net.kingsilk.qh.agency.service.CommonService
import net.kingsilk.qh.agency.service.StaffGroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

/**
 * Created by tpx on 17-3-20.
 */
@RestController()
@RequestMapping("/api/staff")
@Api( // 用在类上，用于设置默认值
        tags = "Staff",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "员工管理相关API"
)
class StaffController {

    @Autowired
    StaffRepo staffRepo

    @Autowired
    StaffGroupRepo staffGroupRepo

    @Autowired
    CompanyRepo companyRepo

    @Autowired
    CommonService commonService

    @Autowired
    StaffGroupService staffGroupService

    @RequestMapping(path = "/info",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "员工信息",
            nickname = "员工信息",
            notes = "员工信息"
    )
    @ApiParam(value = "id")
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('STAFF_R')")
    UniResp<StaffInfoResp> info(String id) {
        Staff staff = staffRepo.findOne(id)
        if (!staff) {
            return new UniResp(status: 10026, message: "员工信息不存在")
        }
        StaffInfoResp infoResp = new StaffInfoResp().convertToResp(staff);
        infoResp.staffGroupList = staffGroupService.search(null, staff.getId());
        return new UniResp<StaffInfoResp>(status: 200, data: infoResp)
    }

    @RequestMapping(path = "/save",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "保存或更新员工信息",
            nickname = "保存或更新员工信息",
            notes = "保存或更新员工信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('STAFF_C','STAFF_U')")
    UniResp<String> save(@RequestBody StaffSaveReq staffSaveReq) {
        Staff staff = null;
        if (staffSaveReq.id != null) {
            staff = staffRepo.findOne(staffSaveReq.id)
            staff.setDisabled(staffSaveReq.disabled)
            List<StaffGroup> oldGroupList = staffGroupService.search(null, staff.getId());
            for (StaffGroup sg : oldGroupList) {
                sg.staffs.remove(staff);
            }
            staffGroupRepo.save(oldGroupList);
        }
        if (!staff) {
            staff = new Staff();
            staff = staffSaveReq.convertReqToStaff(staff)
            Company company = companyRepo.findOne(BrandIdFilter.companyId)
            staff.setCompany(company);
//            String userId = Math.round(Math.random() * 100)
//            staff.setUserId(userId)
        }
        staffRepo.save(staff);
        List<StaffGroup> newGroupList = new ArrayList<>();
        for (String groupId : staffSaveReq.staffGroupIds) {
            StaffGroup staffGroup = staffGroupRepo.findOne(groupId);
            LinkedHashSet<Staff> staffs = staffGroup.getStaffs();
            if (!staffs.contains(staff)) {
                staffs.add(staff)
                staffGroup.setStaffs(staffs);
                newGroupList.add(staffGroup);
            }
        }
        staffGroupRepo.save(newGroupList);
        return new UniResp<StaffInfoResp>(status: 200, data: "保存成功")
    }

    @RequestMapping(path = "/page",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "员工分页信息",
            nickname = "员工分页信息",
            notes = "员工分页信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('STAFF_R')")
    UniResp<StaffPageResp> page(StaffPageReq staffPageReq) {
        PageRequest pageRequest = new PageRequest(staffPageReq.curPage, staffPageReq.pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))

        Page<Staff> staffPage = staffRepo.findAll(
                Expressions.allOf(
                        QStaff.staff.deleted.in([null, false]),
                        Expressions.anyOf(
                                staffPageReq.keyWord ? QStaff.staff.realName.contains(staffPageReq.keyWord) : null,
                                staffPageReq.keyWord ? QStaff.staff.nickName.contains(staffPageReq.keyWord) : null,
                                staffPageReq.keyWord ? QStaff.staff.userId.contains(staffPageReq.keyWord) : null,
                                staffPageReq.keyWord ? QStaff.staff.phone.contains(staffPageReq.keyWord) : null
                        )
                ), pageRequest)

        Page<StaffPageResp.StaffMinInfo> infoPage = staffPage.map({ Staff staff ->
            StaffPageResp.StaffMinInfo info = new StaffPageResp.StaffMinInfo();
            info.id = staff.id
            info.memo = staff.memo
            info.idNumber = staff.idNumber
            info.avatar = staff.avatar
            info.nickName = staff.nickName
            info.realName = staff.realName
            info.phone = staff.phone
            info.dateCreated = staff.dateCreated
            info.createdBy = staff.createdBy
            info.staffGroupList = staffGroupService.search(null,staff.getId()).toList()
            info.disabled = staff.disabled
            info.userId = staff.userId
            return info
        });
        StaffPageResp resp = new StaffPageResp()
        resp.recPage = infoPage
        return new UniResp<StaffPageResp<Page<StaffPageResp.StaffMinInfo>>>(status: 200, data: resp)
    }

    @RequestMapping(path = "/enable",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "禁用或启用员工",
            nickname = "禁用或启用员工",
            notes = "禁用或启用员工"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('STAFF_C','STAFF_U')")
    UniResp<String> enable(StaffEnableReq staffEnableReq) {
        Staff staff = staffRepo.findOne(staffEnableReq.id);
        if (!staff) {
            return new UniResp(status: 10026, data: "员工信息不存在")
        }
        staff.setDisabled(!staff.disabled);
        staffRepo.save(staff);
        return new UniResp<StaffInfoResp>(status: 200, data: "操作成功")
    }


    @RequestMapping(path = "/queryPhone",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "查询手机号是否重复",
            nickname = "查询手机号是否重复",
            notes = "查询手机号是否重复"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
    UniResp<Boolean> queryPhone(String phone, String id) {
        Staff staff
        Boolean isRepeat = false
        if (id) {
            staff = staffRepo.findOne(id)
            if (!staff) {
                Iterable<Staff> staffs = staffRepo.findAllByPhone(phone)
                if (staffs.size() > 0) {
                    isRepeat = true
                }
            } else {
                if (phone == staff.phone) {
                    isRepeat = false
                } else {
                    Iterable<Staff> members = staffRepo.findAllByPhone(phone)
                    if (members.size() > 0) {
                        isRepeat = true
                    }
                }
            }
        }else{
            Iterable<Staff> staffs = staffRepo.findAllByPhone(phone)
            if (staffs.size() > 0) {
                isRepeat = true
            }
        }
        return new UniResp<Boolean>(status: 200, data: isRepeat)
    }
}
