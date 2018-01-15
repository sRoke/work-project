package net.kingsilk.qh.agency.service;

import net.kingsilk.qh.agency.core.StaffAuthorityEnum;
import net.kingsilk.qh.agency.domain.QStaff;
import net.kingsilk.qh.agency.domain.Staff;
import net.kingsilk.qh.agency.repo.PartnerRepo;
import net.kingsilk.qh.agency.repo.PartnerStaffRepo;
import net.kingsilk.qh.agency.repo.StaffRepo;
import net.kingsilk.qh.agency.security.BrandAppIdFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.querydsl.core.types.dsl.Expressions.allOf;

/**
 *
 */
@Service
public class StaffUserDetailsService implements UserDetailsService {

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private PartnerStaffRepo partnerStaffRepo;


    @Autowired
    private StaffAuthorityService staffAuthorityService;

    @Autowired
    private PartnerRepo partnerRepo;

    public StaffUserDetailsService(StaffRepo staffRepo, PartnerStaffRepo partnerStaffRepo, StaffAuthorityService staffAuthorityService) {
        this.staffRepo = staffRepo;
        this.partnerStaffRepo = partnerStaffRepo;
        this.staffAuthorityService = staffAuthorityService;
    }

    /**
     *
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        List<GrantedAuthority> authorityList = new LinkedList<>();

        // 如果 http 请求头中有 "Company-Id", 则查找相应的员工，并更新员工的权限。
//        Company company
//        if (companyId) {
//            company = companyRepo.findOne(companyId)
//        }
        String brandAppId = BrandAppIdFilter.getBrandAppId();

        Staff staff = null;
        if (StringUtils.hasText(brandAppId)) {
            staff = staffRepo.findOne(allOf(
//                    QStaff.staff.company.eq(company),
                    QStaff.staff.brandAppId.eq(brandAppId),
                    QStaff.staff.userId.eq(userId),
                    QStaff.staff.deleted.eq(false)
            ));

        }


//        partnerStaff = partnerStaffRepo.findOne(
//                allOf(
//                        QPartnerStaff.partnerStaff.userId.eq(userId),
//                        QPartnerStaff.partnerStaff.deleted.in(false)
//                ))
//
//        Assert.notNull(staff || partnerStaff, "该员工不存在")

//        }
//        Assert.isTrue(brandId==staff.getBrandAppId(),"该员工不属于该品牌商")

        if (staff != null) {
            Set<String> authoritySet = staffAuthorityService.getAuthorities(staff);

            //TODO  权限按分配的来
            // 自动添加 STAFF 权限
            if (!authoritySet.contains(StaffAuthorityEnum.STAFF.name())) {
                authoritySet.add(StaffAuthorityEnum.STAFF.name());
            }
            if (staff.getAuthorities().contains("SA")) {
                staffAuthorityService.fillAuth(authoritySet, StaffAuthorityEnum.SA);
            }

            List<GrantedAuthority> list = AuthorityUtils.createAuthorityList((String[]) authoritySet.toArray(new String[authoritySet.size()]));
            authorityList.addAll(list);

        }
        User.UserBuilder userBuilder = User.withUsername(userId);
        userBuilder.password("N/A");

        userBuilder.authorities(authorityList);

        return userBuilder.build();
    }
}
