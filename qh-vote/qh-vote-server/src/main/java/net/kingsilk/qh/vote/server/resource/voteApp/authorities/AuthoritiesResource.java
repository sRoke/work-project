package net.kingsilk.qh.vote.server.resource.voteApp.authorities;

import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.authorities.AuthoritiesApi;
import net.kingsilk.qh.vote.domain.Staff;
import net.kingsilk.qh.vote.repo.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class AuthoritiesResource implements AuthoritiesApi {

    @Autowired
    private StaffRepo staffRepo;

    @Override
    public UniResp<Set<String>> getAuthorities(String voteAppId) {
        return null;
    }

    @Override
    public UniResp<String> setSAStaff(String voteAppId, String userId, String orgId) {
        Staff staff = new Staff();
        staff.setUserId(userId);
        Set<String> set = new LinkedHashSet<>();
        set.add("SA");
        staff.setAuthorities(set);
        staff.setVoteAppId(voteAppId);
        staffRepo.save(staff);
        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(staff.getId());
        return uniResp;
    }
}
