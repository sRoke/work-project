package net.kingsilk.qh.oauth.core.wap.resource.user.addr;

import com.mysema.commons.lang.Assert;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.addr.AddrAddReq;
import net.kingsilk.qh.oauth.api.user.addr.AddrApi;
import net.kingsilk.qh.oauth.api.user.addr.AddrGetResp;
import net.kingsilk.qh.oauth.api.user.addr.AddrUpdateReq;
import net.kingsilk.qh.oauth.core.AddrTypeEnum;
import net.kingsilk.qh.oauth.core.wap.resource.UniPageConverter;
import net.kingsilk.qh.oauth.domain.Addr;
import net.kingsilk.qh.oauth.domain.QAddr;
import net.kingsilk.qh.oauth.domain.QUser;
import net.kingsilk.qh.oauth.domain.User;
import net.kingsilk.qh.oauth.repo.AddrRepo;
import net.kingsilk.qh.oauth.repo.UserRepo;
import net.kingsilk.qh.oauth.service.AddrService;
import net.kingsilk.qh.oauth.service.ParamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public class AddrResource implements AddrApi {

    @Autowired
    private AddrRepo addrRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AddrService addrService;

    @Autowired
    private UniPageConverter uniPageConverter;

    @Override
    public UniResp<String> add(
            String userId,
            AddrAddReq addrReq
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

        // TODO 每个人最多20条地址
        final long MAX_COUNT = 20;

        long count = addrRepo.count(Expressions.allOf(
                QAddr.addr.addrType.eq(AddrTypeEnum.valueOf(addrReq.getAddrType())),
                QAddr.addr.userId.eq(userId),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ));

        Assert.isTrue(count <= MAX_COUNT, "个人用户最多只能有" + MAX_COUNT + "条地址");


        // 先保存
        Addr addr = new Addr();
        addr.setAddrType(AddrTypeEnum.valueOf(addrReq.getAddrType()));
        addr.setUserId(user.getId());
        addr.setOrgId(null);
        addr.setAdc(addrReq.getAdc());
        addr.setStreet(addrReq.getStreet());
        addr.setPostCode(addrReq.getPostCode());
        addr.setCoorX(addrReq.getCoorX());
        addr.setCoorY(addrReq.getCoorY());
        addr.setContact(addrReq.getContact());
        addr.setPhones(addrReq.getPhones());
        addr.setDefaultAddr(addrReq.isDefaultAddr());
        addr.setMemo(addrReq.getMemo());
        addrRepo.save(addr);

        // 再重新设置默认地址
        if (addrReq.isDefaultAddr()) {
            addrService.setUserDefaultShippingAddr(userId, addr.getId());
        }

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(addr.getId());
        return uniResp;
    }

    @Override
    public UniResp<AddrGetResp> get(
            String userId,
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

        Addr addr = addrRepo.findOne(Expressions.allOf(
                QAddr.addr.id.eq(addrId),
//                QAddr.addr.addrType.eq(AddrTypeEnum.USER_SHIPPING_ADDR),
                QAddr.addr.userId.eq(userId),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ));

        Assert.isTrue(addr != null, "地址不存在");

        AddrGetResp addrGetResp = toAddrGetResp(addr);

        UniResp<AddrGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(addrGetResp);
        return uniResp;
    }

    @Override
    public UniResp<Void> del(
            String userId,
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

        Addr addr = addrRepo.findOne(Expressions.allOf(
                QAddr.addr.id.eq(addrId),
//                QAddr.addr.addrType.eq(AddrTypeEnum.USER_SHIPPING_ADDR),
                QAddr.addr.userId.eq(userId),
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

    @Override
    public UniResp<Void> update(
            String userId,
            String addrId,
            AddrUpdateReq addrReq
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

        Addr addr = addrRepo.findOne(Expressions.allOf(
                QAddr.addr.id.eq(addrId),
//                QAddr.addr.addrType.eq(AddrTypeEnum.USER_SHIPPING_ADDR),
                QAddr.addr.userId.eq(userId),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ));
        Assert.isTrue(addr != null, "地址不存在");

        // 先保存


        if (addrReq.getAdc().isPresent()) {
            addr.setAdc(addrReq.getAdc().get());
        }

        if (addrReq.getContact().isPresent()) {
            addr.setContact(addrReq.getContact().get());
        }

        if (addrReq.getStreet().isPresent()) {
            addr.setStreet(addrReq.getStreet().get());
        }

        if (addrReq.getPostCode().isPresent()) {
            addr.setPostCode(addrReq.getPostCode().get());
        }

        if (addrReq.getCoorX().isPresent()) {
            addr.setCoorX(addrReq.getCoorX().get());
        }

        if (addrReq.getCoorY().isPresent()) {
            addr.setCoorY(addrReq.getCoorY().get());
        }

        if (addrReq.getPhones().isPresent()) {
            addr.setPhones(addrReq.getPhones().get());
        }

        if (addrReq.getDefaultAddr().isPresent()) {
            addr.setDefaultAddr(addrReq.getDefaultAddr().get());
        }

        if (addrReq.getMemo().isPresent()) {
            addr.setMemo(addrReq.getMemo().get());
        }

        addrRepo.save(addr);

        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }


    private AddrGetResp toAddrGetResp(Addr addr) {

        AddrGetResp resp = new AddrGetResp();
        Optional.ofNullable(addr).ifPresent(it ->
                {
                    resp.setId(addr.getId());
                    resp.setDateCreated(addr.getDateCreated());
                    resp.setCreatedBy(addr.getCreatedBy());
                    resp.setLastModifiedDate(addr.getLastModifiedDate());
                    resp.setLastModifiedBy(addr.getLastModifiedBy());


                    resp.setUserId(addr.getUserId());
                    resp.setAdc(addr.getAdc());
                    resp.setStreet(addr.getStreet());
                    resp.setPostCode(addr.getPostCode());
                    resp.setCoorX(addr.getCoorX());
                    resp.setCoorY(addr.getCoorY());
                    resp.setContact(addr.getContact());
                    resp.setPhones(addr.getPhones());
                    resp.setDefaultAddr(addr.isDefaultAddr());
                    resp.setMemo(addr.getMemo());
                }
        );
        return resp;

    }

    @Override
    public UniResp<UniPage<AddrGetResp>> list(
            int size,
            int page,
            List<String> sort,
            String userId,
            List<String> addrIds
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


        Page<Addr> domainPage = addrRepo.findAll(Expressions.allOf(
                addrIds != null && !addrIds.isEmpty()
                        ? QAddr.addr.id.in(addrIds)
                        : null,
//                QAddr.addr.addrType.eq(AddrTypeEnum.USER_SHIPPING_ADDR),
                QAddr.addr.userId.eq(userId),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ), pageable);

        Page<AddrGetResp> respPage = domainPage.map(i -> toAddrGetResp(i));

        UniPage<AddrGetResp> respUniPage = uniPageConverter.convert(respPage);
        UniResp<UniPage<AddrGetResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(respUniPage);
        return uniResp;
    }

    @Override
    public UniResp<AddrGetResp> getDefault(
            String userId,
            String addrType
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

        Addr addr = addrRepo.findOne(Expressions.allOf(
                QAddr.addr.addrType.eq(AddrTypeEnum.valueOf(addrType)),
                QAddr.addr.userId.eq(userId),
                QAddr.addr.defaultAddr.eq(true),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ));
//        Assert.isTrue(addr != null, "地址不存在");

        AddrGetResp addrGetResp = toAddrGetResp(addr);

        UniResp<AddrGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(addrGetResp);
        return uniResp;
    }

    @Override
    public UniResp<Void> setDefault(
            String userId,
            String addrId,
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

        Addr addr = addrRepo.findOne(Expressions.allOf(
                QAddr.addr.addrType.eq(AddrTypeEnum.valueOf(addrType)),
                QAddr.addr.id.eq(addrId),
                QAddr.addr.userId.eq(userId),
                Expressions.anyOf(
                        QAddr.addr.deleted.isNull(),
                        QAddr.addr.deleted.eq(false)
                )
        ));
        Assert.isTrue(addr != null, "地址不存在");

        addrService.setUserDefaultShippingAddr(userId, addrId);

        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }
}
