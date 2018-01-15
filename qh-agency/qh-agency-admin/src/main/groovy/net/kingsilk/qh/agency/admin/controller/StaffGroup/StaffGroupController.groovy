package net.kingsilk.qh.agency.admin.controller.StaffGroup

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.core.StaffAuthorityEnum
import net.kingsilk.qh.agency.domain.*
import net.kingsilk.qh.agency.repo.CompanyRepo
import net.kingsilk.qh.agency.repo.StaffGroupRepo
import net.kingsilk.qh.agency.repo.StaffRepo
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import net.kingsilk.qh.agency.service.SecService
import net.kingsilk.qh.agency.service.StaffAuthorityService
import net.kingsilk.qh.agency.service.StaffGroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.MediaType
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.*

/**
 * Created by lit on 17-3-30.
 */
@RestController()
@RequestMapping("/api/staffGroup")
@Api( // 用在类上，用于设置默认值
        tags = "staffGroup",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
        protocols = "http,https",
        description = "角色管理相关API"
)
class StaffGroupController {
    @Autowired
    CompanyRepo companyRepo

    @Autowired
    StaffGroupRepo staffGroupRepo

    @Autowired
    StaffRepo staffRepo
    @Autowired
    SecService secService

    @Autowired
    StaffGroupService staffGroupService

    @Autowired
    StaffAuthorityService staffAuthorityService

    @RequestMapping(path = "/currentUserInfo",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "当前用户的权限信息",
            nickname = "当前用户的权限信息",
            notes = "当前用户的权限信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
    UniResp<StaffGroupLoadCurrentUserResp> currentUserInfo(String id, String source) {

        Staff staff
        Set<String> authoritySet
        String userId = secService.curUserId();
        String companyId = BrandIdFilter.getCompanyId()
        Assert.notNull(companyId, "公司 ${companyId} 不存在")
        Company company = companyRepo.findOne(companyId)
        Assert.notNull(company, "公司 ${companyId} 不存在")
        staff = staffRepo.findOne(
                Expressions.allOf(
                        QStaff.staff.userId.eq(userId),
                        QStaff.staff.company.eq(company),
                        QStaff.staff.deleted.in([false, null])
                )
        )
        Assert.notNull(staff, "员工 ${userId} 不存在")
        authoritySet = staffAuthorityService.getAuthorities(staff)
        if (authoritySet.contains(StaffAuthorityEnum.SA.name())) {
            staffAuthorityService.fillAuth(authoritySet, StaffAuthorityEnum.SA)
        }

        StaffGroupLoadCurrentUserResp Resp = new StaffGroupLoadCurrentUserResp().convertToResp(source, staff.getId(), authoritySet)
        return new UniResp(status: 200, data: Resp);
    }

    @RequestMapping(path = "/info",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "当前正在编辑用户的权限信息",
            nickname = "当前正在编辑用户的权限信息",
            notes = "当前正在编辑用户的权限信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('STAFF_GROUP_C','STAFF_GROUP_U','STAFF_GROUP_R')")
    UniResp<StaffGroupInfoResp> info(String id, String source) {
        StaffGroup staffGroup = staffGroupRepo.findOne(id);
        if (!staffGroup) {
            return new UniResp(status: 10027, message: "角色信息不存在")
        }
        StaffGroupInfoResp infoResp = new StaffGroupInfoResp();
        infoResp.convertStaffGroupToResp(source, staffGroup)
        return new UniResp<StaffGroupInfoResp>(status: 200, data: infoResp)

    }


    @RequestMapping(path = "/save",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "保存或更新角色信息",
            nickname = "保存或更新角色信息",
            notes = "保存或更新角色信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('STAFF_GROUP_C','STAFF_GROUP_U','STAFF_GROUP_R')")
    UniResp<String> save(@RequestBody StaffGroupSaveReq staffGroupSaveReq) {

        String companyId = BrandIdFilter.getCompanyId()
        Company company = companyRepo.findOne(companyId)
        Assert.notNull(company, "公司 ${companyId} 不存在")
        Assert.notNull(staffGroupSaveReq.name, "名称为必填字段")
        Assert.notNull(staffGroupSaveReq.status, "状态为必填字段")
        StaffGroup staffGroup
        if (staffGroupSaveReq.getId() != null) {
            staffGroup = staffGroupRepo.findOne(staffGroupSaveReq.getId())
        }
        if (staffGroup == null) {
            staffGroup = new StaffGroup();
        }
        staffGroup.company = company

        staffGroup = staffGroupSaveReq.convertToStaffGroup(staffGroup)
        staffGroupRepo.save(staffGroup)
        return new UniResp<StaffGroupInfoResp>(status: 200, data: "保存成功")
    }

    @RequestMapping(path = "/page",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "角色分页信息",
            nickname = "角色分页信息",
            notes = "角色分页信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('STAFF_GROUP_R')")
    UniResp<StaffGroupPageResp> page(StaffGroupPageReq staffGroupPageReq) {
        PageRequest pageRequest = new PageRequest(staffGroupPageReq.curPage, staffGroupPageReq.pageSize,
                new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        Page<StaffGroup> page = staffGroupRepo.findAll(
                Expressions.allOf(
                        staffGroupPageReq.keyWord ? QStaffGroup.staffGroup.name.contains(staffGroupPageReq.keyWord) : null,
                        QStaffGroup.staffGroup.deleted.in([null, false]),
                ), pageRequest)
        Page<StaffGroupPageResp.StaffGroupMini> infoPage = page.map({ StaffGroup staffGroup ->
            StaffGroupPageResp.StaffGroupMini info = new StaffGroupPageResp.StaffGroupMini();
            info.id = staffGroup.id
            info.name = staffGroup.name
            info.lastModifiedDate = staffGroup.lastModifiedDate
            info.staffSize = staffGroup.staffs.size()
            info.status = staffGroup.status
            return info
        })
        StaffGroupPageResp resp = new StaffGroupPageResp()
        resp.recPage = infoPage
        return new UniResp(status: 200, data: resp);
    }

    @RequestMapping(path = "/load",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "保存或更新角色信息",
            nickname = "保存或更新角色信息",
            notes = "加载角色信息，如果来源于新建页面，则staffId随便传source传入页面来源类型，如果来自编辑页面，则需要调用该方法两次"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('STAFF_GROUP_R')")
    UniResp<StaffGroupLoadResp> load() {


        Map<String, Map<String, Map<String, String>>> authorMap = staffGroupService.getAuthorMap()

        StaffGroupLoadResp staffGroupLoadResp = new StaffGroupLoadResp().convertToResp(authorMap)
        return new UniResp(status: 200, data: staffGroupLoadResp);
    }


    @RequestMapping(path = "/delete",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "删除角色",
            nickname = "删除角色",
            notes = "删除角色"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('STAFF_GROUP_D')")
    UniResp<String> delete(String id) {
        StaffGroup staffGroup
        if (id != null) {
            staffGroup = staffGroupRepo.findOne(id)
        }
        Assert.notNull(staffGroup, "角色 ${id} 不存在")

        staffGroup.deleted = true
        staffGroupRepo.save(staffGroup)
        return new UniResp(status: 200, data: "删除成功");
    }

    @RequestMapping(path = "/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "员工组信息",
            nickname = "员工组信息",
            notes = "员工组信息"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF')")
    UniResp search(String keywords) {
        List<StaffGroup> list = staffGroupService.search(keywords,null).toList();
        return new UniResp(status: 200, data: list);
    }

    /**
     * 根据当前登录用户,获取当前登录人的权限列表
     * @return
     */
    @RequestMapping(path = "/currentAuth",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "根据当前登录用户,获取当前登录人的权限列表",
            nickname = "根据当前登录用户,获取当前登录人的权限列表",
            notes = "根据当前登录用户,获取当前登录人的权限列表"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('STAFF_GROUP_U','STAFF_GROUP_C')")
    Set<String> currentAuth() {
        Staff staff
        Set<String> authoritySet
        String userId = secService.curUserId();
        String companyId = BrandIdFilter.getCompanyId()
        Assert.notNull(companyId, "公司 ${companyId} 不存在")
        Company company = companyRepo.findOne(companyId)
        Assert.notNull(company, "公司 ${companyId} 不存在")
        staff = staffRepo.findOne(
                Expressions.allOf(
                        QStaff.staff.userId.eq(userId),
                        QStaff.staff.company.eq(company),
                        QStaff.staff.deleted.in([false, null])
                )
        )
        Assert.notNull(staff, "员工 ${userId} 不存在")
        authoritySet = staffAuthorityService.getAuthorities(staff);
        if (authoritySet.contains(StaffAuthorityEnum.SA.name())){
            staffAuthorityService.fillAuth(authoritySet,StaffAuthorityEnum.SA)
        }
        return authoritySet
    }

    @RequestMapping(path = "/enable",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(
            value = "禁用或启用角色",
            nickname = "禁用或启用角色",
            notes = "禁用或启用角色"
    )
    @ResponseBody
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF') && hasAnyAuthority('STAFF_GROUP_U','STAFF_GROUP_C')")
    UniResp<String> enable(String id, String status) {
        StaffGroup staffGroup = staffGroupRepo.findOne(id)
        if (!staffGroup) {
            return new UniResp(status: 10026, data: "角色信息不存在")
        }
        staffGroup.status = Boolean.valueOf(status)
        staffGroupRepo.save(staffGroup);
        return new UniResp<String>(status: 200, data: "操作成功")
    }
}
