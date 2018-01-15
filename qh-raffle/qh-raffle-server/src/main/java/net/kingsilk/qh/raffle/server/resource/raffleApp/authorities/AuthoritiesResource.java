package net.kingsilk.qh.raffle.server.resource.raffleApp.authorities;

import net.kingsilk.qh.raffle.api.common.UniResp;
import net.kingsilk.qh.raffle.api.raffleApp.authorities.AuthoritiesApi;
import net.kingsilk.qh.raffle.domain.Staff;
import net.kingsilk.qh.raffle.repo.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class AuthoritiesResource implements AuthoritiesApi {

    @Autowired
    private StaffRepo staffRepo;

    @Override
    public UniResp<Set<String>> getAuthorities(String raffleAppId) {
        return null;
    }

    @Override
    public UniResp<String> setSAStaff(String raffleAppId, String userId, String orgId) {
        Staff staff = new Staff();
        staff.setUserId(userId);
        Set<String> set = new LinkedHashSet<>();
        set.add("SA");
        staff.setAuthorities(set);
        staff.setraffleAppId(raffleAppId);
        staffRepo.save(staff);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(staff.getId());
        return uniResp;
    }
}
