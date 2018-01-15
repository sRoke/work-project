package net.kingsilk.qh.agency.admin.resource.staff

import com.querydsl.core.types.dsl.Expressions
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.kingsilk.qh.agency.admin.api.UniResp
import net.kingsilk.qh.agency.admin.api.staff.StaffApi
import net.kingsilk.qh.agency.admin.api.staff.dto.*
import net.kingsilk.qh.agency.admin.resource.staff.convert.StaffConvert
import net.kingsilk.qh.agency.domain.QStaff
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.domain.StaffGroup
import net.kingsilk.qh.agency.repo.StaffGroupRepo
import net.kingsilk.qh.agency.repo.StaffRepo
import net.kingsilk.qh.agency.service.StaffGroupService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

import javax.ws.rs.*
import javax.ws.rs.core.MediaType

/**
 *
 */
@Api(
        tags = "Staff",
        produces = MediaType.APPLICATION_JSON,
        protocols = "http,https",
        description = "员工管理相关API"
)
@Path("/staff")
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

    @ApiOperation(
            value = "员工信息",
            nickname = "员工信息",
            notes = "员工信息"
    )
    @Path("/info")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<StaffInfoResp> info(@QueryParam(value = "id") String id) {
        Staff staff = staffRepo.findOne(id)
        if (!staff) {
            return new UniResp(status: 10026, message: "员工信息不存在")
        }
        StaffInfoResp infoResp = staffConvert.staffInfoRespConvert(staff);
        infoResp.staffGroupList = staffGroupService.search(null, staff.getId());
        return new UniResp<StaffInfoResp>(status: 200, data: infoResp)
    }

    @ApiOperation(
            value = "保存或更新员工信息",
            nickname = "保存或更新员工信息",
            notes = "保存或更新员工信息"
    )
    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> save(StaffSaveReq staffSaveReq) {
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
            staff = staffConvert.staffSaveReqConvert(staffSaveReq,staff)
//            Company company = companyRepo.findOne(BrandIdFilter.companyId)
//            staff.setCompany(company);
            String userId = new ObjectId();
            staff.setUserId(userId)
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
        return new UniResp<String>(status: 200, data: "保存成功")
    }

    @ApiOperation(
            value = "员工分页信息",
            nickname = "员工分页信息",
            notes = "员工分页信息"
    )
    @Path("/page")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<StaffPageResp> page(@BeanParam StaffPageReq staffPageReq) {
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
//            info.staffGroupList = staffGroupService.search(null,staff.getId()).toList()
            info.disabled = staff.disabled
            info.userId = staff.userId
            return info
        });
        StaffPageResp resp = new StaffPageResp()
        resp.recPage = infoPage
        return new UniResp<StaffPageResp>(status: 200, data: resp)
    }

    @ApiOperation(
            value = "禁用或启用员工",
            nickname = "禁用或启用员工",
            notes = "禁用或启用员工"
    )
    @Path("/enable")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<String> enable(@BeanParam StaffEnableReq staffEnableReq) {
        Staff staff = staffRepo.findOne(staffEnableReq.id);
        if (!staff) {
            return new UniResp(status: 10026, data: "员工信息不存在")
        }
        staff.setDisabled(staffEnableReq.status);
        staffRepo.save(staff);
        return new UniResp<String>(status: 200, data: "操作成功")
    }


    @ApiOperation(
            value = "查询手机号是否重复",
            nickname = "查询手机号是否重复",
            notes = "查询手机号是否重复"
    )
    @Path("/queryPhone")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    UniResp<Boolean> queryPhone(@QueryParam(value = "phone") String phone, @PathParam(value = "id") String id) {
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
