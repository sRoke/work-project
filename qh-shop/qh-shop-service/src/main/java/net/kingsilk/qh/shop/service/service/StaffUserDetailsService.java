package net.kingsilk.qh.shop.service.service;

import net.kingsilk.qh.shop.core.AuthorityEnum;
import net.kingsilk.qh.shop.domain.QShopStaff;
import net.kingsilk.qh.shop.domain.QStaff;
import net.kingsilk.qh.shop.domain.ShopStaff;
import net.kingsilk.qh.shop.domain.Staff;
import net.kingsilk.qh.shop.repo.ShopStaffRepo;
import net.kingsilk.qh.shop.repo.StaffRepo;
import net.kingsilk.qh.shop.service.security.BrandAppIdFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.querydsl.core.types.dsl.Expressions.allOf;


@Component
public class StaffUserDetailsService implements UserDetailsService {
    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private ShopStaffRepo shopStaffRepo;

    public StaffUserDetailsService(StaffRepo staffRepo, ShopStaffRepo shopStaffRepo, AuthorityService authorityService) {
        this.staffRepo = staffRepo;
        this.shopStaffRepo = shopStaffRepo;
        this.authorityService = authorityService;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {


        List<GrantedAuthority> authorityList = new LinkedList<>();

        BrandAppIdFilter brandAppIdFilter = new BrandAppIdFilter();
        String brandAppId = brandAppIdFilter.getBrandAppId();
        String shopId = brandAppIdFilter.getShopId();

        Staff staff = null;

        ShopStaff shopStaff = null;

        if (StringUtils.hasText(brandAppId)) {
            staff = staffRepo.findOne(allOf(
                    QStaff.staff.brandAppId.eq(brandAppId),
                    QStaff.staff.userId.eq(userId),
                    QStaff.staff.enable.ne(false),
                    QStaff.staff.deleted.ne(true)
            ));

        }
        if (StringUtils.hasText(shopId)) {
            shopStaff = shopStaffRepo.findOne(
                    allOf(
                            QShopStaff.shopStaff.deleted.ne(true),
                            QShopStaff.shopStaff.userId.eq(userId),
                            QShopStaff.shopStaff.shopId.eq(shopId),
                            QShopStaff.shopStaff.enable.ne(false),
                            QShopStaff.shopStaff.brandAppId.eq(brandAppId)
                    )
            );
        }
        Set<String> authoritySet = new HashSet<>();

        if (staff != null) {

            //TODO  权限按分配的来
            // 自动添加 STAFF 权限
            if (!authoritySet.contains(AuthorityEnum.STAFF.name())) {
                authoritySet.add(AuthorityEnum.STAFF.name());
            }

            authoritySet.add(AuthorityEnum.SA.name());
        }
        if (shopStaff != null) {
            authoritySet = authorityService.getAuthorities(shopStaff);

            if (!authoritySet.contains(AuthorityEnum.SHOPSTAFF.name())) {
                authoritySet.add(AuthorityEnum.SHOPSTAFF.name());
            }

        }
        if (!authoritySet.isEmpty()) {
            List<GrantedAuthority> list =
                    AuthorityUtils.createAuthorityList((String[]) authoritySet.toArray(new String[authoritySet.size()]));
            authorityList.addAll(list);
        }
        User.UserBuilder userBuilder = User.withUsername(userId);
        userBuilder.password("N/A");

        userBuilder.authorities(authorityList);

        return userBuilder.build();
    }
}
