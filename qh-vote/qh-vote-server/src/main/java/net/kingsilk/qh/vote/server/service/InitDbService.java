package net.kingsilk.qh.vote.server.service;


import net.kingsilk.qh.vote.domain.QStaff;
import net.kingsilk.qh.vote.domain.Staff;
import net.kingsilk.qh.vote.repo.StaffRepo;
import net.kingsilk.qh.vote.repo.VoteAppRepo;
import net.kingsilk.qh.vote.repo.VoteWorksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class InitDbService {
    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private VoteAppRepo voteAppRepo;

    @Autowired
    private VoteWorksRepo voteWorksRepo;


    @EventListener
    void onApplicationStated(ContextRefreshedEvent event) {
        initAdminUser();
    }

    private void initAdminUser() {
        Staff user = staffRepo.findOne(
                QStaff.staff.userId.eq("58de6b27785a82000005a140")
        );
        if (user == null) {
            user = new Staff();
            user.setMemo("admin");
            user.setDisabled(true);
            user.setUserId("58de6b27785a82000005a140");
            Set<String> stringSet = new LinkedHashSet<>();
            stringSet.add("SA");
            user.setAuthorities(stringSet);
            staffRepo.save(user);
        }
//        VoteWorks voteWorks = new VoteWorks();
//        voteWorksRepo.save(voteWorks);
//        VoteApp voteApp = new VoteApp();
//        voteApp.setBrandAppId("5a3488e846e0fb00083bd03e");
//        voteApp.setShopId("5a348951e828860007d787e0");
//        voteAppRepo.save(voteApp);
    }
}
