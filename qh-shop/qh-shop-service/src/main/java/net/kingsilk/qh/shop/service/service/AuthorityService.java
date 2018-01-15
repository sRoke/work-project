package net.kingsilk.qh.shop.service.service;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.core.AuthorityEnum;
import net.kingsilk.qh.shop.domain.QShopStaffGroup;
import net.kingsilk.qh.shop.domain.ShopStaff;
import net.kingsilk.qh.shop.domain.ShopStaffGroup;
import net.kingsilk.qh.shop.repo.ShopStaffGroupRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class AuthorityService {


    @Autowired
    private ShopStaffGroupRepo shopStaffGroupRepo;

    /**
     * 递归调用的方法，给定父权限，将父权限和子权限都加入到 set 中。
     *
     * @param auths
     * @param e
     */
    public Set<String> fillAuth(Set<String> auths, AuthorityEnum e) {
        Set<String> newauths = new LinkedHashSet<>();
        newauths.addAll(auths);
        newauths.add(e.name());
        if (e.getChildren() != null) {
            for (AuthorityEnum children : e.getChildren()) {
                Set<String> respAuths = new LinkedHashSet<>();
                respAuths = fillAuth(respAuths, children);
                newauths.addAll(respAuths);
            }
        }
        return newauths;
    }


    /**
     * 合并权限，并返回。
     *
     * @param shopStaff
     * @return
     */
    public Set<String> getAuthorities(ShopStaff shopStaff) {

        Iterable<ShopStaffGroup> authList = shopStaffGroupRepo.findAll(Expressions.allOf(
                QShopStaffGroup.shopStaffGroup.staffS.contains(shopStaff)
        ));

        Set<String> stringSet = new HashSet<>();

        authList.forEach(
                shopStaffGroup -> {
                    shopStaffGroup.getAuthorities().forEach(
                            s -> {
                                stringSet.add(s);
                            }
                    );
                }
        );


        return stringSet;

    }

}
