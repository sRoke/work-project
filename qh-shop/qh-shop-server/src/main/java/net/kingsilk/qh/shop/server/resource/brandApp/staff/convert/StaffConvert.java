package net.kingsilk.qh.shop.server.resource.brandApp.staff.convert;

import net.kingsilk.qh.shop.api.brandApp.staff.dto.StaffInfoResp;
import net.kingsilk.qh.shop.domain.Staff;
import org.springframework.stereotype.Component;

@Component
public class StaffConvert {


    public StaffInfoResp staffInfoRespConvert(Staff staff) {
        StaffInfoResp staffInfoResp = new StaffInfoResp();

        staffInfoResp.setMemo(staff.getMemo());
        staffInfoResp.setUserId(staff.getUserId());
        staffInfoResp.setLastModifiedDate(staff.getLastModifiedDate());
        staffInfoResp.setEnable(staff.getEnable());
        staffInfoResp.setDateCreated(staff.getDateCreated());
        return staffInfoResp;
    }

}
