package net.kingsilk.qh.shop.server.resource.brandApp.authorities;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.authorities.AuthoritiesApi;
import net.kingsilk.qh.shop.core.AuthorityEnum;
import net.kingsilk.qh.shop.domain.*;
import net.kingsilk.qh.shop.repo.ShopStaffRepo;
import net.kingsilk.qh.shop.repo.StaffGroupRepo;
import net.kingsilk.qh.shop.repo.StaffRepo;
import net.kingsilk.qh.shop.service.service.AuthorityService;
import net.kingsilk.qh.shop.service.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthoritiesResource implements AuthoritiesApi {

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private ShopStaffRepo shopStaffRepo;

    @Autowired
    private StaffGroupRepo staffGroupRepo;

    @Autowired
    private SecService secService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")
    public UniResp<Set<String>> admin(String brandAppId) {
        String userId = secService.curUserId();

        Staff staff = staffRepo.findOne(
                Expressions.allOf(
                        QStaff.staff.deleted.ne(true),
                        QStaff.staff.brandAppId.eq(brandAppId),
                        QStaff.staff.userId.eq(userId),
                        QStaff.staff.enable.ne(false)
                )
        );
        if (staff == null) {
            throw new ErrStatusException(ErrStatus.NOAUTH, "没有访问权限");
        }
        Set<String> authorities = new HashSet<>();
        authorities.add("SA");
        UniResp<Set<String>> uniResp = new UniResp<>();

        uniResp.setStatus(200);
        uniResp.setData(authorities);
        return uniResp;
    }


    @Override
    public UniResp<String> setSAStaff(String brandAppId, String userId, String orgId) {
        Staff staff = new Staff();
        staff.setUserId(userId);
        staff.setBrandAppId(brandAppId);
        staff.setMemo("拥有者");
        staffRepo.save(staff);

        //后台管理员
        StaffGroup staffGroup = new StaffGroup();
        staffGroup.setBrandAppId(brandAppId);
        staffGroup.setName("超级管理员");
        Set<String> authorities = new HashSet<>();
        Set<Staff> staffs = new HashSet<>();
        staffs.add(staff);
        authorities.add(AuthorityEnum.SA.name());
        staffGroup.setAuthorities(authorities);
        staffGroup.setStaffS(staffs);
        staffGroup.setReserved(true);
        staffGroupRepo.save(staffGroup);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(staff.getId());
        return uniResp;
    }
}
