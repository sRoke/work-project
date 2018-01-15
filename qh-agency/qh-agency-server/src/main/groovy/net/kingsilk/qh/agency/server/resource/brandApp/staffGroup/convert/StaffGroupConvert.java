package net.kingsilk.qh.agency.server.resource.brandApp.staffGroup.convert;

import net.kingsilk.qh.agency.api.brandApp.staffGroup.dto.StaffGroupInfoResp;
import net.kingsilk.qh.agency.domain.StaffGroup;
import org.springframework.stereotype.Component;

@Component
public class StaffGroupConvert {

    public StaffGroupInfoResp staffGroupConvert(StaffGroup staffGroup) {
        StaffGroupInfoResp staffGroupInfoResp = new StaffGroupInfoResp();
        staffGroupInfoResp.setName(staffGroup.getName());
        staffGroupInfoResp.setDesp(staffGroup.getDesp());
        staffGroupInfoResp.setAuthor(staffGroup.getAuthorities());
        return staffGroupInfoResp;
    }
}
