package net.kingsilk.qh.oauth.core.wap.resource.user.org.regAddr;

import com.mysema.commons.lang.*;
import com.querydsl.core.types.dsl.*;
import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.user.org.regAddr.*;
import net.kingsilk.qh.oauth.core.*;
import net.kingsilk.qh.oauth.core.wap.resource.UniPageConverter;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import net.kingsilk.qh.oauth.service.*;
import org.springframework.beans.factory.annotation.*;

/**
 *
 */
public class RegAddrResource implements RegAddrApi {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrgRepo orgRepo;

    @Autowired
    private AddrRepo addrRepo;

    @Autowired
    private AddrService addrService;

    @Autowired
    private UniPageConverter uniPageConverter;


    private RegAddrGetResp toRegAddrGetResp(Addr addr) {

        RegAddrGetResp resp = new RegAddrGetResp();

        resp.setId(addr.getId());
        resp.setDateCreated(addr.getDateCreated());
        resp.setCreatedBy(addr.getCreatedBy());
        resp.setLastModifiedDate(addr.getLastModifiedDate());
        resp.setLastModifiedBy(addr.getLastModifiedBy());


        resp.setOrgId(addr.getOrgId());
        resp.setAdc(addr.getAdc());
        resp.setStreet(addr.getStreet());
        resp.setPostCode(addr.getPostCode());
        resp.setCoorX(addr.getCoorX());
        resp.setCoorY(addr.getCoorY());
        resp.setContact(addr.getContact());
        resp.setPhones(addr.getPhones());
        resp.setMemo(addr.getMemo());

        return resp;
    }

    @Override
    public UniResp<RegAddrGetResp> get(
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
                QOrg.org.userId.eq(userId),
                Expressions.anyOf(
                        QOrg.org.deleted.isNull(),
                        QOrg.org.deleted.eq(false)
                )
        ));
        Assert.isTrue(org != null, "组织不存在");

        Addr addr = addrRepo.findOne(Expressions.allOf(
                QAddr.addr.orgId.eq(orgId),
                QAddr.addr.addrType.eq(AddrTypeEnum.ORG_REG_ADDR),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ));
        Assert.isTrue(addr != null, "地址不存在");

        RegAddrGetResp resp = toRegAddrGetResp(addr);

        UniResp<RegAddrGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);
        return uniResp;
    }

    @Override
    public UniResp<Void> update(
            String userId,
            String orgId,
            RegAddrUpdateReq updateReq
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
                QOrg.org.userId.eq(userId),
                Expressions.anyOf(
                        QOrg.org.deleted.isNull(),
                        QOrg.org.deleted.eq(false)
                )
        ));
        Assert.isTrue(org != null, "组织不存在");

        Addr addr = addrRepo.findOne(Expressions.allOf(
                QAddr.addr.orgId.eq(orgId),
                QAddr.addr.addrType.eq(AddrTypeEnum.ORG_REG_ADDR),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ));
        Assert.isTrue(addr != null, "地址不存在");


        // 先保存
        if (updateReq.getAdc().isPresent()) {
            addr.setAdc(updateReq.getAdc().get());
        }

        if (updateReq.getStreet().isPresent()) {
            addr.setStreet(updateReq.getStreet().get());
        }

        if (updateReq.getPostCode().isPresent()) {
            addr.setPostCode(updateReq.getPostCode().get());
        }

        if (updateReq.getCoorX().isPresent()) {
            addr.setCoorX(updateReq.getCoorX().get());
        }

        if (updateReq.getCoorY().isPresent()) {
            addr.setCoorY(updateReq.getCoorY().get());
        }

        if (updateReq.getContact().isPresent()) {
            addr.setPhones(updateReq.getPhones().get());
        }

        if (updateReq.getMemo().isPresent()) {
            addr.setMemo(updateReq.getMemo().get());
        }
        addrRepo.save(addr);

        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }

}
