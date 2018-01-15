package net.kingsilk.qh.shop.server.resource.brandApp.staffGroup.convert;


import net.kingsilk.qh.shop.api.brandApp.staffGroup.dto.StaffGroupInfoResp;
import net.kingsilk.qh.shop.domain.StaffGroup;
import org.springframework.stereotype.Component;

@Component
public class StaffGroupConvert {

    public StaffGroupInfoResp staffGroupConvert(StaffGroup staffGroup) {
        StaffGroupInfoResp staffGroupInfoResp = new StaffGroupInfoResp();
        staffGroupInfoResp.setName(staffGroup.getName());
        staffGroupInfoResp.setDesp(staffGroup.getDesp());
        staffGroupInfoResp.setAuthorities(staffGroup.getAuthorities());
        return staffGroupInfoResp;
    }
}
