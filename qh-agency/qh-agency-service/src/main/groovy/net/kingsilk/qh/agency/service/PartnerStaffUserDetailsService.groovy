package net.kingsilk.qh.agency.service

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.repo.PartnerRepo
import net.kingsilk.qh.agency.repo.PartnerStaffRepo
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

//@Service
@CompileStatic
class PartnerStaffUserDetailsService implements UserDetailsService {

    @Autowired
    PartnerStaffRepo partnerStaffRepo

    @Autowired
    PartnerRepo partnerRepo

    @Autowired
    StaffAuthorityService staffAuthorityService

    PartnerStaffUserDetailsService(
            PartnerStaffRepo partnerStaffRepo,
            PartnerRepo partnerRepo,
            StaffAuthorityService staffAuthorityService
    ) {
        this.partnerStaffRepo = partnerStaffRepo;
        this.partnerRepo = partnerRepo
        this.staffAuthorityService = staffAuthorityService
    }

/**
 *
 * @param username userId.
 * @return
 * @throws UsernameNotFoundException
 */
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User.UserBuilder userBuilder = User.withUsername(username)
        userBuilder.password("N/A")

        List<GrantedAuthority> authorityList = []

        String brandAppId = BrandAppIdFilter.getBrandAppId()



        println("--------------------来获取权限了,${authorityList}")
        userBuilder.authorities(authorityList)
        return userBuilder.build()
    }
}
