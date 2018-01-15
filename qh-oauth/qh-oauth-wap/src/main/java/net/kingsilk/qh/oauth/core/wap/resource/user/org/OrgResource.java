package net.kingsilk.qh.oauth.core.wap.resource.user.org;

import com.mysema.commons.lang.Assert;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.org.OrgAddReq;
import net.kingsilk.qh.oauth.api.user.org.OrgApi;
import net.kingsilk.qh.oauth.api.user.org.OrgGetResp;
import net.kingsilk.qh.oauth.api.user.org.OrgUpdateReq;
import net.kingsilk.qh.oauth.core.wap.resource.UniPageConverter;
import net.kingsilk.qh.oauth.domain.Org;
import net.kingsilk.qh.oauth.domain.QOrg;
import net.kingsilk.qh.oauth.domain.QUser;
import net.kingsilk.qh.oauth.domain.User;
import net.kingsilk.qh.oauth.repo.AddrRepo;
import net.kingsilk.qh.oauth.repo.OrgRepo;
import net.kingsilk.qh.oauth.repo.UserRepo;
import net.kingsilk.qh.oauth.service.AddrService;
import net.kingsilk.qh.oauth.service.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import java.util.List;

/**
 *
 */
@Singleton
@Component
@Path("/user/{userId}/org")
public class OrgResource implements OrgApi {

    @Autowired
    private AddrRepo addrRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddrService addrService;

    @Autowired
    private UniPageConverter uniPageConverter;

    @Autowired
    private OrgRepo orgRepo;

    @Override
    public UniResp<String> add(
            String userId,
            OrgAddReq orgAddReq
    ) {

        // TODO user id

        User user = userRepo.findOne(Expressions.allOf(
                QUser.user.id.eq(userId),
                Expressions.anyOf(
                        QUser.user.deleted.isNull(),
                        QUser.user.deleted.eq(false)
                )
        ));
        Assert.isTrue(user != null, "用户不存在");

        // TODO 每个人最多20个组织
        final long MAX_COUNT = 20;

        long count = orgRepo.count(Expressions.allOf(
                QOrg.org.userId.eq(userId),
                Expressions.anyOf(
                        QOrg.org.deleted.isNull(),
                        QOrg.org.deleted.eq(false)
                )
        ));

        Assert.isTrue(count <= MAX_COUNT, "个人用户最多只能有" + MAX_COUNT + "个组织");


        Org org = new Org();
        org.setUserId(userId);
        org.setName(orgAddReq.getName());
        org.setStatus(orgAddReq.getStatus());

        orgRepo.save(org);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(org.getId());
        return uniResp;
    }

    @Override
    public UniResp<Void> del(
            String userId,
            String orgId
    ) {

        // TODO user id

        User user = userRepo.findOne(Expressions.allOf(
                QUser.user.id.eq(userId),
                Expressions.anyOf(
                        QUser.user.deleted.isNull(),
                        QUser.user.deleted.eq(false)
                )
        ));
        Assert.isTrue(user != null, "用户不存在");

        Org org = orgRepo.findOne(Expressions.allOf(
                QOrg.org.id.eq(orgId),
                Expressions.anyOf(
                        QOrg.org.deleted.isNull(),
                        QOrg.org.deleted.eq(false)
                )
        ));
        Assert.isTrue(org != null, "组织不存在");

        org.setDeleted(true);
        orgRepo.save(org);
        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }

    private OrgGetResp toOrgGetResp(Org org) {
        OrgGetResp resp = new OrgGetResp();

        resp.setId(org.getId());
        resp.setDateCreated(org.getDateCreated());
        resp.setCreatedBy(org.getCreatedBy());
        resp.setLastModifiedDate(org.getLastModifiedDate());
        resp.setLastModifiedBy(org.getLastModifiedBy());

        resp.setUserId(org.getUserId());
        resp.setName(org.getName());
        resp.setStatus(org.getStatus());

        return resp;
    }

    @Override
    public UniResp<OrgGetResp> get(
            String userId,
            String orgId
    ) {

        User user = userRepo.findOne(Expressions.allOf(
                QUser.user.id.eq(userId),
                Expressions.anyOf(
                        QUser.user.deleted.isNull(),
                        QUser.user.deleted.eq(false)
                )
        ));
        Assert.isTrue(user != null, "用户不存在");

        Org org = orgRepo.findOne(Expressions.allOf(
                QOrg.org.id.eq(orgId),
                Expressions.anyOf(
                        QOrg.org.deleted.isNull(),
                        QOrg.org.deleted.eq(false)
                )
        ));
        Assert.isTrue(org != null, "组织不存在");

        OrgGetResp resp = toOrgGetResp(org);


        UniResp<OrgGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);
        return uniResp;
    }

    @Override
    public UniResp<Void> update(
            String userId,
            String orgId,
            OrgUpdateReq orgUpdateReq
    ) {

        User user = userRepo.findOne(Expressions.allOf(
                QUser.user.id.eq(userId),
                Expressions.anyOf(
                        QUser.user.deleted.isNull(),
                        QUser.user.deleted.eq(false)
                )
        ));
        Assert.isTrue(user != null, "用户不存在");

        Org org = orgRepo.findOne(Expressions.allOf(
                QOrg.org.id.eq(orgId),
                Expressions.anyOf(
                        QOrg.org.deleted.isNull(),
                        QOrg.org.deleted.eq(false)
                )
        ));
        Assert.isTrue(org != null, "组织不存在");


        if (orgUpdateReq.getName().isPresent()) {
            org.setName(orgUpdateReq.getName().get());
        }
        if (orgUpdateReq.getStatus().isPresent()) {
            org.setStatus(orgUpdateReq.getStatus().get());
        }
        if (!StringUtils.isEmpty(userId)) {
            org.setUserId(userId);
        }
        orgRepo.save(org);


        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<UniPage<OrgGetResp>> list(
            int size,
            int page,
            List<String> sort,
            String userId
    ) {

        User user = userRepo.findOne(Expressions.allOf(
                QUser.user.id.eq(userId),
                Expressions.anyOf(
                        QUser.user.deleted.isNull(),
                        QUser.user.deleted.eq(false)
                )
        ));
        Assert.isTrue(user != null, "用户不存在");


        Sort s = ParamUtils.toSort(sort);
        Pageable pageable = new PageRequest(page, size, s);


        Page<Org> domainPage = orgRepo.findAll(Expressions.allOf(
                QOrg.org.userId.eq(userId),
                Expressions.anyOf(
                        QOrg.org.deleted.isNull(),
                        QOrg.org.deleted.eq(false)
                )
        ), pageable);

        Page<OrgGetResp> respPage = domainPage.map(i -> toOrgGetResp(i));

        UniPage<OrgGetResp> respUniPage = uniPageConverter.convert(respPage);
        UniResp<UniPage<OrgGetResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(respUniPage);
        return uniResp;
    }
}
