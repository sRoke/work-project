package net.kingsilk.qh.oauth.core.wap.resource.user.org.addr;

import com.mysema.commons.lang.Assert;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.org.officeAddr.OfficeAddrAddReq;
import net.kingsilk.qh.oauth.api.user.org.officeAddr.OfficeAddrApi;
import net.kingsilk.qh.oauth.api.user.org.officeAddr.OfficeAddrGetResp;
import net.kingsilk.qh.oauth.api.user.org.officeAddr.OfficeAddrUpdateReq;
import net.kingsilk.qh.oauth.core.AddrTypeEnum;
import net.kingsilk.qh.oauth.core.wap.resource.UniPageConverter;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.AddrRepo;
import net.kingsilk.qh.oauth.repo.OrgRepo;
import net.kingsilk.qh.oauth.repo.OrgStaffRepo;
import net.kingsilk.qh.oauth.repo.UserRepo;
import net.kingsilk.qh.oauth.service.AddrService;
import net.kingsilk.qh.oauth.service.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AddrResource implements OfficeAddrApi {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrgRepo orgRepo;

    @Autowired
    private OrgStaffRepo orgStaffRepo;

    @Autowired
    private AddrRepo addrRepo;

    @Autowired
    private AddrService addrService;

    @Autowired
    private UniPageConverter uniPageConverter;


    @Override
    public UniResp<String> add(
            String userId,
            String orgId,
            OfficeAddrAddReq addReq
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
//                QOrg.org.userId.eq(userId),
                Expressions.anyOf(
                        QOrg.org.deleted.isNull(),
                        QOrg.org.deleted.eq(false)
                )
        ));
        Assert.isTrue(org != null, "组织不存在");

//        是否需要去判断用户是否属于当前组织，一个用户可能属于多个组织
        Iterable<OrgStaff> orgStaffList = orgStaffRepo.findAll(
                Expressions.allOf(
                        QOrgStaff.orgStaff.userId.eq(userId),
                        QOrgStaff.orgStaff.deleted.in(false)
                ));

        List<String> orgIdList = new ArrayList<>();

        orgStaffList.forEach(
                orgStaff ->
                        orgIdList.add(orgStaff.getOrgId())
        );

        if (orgIdList.contains(orgId)) {

            // 判断新增的地址类型
            if (addReq.getAddrType().equals("ORG_OFFICE_ADDR")) {
                // TODO 每个组织最多20个办公地址
                final long MAX_COUNT = 20;
                long count = addrRepo.count(Expressions.allOf(
                        QAddr.addr.addrType.eq(AddrTypeEnum.ORG_OFFICE_ADDR),
                        QAddr.addr.orgId.eq(orgId),
                        Expressions.anyOf(
                                QAddr.addr.deleted.isNull(),
                                QAddr.addr.deleted.eq(false)
                        )
                ));
                Assert.isTrue(count <= MAX_COUNT, "每个组织最多只能有" + MAX_COUNT + "条办公地址");

            }
            // 先保存
            Addr addr = new Addr();
            addr.setAddrType(AddrTypeEnum.valueOf(addReq.getAddrType()));
            addr.setUserId(null);
            addr.setOrgId(orgId);
            addr.setAdc(addReq.getAdc());
            addr.setStreet(addReq.getStreet());
            addr.setPostCode(addReq.getPostCode());
            addr.setCoorX(addReq.getCoorX());
            addr.setCoorY(addReq.getCoorY());
            addr.setContact(addReq.getContact());
            addr.setPhones(addReq.getPhones());
            addr.setDefaultAddr(addReq.isDefaultAddr());
            addr.setMemo(addReq.getMemo());
            addrRepo.save(addr);

            if (addReq.isDefaultAddr()) {
                addrService.setOrgDefaultOfficeAddr(orgId, addr.getId());
            }

            UniResp<String> uniResp = new UniResp<>();
            uniResp.setStatus(200);
            uniResp.setData(addr.getId());
            return uniResp;
        }else {
            UniResp<String> uniResp = new UniResp<>();
            uniResp.setStatus(10001);
            uniResp.setData("该员工不属于该组织");
            return uniResp;
        }
    }

    @Override
    public UniResp<Void> del(
            String userId,
            String orgId,
            String addrId
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

        Addr addr = addrRepo.findOne(Expressions.allOf(
                QAddr.addr.id.eq(addrId),
                QAddr.addr.orgId.eq(orgId),
//                QAddr.addr.addrType.eq(AddrTypeEnum.ORG_OFFICE_ADDR),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ));
        Assert.isTrue(addr != null, "地址不存在");

        addr.setDeleted(true);
        addrRepo.save(addr);

        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }


    private OfficeAddrGetResp toOfficeAddrGetResp(Addr addr) {

        OfficeAddrGetResp resp = new OfficeAddrGetResp();

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
        resp.setDefaultAddr(addr.isDefaultAddr());
        resp.setMemo(addr.getMemo());

        return resp;
    }

    @Override
    public UniResp<OfficeAddrGetResp> get(
            String userId,
            String orgId,
            String addrId
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

        Addr addr = addrRepo.findOne(Expressions.allOf(
                QAddr.addr.id.eq(addrId),
                QAddr.addr.orgId.eq(orgId),
//                QAddr.addr.addrType.eq(AddrTypeEnum.ORG_OFFICE_ADDR),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ));
        Assert.isTrue(addr != null, "地址不存在");

        OfficeAddrGetResp resp = toOfficeAddrGetResp(addr);

        UniResp<OfficeAddrGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);
        return uniResp;
    }

    @Override
    public UniResp<Void> update(
            String userId,
            String orgId,
            String addrId,
            OfficeAddrUpdateReq updateReq
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

        Addr addr = addrRepo.findOne(Expressions.allOf(
                QAddr.addr.id.eq(addrId),
                QAddr.addr.orgId.eq(orgId),
//                QAddr.addr.addrType.eq(AddrTypeEnum.ORG_OFFICE_ADDR),
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

        if (updateReq.getDefaultAddr().isPresent()) {
            addr.setDefaultAddr(updateReq.getDefaultAddr().get());
        }

        if (updateReq.getMemo().isPresent()) {
            addr.setMemo(updateReq.getMemo().get());
        }
        addrRepo.save(addr);

        // 更新默认地址
        if (updateReq.getDefaultAddr().isPresent() && updateReq.getDefaultAddr().get()) {
            addrService.setOrgDefaultOfficeAddr(orgId, addrId);
        }

        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<UniPage<OfficeAddrGetResp>> list(
            int size,
            int page,
            List<String> sort,
            String userId,
            String orgId,
            String addrType
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


        Sort s = ParamUtils.toSort(sort);
        Pageable pageable = new PageRequest(page, size, s);


        Page<Addr> domainPage = addrRepo.findAll(Expressions.allOf(
                QAddr.addr.addrType.eq(AddrTypeEnum.valueOf(addrType)),
                QAddr.addr.orgId.eq(orgId),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ), pageable);

        Page<OfficeAddrGetResp> respPage = domainPage.map(i -> toOfficeAddrGetResp(i));

        UniPage<OfficeAddrGetResp> respUniPage = uniPageConverter.convert(respPage);
        UniResp<UniPage<OfficeAddrGetResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(respUniPage);
        return uniResp;
    }

    @Override
    public UniResp<OfficeAddrGetResp> getDefault(
            String userId,
            String orgId,
            String addrType
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

        Addr addr = addrRepo.findOne(Expressions.allOf(
                QAddr.addr.orgId.eq(orgId),
                QAddr.addr.addrType.eq(AddrTypeEnum.valueOf(addrType)),
                QAddr.addr.defaultAddr.eq(true),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ));

        OfficeAddrGetResp resp = toOfficeAddrGetResp(addr);

        UniResp<OfficeAddrGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);
        return uniResp;
    }

    @Override
    public UniResp<Void> setDefault(
            String userId,
            String orgId,
            String addrType
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


        Addr addr = addrRepo.findOne(Expressions.allOf(
                QAddr.addr.orgId.eq(orgId),
                QAddr.addr.addrType.eq(AddrTypeEnum.valueOf(addrType)),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ));
        Assert.isTrue(addr != null, "地址不存在");

        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }

    @Override
    public UniResp<String> judgeBrandComAddr(
            String userId,
            String orgId) {
        User user = userRepo.findOne(Expressions.allOf(
                QUser.user.id.eq(userId),
                Expressions.anyOf(
                        QUser.user.deleted.isNull(),
                        QUser.user.deleted.eq(false)
                )
        ));
        Assert.isTrue(user != null, "用户不存在");


        Addr tmpAddr = addrRepo.findOne(
                Expressions.allOf(
                        QAddr.addr.addrType.eq(AddrTypeEnum.ORG_RETURN_ADDR),
                        QAddr.addr.defaultAddr.in(true)
                ));
        UniResp<String> uniResp = new UniResp<>();
        Integer stats = (tmpAddr == null ? 10037 : 10038);
        String data = (tmpAddr == null ? "" : tmpAddr.getId());
        uniResp.setStatus(stats);
        uniResp.setData(data);
        return uniResp;
    }
}
