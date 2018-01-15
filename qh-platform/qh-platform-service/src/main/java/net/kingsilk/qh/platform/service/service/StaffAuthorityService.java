package net.kingsilk.qh.platform.service.service;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.platform.core.StaffAuthorityEnum;
import net.kingsilk.qh.platform.domain.QStaffGroup;
import net.kingsilk.qh.platform.domain.Staff;
import net.kingsilk.qh.platform.domain.StaffGroup;
import net.kingsilk.qh.platform.repo.StaffGroupRepo;
import net.kingsilk.qh.platform.repo.StaffRepo;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 员工权限相关的服务。
 */
@Service
public class StaffAuthorityService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private StaffGroupRepo staffGroupRepo;

    static final Map<String, StaffAuthorityEnum> staffAuthMap = EnumUtils.getEnumMap(StaffAuthorityEnum.class);

//    /**
//     * 将给定的权限列表，按照权限父子关系，将子权限加入并返回。
//     *
//     * @param authorities
//     * @return 包含子权限的权限列表
//     * @see StaffAuthorityEnum
//     */
//    public static Set<GrantedAuthority> extendStaffChildAuthorities(String... authorities) {
//
//        Set<String> authStrSet = new HashSet<>();
//        for (String auth : authorities) {
//            StaffAuthorityEnum e = staffAuthMap.get(auth);
//            if (e != null) {
//                fillAuth(authStrSet, e);
//            } else {
//                authStrSet.add(auth);
//            }
//        }
////        return authStrSet;
//        List<GrantedAuthority> authList = new ArrayList<>();
//        AuthorityUtils.createAuthorityList((String[]) authStrSet.toArray());
//        Set<GrantedAuthority> authoritySet = new LinkedHashSet<>();
//        return
//    }

    /**
     * 递归调用的方法，给定父权限，将父权限和子权限都加入到 set 中。
     *
     * @param auths
     * @param e
     */
    public static void fillAuth(Set<String> auths, StaffAuthorityEnum e) {
        auths.add(e.name());

        if (e.getChildren() != null) {
//            e.children.each {
//                fillAuth(auths, e.children)
//            }
            for (StaffAuthorityEnum children : e.getChildren()) {
                fillAuth(auths, children);
            }
        }
    }

    /**
     * 从 StaffGropup， Staff 中合并权限，并返回。
     *
     * @param staff
     * @return
     */
    public Set<String> getAuthorities(Staff staff) {

        Iterable<StaffGroup> list = staffGroupRepo.findAll(Expressions.allOf(
//                QStaffGroup.staffGroup.company.eq(staff.company),
                QStaffGroup.staffGroup.staffs.contains(staff)
        ));

        Set<String> authList = new LinkedHashSet<>();

        list.forEach(
                staffGroup -> {
                    authList.addAll(staffGroup.getAuthorities());
                }
        );

        if (staff.getAuthorities() != null) {
            authList.addAll(staff.getAuthorities());
        }

        return authList;

    }


}
