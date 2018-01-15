package net.kingsilk.qh.agency.server.resource.brandApp.staff.convert

import net.kingsilk.qh.agency.api.common.dto.StaffGroupModel
import net.kingsilk.qh.agency.api.common.dto.StaffModel
import net.kingsilk.qh.agency.api.brandApp.staff.dto.StaffInfoResp
import net.kingsilk.qh.agency.api.brandApp.staff.dto.StaffMinInfo
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.domain.StaffGroup
import net.kingsilk.qh.agency.service.StaffGroupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/21.
 */
@Component
class StaffConvert {

    @Autowired
    StaffGroupService staffGroupService

    StaffInfoResp staffInfoRespConvert(Staff staff) {
        StaffInfoResp staffInfoResp = new StaffInfoResp()
        staffInfoResp.memo = staff.memo
        staffInfoResp.userId = staff.userId
        staffInfoResp.deleted = staff.deleted
//        staffInfoResp.phone = staff.phone
        staffInfoResp.lastModifiedDate = staff.lastModifiedDate
//        staffInfoResp.idNumber = staff.idNumber
//        staffInfoResp.avatar = staff.avatar
//        staffInfoResp.nickName = staff.nickName
//        staffInfoResp.realName = staff.realName
        staffInfoResp.disabled = staff.disabled
        staffInfoResp.dateCreated = staff.dateCreated
        return staffInfoResp;
    }

    StaffMinInfo staffMinInfoConvert(Staff staff) {
        StaffMinInfo info = new StaffMinInfo()
        info.id = staff.id
        info.memo = staff.memo
        info.dateCreated = staff.dateCreated
        info.createdBy = staff.createdBy
        info.staffGroupList = staffGroupService.search(null, staff.getId()).toList()
        info.disabled = staff.disabled
        info.userId = staff.userId
        return info
    }

    StaffGroupModel staffGroupModelConvert(StaffGroup staffGroup) {
        StaffGroupModel groupModel = new StaffGroupModel()
        groupModel.setStatus(staffGroup.getStatus())
        groupModel.setName(staffGroup.getName())
        groupModel.setDesp(staffGroup.getDesp())
        groupModel.setReserved(staffGroup.getReserved())
        groupModel.setAuthorities(staffGroup.getAuthorities())
        groupModel.setBrandAppId(staffGroup.getBrandAppId())
        groupModel.setId(staffGroup.getId())
//        staffGroup.getStaffs().each {
//            Staff staff ->
//                groupModel.getStaffs().add(staffModelConvert(staff))
//        }
        return groupModel
    }

    StaffModel staffModelConvert(Staff staff) {
        StaffModel staffModel = new StaffModel()
        staffModel.setBrandAppId(staff.getBrandAppId())
        staffModel.setDisabled(staff.isDisabled())
        staffModel.setAuthorities(staff.getAuthorities())
        staffModel.setMemo(staff.getMemo())
        staffModel.setUserId(staff.getUserId())
        return staffModel
    }

}
