package net.kingsilk.qh.platform.server.resource.staffGroup.convert;

import net.kingsilk.qh.platform.api.staffGroup.dto.StaffGroupInfoResp;
import net.kingsilk.qh.platform.domain.StaffGroup;
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
