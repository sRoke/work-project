package net.kingsilk.qh.raffle.server.service;


import net.kingsilk.qh.raffle.domain.QStaff;
import net.kingsilk.qh.raffle.domain.Staff;
import net.kingsilk.qh.raffle.repo.StaffRepo;
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

    }
}
