package net.kingsilk.qh.agency.service

import com.querydsl.core.types.dsl.Expressions
import net.kingsilk.qh.agency.core.StaffAuthorityEnum
import net.kingsilk.qh.agency.domain.QStaffGroup
import net.kingsilk.qh.agency.domain.Staff
import net.kingsilk.qh.agency.repo.StaffGroupRepo
import net.kingsilk.qh.agency.repo.StaffRepo
import org.apache.commons.lang3.EnumUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Service

/**
 * 员工权限相关的服务。
 */
@Service
class StaffAuthorityService {

    @Autowired
    MongoTemplate mongoTemplate

    @Autowired
    StaffRepo staffRepo

    @Autowired
    StaffGroupRepo staffGroupRepo

    static final Map<String, StaffAuthorityEnum> staffAuthMap = EnumUtils.getEnumMap(StaffAuthorityEnum)

    /**
     * 将给定的权限列表，按照权限父子关系，将子权限加入并返回。
     *
     * @param authorities
     * @return 包含子权限的权限列表
     *
     * @see StaffAuthorityEnum
     */
    static Set<GrantedAuthority> extendStaffChildAuthorities(String... authorities) {

        Set<String> authStrSet = new HashSet<>()
        for (String auth : authorities) {
            StaffAuthorityEnum e = staffAuthMap.get(auth)
            if (e) {
                fillAuth(authStrSet, e)
            } else {
                authStrSet.add(auth)
            }
        }
        return authStrSet
        //   List<GrantedAuthority> authList = []
        //return AuthorityUtils.createAuthorityList((String[]) authStrSet.toArray())
    }

    /**
     * 递归调用的方法，给定父权限，将父权限和子权限都加入到 set 中。
     *
     * @param auths
     * @param e
     */
    static void fillAuth(Set<String> auths, StaffAuthorityEnum e) {
        auths.addAll(e.name())

        if (e.children) {
//            e.children.each {
//                fillAuth(auths, e.children)
//            }
            for (StaffAuthorityEnum children:e.children){
                fillAuth(auths, children)
            }
        }
    }

    /**
     * 从 StaffGropup， Staff 中合并权限，并返回。
     *
     * @param staff
     * @return
     */
    Set<String> getAuthorities(Staff staff) {

        List<String> authList = staffGroupRepo.findAll(Expressions.allOf(
//                QStaffGroup.staffGroup.company.eq(staff.company),
                QStaffGroup.staffGroup.staffs.contains(staff)
        ))*.authorities.flatten().unique()


        if (staff.authorities) {
            authList.addAll(staff.authorities)
            authList = authList.unique()
        }

        return authList.toSet()

    }


}
