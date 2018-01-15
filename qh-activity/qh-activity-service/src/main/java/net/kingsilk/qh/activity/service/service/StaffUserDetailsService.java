package net.kingsilk.qh.activity.service.service;

import net.kingsilk.qh.activity.domain.QStaff;
import net.kingsilk.qh.activity.domain.Staff;
import net.kingsilk.qh.activity.repo.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.querydsl.core.types.dsl.Expressions.allOf;

@Service
public class StaffUserDetailsService implements UserDetailsService {

    @Autowired
    private StaffRepo staffRepo;
//    @Autowired
//    private StaffAuthorityService staffAuthorityService;
//
//    public StaffService(StaffRepo staffRepo, StaffAuthorityService staffAuthorityService) {
//        this.staffRepo = staffRepo;
//        this.staffAuthorityService = staffAuthorityService;
//    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
//        List<GrantedAuthority> authorityList = new ArrayList<>();

        Staff staff;

        staff = staffRepo.findOne(allOf(
                QStaff.staff.userId.eq(userId),
                QStaff.staff.deleted.ne(true)
        ));


//        if (staff != null) {
//            authorityList.add("SA");
//        }
//            Set<String> authoritySet = staffAuthorityService.getAuthorities(staff);
//
//            //TODO  权限按分配的来
//
//            if (!authoritySet.contains(StaffAuthorityEnum.STAFF.name())) {
//                authoritySet.add(StaffAuthorityEnum.STAFF.name());
//            }
//            if (staff.getAuthorities().contains("SA")) {
//                staffAuthorityService.fillAuth(authoritySet, StaffAuthorityEnum.SA);
//            }
//            List<GrantedAuthority> list =
//                    AuthorityUtils.createAuthorityList(authoritySet.toArray(new String[]{}));
//            authorityList.addAll(list);
//        }
        User.UserBuilder userBuilder = User.withUsername(userId);
        userBuilder.password("N/A");

        if (staff != null) {
            userBuilder.authorities("SA");
        }else {
            userBuilder.authorities("user");
        }
        return userBuilder.build();
    }
}
