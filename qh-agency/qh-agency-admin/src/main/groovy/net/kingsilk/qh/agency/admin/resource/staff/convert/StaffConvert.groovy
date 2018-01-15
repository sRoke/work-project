package net.kingsilk.qh.agency.admin.resource.staff.convert

import net.kingsilk.qh.agency.admin.api.staff.dto.StaffInfoResp
import net.kingsilk.qh.agency.admin.api.staff.dto.StaffSaveReq
import net.kingsilk.qh.agency.domain.Staff
import org.springframework.stereotype.Component

/**
 * Created by lit on 17/7/21.
 */
@Component
class StaffConvert {

    def staffSaveReqConvert(StaffSaveReq staffSaveReq,Staff staff){
        staff.realName = staffSaveReq.realName
        staff.phone = staffSaveReq.phone
        staff.disabled = staffSaveReq.disabled
        staff.userId = staffSaveReq.userId
        return staff
    }

    StaffInfoResp staffInfoRespConvert(Staff staff) {
        StaffInfoResp staffInfoResp=new StaffInfoResp()
        staffInfoResp.memo = staff.memo
        staffInfoResp.userId = staff.userId
        staffInfoResp.deleted = staff.deleted
        staffInfoResp.phone = staff.phone
        staffInfoResp.lastModifiedDate = staff.lastModifiedDate
        staffInfoResp.idNumber = staff.idNumber
        staffInfoResp.avatar = staff.avatar
        staffInfoResp.nickName = staff.nickName
        staffInfoResp.realName = staff.realName
        staffInfoResp.disabled = staff.disabled
        staffInfoResp.dateCreated = staff.dateCreated
        return staffInfoResp;
    }
}
