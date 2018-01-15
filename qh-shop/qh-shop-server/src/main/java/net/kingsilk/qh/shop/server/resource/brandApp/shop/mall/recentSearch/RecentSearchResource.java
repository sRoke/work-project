package net.kingsilk.qh.shop.server.resource.brandApp.shop.mall.recentSearch;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.UniResp;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.recentSearch.RecentSearchApi;
import net.kingsilk.qh.shop.api.brandApp.shop.mall.recentSearch.dto.RecentSearchResp;
import net.kingsilk.qh.shop.domain.Member;
import net.kingsilk.qh.shop.domain.QMember;
import net.kingsilk.qh.shop.domain.QRecentSearch;
import net.kingsilk.qh.shop.domain.RecentSearch;
import net.kingsilk.qh.shop.repo.MemberRepo;
import net.kingsilk.qh.shop.repo.RecentSearchRepo;
import net.kingsilk.qh.shop.service.service.SecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;

@Component
public class RecentSearchResource implements RecentSearchApi {

    @Autowired
    private RecentSearchRepo recentSearchRepo;

    @Autowired
    private SecService secService;

    @Autowired
    private MemberRepo memberRepo;

    @Override
    @PreAuthorize("permitAll()")
    public UniResp<RecentSearchResp> page(String brandAppId, String shopId) {

        String userId = secService.curUserId();
        UniResp<RecentSearchResp> uniResp = new UniResp<>();

        if (StringUtils.isEmpty(userId)) {
            uniResp.setStatus(200);
            return uniResp;
        }

        RecentSearchResp recentSearchResp = new RecentSearchResp();
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        if (userId != null) {
            Member member = memberRepo.findOne(
                    Expressions.allOf(
                            QMember.member.userId.eq(userId),
                            QMember.member.deleted.ne(true),
                            QMember.member.shopId.eq(shopId),
                            QMember.member.brandAppId.eq(brandAppId)
                    )
            );


            Page<RecentSearch> recentSearchPage = recentSearchRepo.findAll(
                    Expressions.allOf(
                            QRecentSearch.recentSearch.deleted.ne(true),
                            QRecentSearch.recentSearch.shopId.eq(shopId),
                            QRecentSearch.recentSearch.brandAppId.eq(brandAppId),
                            QRecentSearch.recentSearch.memberId.eq(member.getId())
                    ), new PageRequest(0, 20, new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
            );


            recentSearchPage.getContent().forEach(
                    recentSearch -> {
                        linkedHashSet.add(recentSearch.getKeyWord());
                    }
            );
        }
        recentSearchResp.setKeyWords(linkedHashSet);

        uniResp.setData(recentSearchResp);
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<String> delete(String brandAppId, String shopId) {

        String userId = secService.curUserId();


        Member member = memberRepo.findOne(
                Expressions.allOf(
                        QMember.member.userId.eq(userId),
                        QMember.member.deleted.ne(true),
                        QMember.member.shopId.eq(shopId),
                        QMember.member.brandAppId.eq(brandAppId)
                )
        );
        UniResp<String> uniResp = new UniResp<>();

        if (member == null) {
            uniResp.setStatus(ErrStatus.FINDNULL);
            uniResp.setData("请先注册为会员");
            return uniResp;
        }

        Page<RecentSearch> recentSearchPage = recentSearchRepo.findAll(
                Expressions.allOf(
                        QRecentSearch.recentSearch.deleted.ne(true),
                        QRecentSearch.recentSearch.shopId.eq(shopId),
                        QRecentSearch.recentSearch.brandAppId.eq(brandAppId),
                        QRecentSearch.recentSearch.memberId.eq(member.getId())
                ), new PageRequest(0, 20, new Sort(new Sort.Order(Sort.Direction.DESC, "dateCreated")))
        );


        recentSearchRepo.delete(recentSearchPage);

        uniResp.setData("success");
        uniResp.setStatus(200);
        return uniResp;
    }
}
