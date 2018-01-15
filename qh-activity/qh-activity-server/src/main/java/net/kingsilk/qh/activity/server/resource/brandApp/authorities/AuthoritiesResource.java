package net.kingsilk.qh.activity.server.resource.brandApp.authorities;

import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.authorities.AuthoritiesApi;
import net.kingsilk.qh.activity.domain.Staff;
import net.kingsilk.qh.activity.repo.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class AuthoritiesResource implements AuthoritiesApi {

    @Autowired
    private StaffRepo staffRepo;

    @Override
    public UniResp<Set<String>> getAuthorities(String brandAppId) {
        return null;
    }

    @Override
    public UniResp<String> setSAStaff(String brandAppId, String userId, String orgId) {
        Staff staff = new Staff();
        staff.setUserId(userId);
        Set<String> set = new LinkedHashSet<>();
        set.add("SA");
        staff.setAuthorities(set);
        staff.setBrandAppId(brandAppId);
        staffRepo.save(staff);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(staff.getId());
        return uniResp;
    }
}
