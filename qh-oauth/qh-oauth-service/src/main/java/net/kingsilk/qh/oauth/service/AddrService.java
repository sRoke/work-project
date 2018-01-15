package net.kingsilk.qh.oauth.service;

import com.mysema.commons.lang.Assert;
import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.core.AddrTypeEnum;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.OrgRepo;
import net.kingsilk.qh.oauth.repo.OrgStaffRepo;
import net.kingsilk.qh.oauth.repo.UserRepo;
import net.kingsilk.qh.oauth.security.SecService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 *
 */
@Service
public class AddrService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SecService secUtils;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private OrgRepo orgRepo;

    @Autowired
    private OrgStaffRepo orgStaffRepo;


    public void setUserDefaultShippingAddr(
            String userId,
            String defaultShippingAddrId
    ) {

        BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, Addr.class);

        // 先更新用户所有的地址：是否为默认 = false。
        ops.updateMulti(query(
                where("addrType").is(AddrTypeEnum.USER_SHIPPING_ADDR.name())
                        .and("userId").is(userId)
        ), Update.update("defaultAddr", false));

        // 再更新特定的地址为：是否为默认 = true
        ops.updateOne(query(
                where("addrType").is(AddrTypeEnum.USER_SHIPPING_ADDR.name())
                        .and("userId").is(userId)
                        //.and("_id").is(defaultShippingAddrId)
                        .and("_id").is(new ObjectId(defaultShippingAddrId))
        ), Update.update("defaultAddr", true));
        ops.execute();

    }


    public void setOrgDefaultOfficeAddr(
            String orgId,
            String defaultOfficeAddrId
    ) {

        BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, Addr.class);

        // 先更新用户所有的地址：是否为默认 = false。
        ops.updateMulti(query(
                where("addrType").is(AddrTypeEnum.ORG_OFFICE_ADDR.name())
                        .and("orgId").is(orgId)
        ), Update.update("defaultAddr", false));

        // 再更新特定的地址为：是否为默认 = true
        ops.updateOne(query(
                where("addrType").is(AddrTypeEnum.ORG_OFFICE_ADDR.name())
                        .and("orgId").is(orgId)
                        .and("_id").is(new ObjectId(defaultOfficeAddrId))
        ), Update.update("defaultAddr", true));
        ops.execute();

    }

    public UniResp<Void> checkUserAndOrg(String userId, String orgId) {

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
        UniResp<Void> uniResp = new UniResp<>();
        if (orgIdList.contains(orgId)) {
            uniResp.setStatus(200);
        } else {
            uniResp.setStatus(10001);
        }
        return uniResp;
    }

}
