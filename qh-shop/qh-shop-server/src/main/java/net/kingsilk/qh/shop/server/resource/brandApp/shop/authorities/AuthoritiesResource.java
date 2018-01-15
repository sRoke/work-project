package net.kingsilk.qh.shop.server.resource.brandApp.shop.authorities;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.authorities.AuthoritiesApi;
import net.kingsilk.qh.shop.core.AuthorityEnum;
import net.kingsilk.qh.shop.domain.QShopStaff;
import net.kingsilk.qh.shop.domain.ShopStaff;
import net.kingsilk.qh.shop.repo.ShopStaffRepo;
import net.kingsilk.qh.shop.service.service.AuthorityService;
import net.kingsilk.qh.shop.service.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;


@Component("shopAuthoritiesResource")
public class AuthoritiesResource implements AuthoritiesApi {


    @Autowired
    private ShopStaffRepo shopStaffRepo;

    @Autowired
    private SecService secService;

    @Autowired
    private AuthorityService authorityService;


    @Override
    @PreAuthorize("isAuthenticated() && hasAnyAuthority('STAFF','SHOPSTAFF')")
    public UniResp<Set<String>> getAuthorities(String brandAppId, String shopId) {


        String userId = secService.curUserId();

        ShopStaff shopStaff = shopStaffRepo.findOne(
                Expressions.allOf(
                        QShopStaff.shopStaff.brandAppId.eq(brandAppId),
                        QShopStaff.shopStaff.userId.eq(userId),
                        QShopStaff.shopStaff.shopId.eq(shopId),
                        QShopStaff.shopStaff.deleted.ne(true)
                ));
        UniResp<Set<String>> uniResp = new UniResp<>();
        if (shopStaff == null) {
            throw new ErrStatusException(ErrStatus.NOAUTH, "没有访问权限");
        }
        Set<String> authorities = authorityService.getAuthorities(shopStaff);
        Set<String> respAuth = new LinkedHashSet<>();
        respAuth.addAll(authorities);
        authorities.forEach(
                authority-> respAuth.addAll(authorityService.fillAuth(new LinkedHashSet<>(), AuthorityEnum.valueOf(authority)))
        );
        uniResp.setStatus(200);
        uniResp.setData(respAuth);
        return uniResp;
    }
}
