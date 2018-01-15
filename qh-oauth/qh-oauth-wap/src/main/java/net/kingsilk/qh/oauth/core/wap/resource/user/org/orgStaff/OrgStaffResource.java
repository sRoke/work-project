package net.kingsilk.qh.oauth.core.wap.resource.user.org.orgStaff;

import com.querydsl.core.types.dsl.Expressions;
import net.kingsilk.qh.oauth.api.UniPage;
import net.kingsilk.qh.oauth.api.UniResp;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffAddReq;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffApi;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffGetResp;
import net.kingsilk.qh.oauth.api.user.org.orgStaff.OrgStaffUpdateReq;
import net.kingsilk.qh.oauth.core.wap.resource.UniPageConverter;
import net.kingsilk.qh.oauth.domain.Org;
import net.kingsilk.qh.oauth.domain.OrgStaff;
import net.kingsilk.qh.oauth.domain.QOrgStaff;
import net.kingsilk.qh.oauth.domain.User;
import net.kingsilk.qh.oauth.repo.OrgRepo;
import net.kingsilk.qh.oauth.repo.OrgStaffRepo;
import net.kingsilk.qh.oauth.repo.UserRepo;
import net.kingsilk.qh.oauth.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.inject.Singleton;
import javax.ws.rs.Path;
import java.util.List;

/**
 *
 */
@Singleton
@Component
@Path("/user/{userId}/org/{orgId}/orgStaff")
public class OrgStaffResource implements OrgStaffApi {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrgRepo orgRepo;


    private AddrService addrService;

    @Autowired
    private UniPageConverter uniPageConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private OrgService orgService;


    @Autowired
    private OrgStaffRepo orgStaffRepo;


    @Autowired
    private OrgStaffService orgStaffService;

    @Override
    public UniResp<String> add(
            String userId,
            String orgId,
            OrgStaffAddReq addReq
    ) {

        User user = userService.requireUser(userId);
        Org org = orgService.checkExists(userId, orgId);


        Assert.isTrue(orgStaffService.isStaff(orgId, addReq.getUserId()) == null, "用户已经是员工了");

        String idNo = addReq.getIdNo();
        if (!StringUtils.isEmpty(idNo)) {
            OrgStaff usingStaff = orgStaffService.isIdNoInUse(orgId, idNo, null);
            Assert.isTrue(usingStaff == null, "身份证号已经被占用");
        }

        String jobNo = addReq.getJobNo();
        if (!StringUtils.isEmpty(jobNo)) {
            OrgStaff usingStaff = orgStaffService.isJobNoInUse(orgId, jobNo, null);
            Assert.isTrue(usingStaff == null, "工号已经被占用");
        }

        String nickName = addReq.getNickName();
        if (!StringUtils.isEmpty(nickName)) {
            OrgStaff usingStaff = orgStaffService.isNickNameInUse(orgId, nickName, null);
            Assert.isTrue(usingStaff == null, "花名已经被占用");
        }

        OrgStaff orgStaff = orgStaffService.isDeleted(orgId, addReq.getUserId());
        if (orgStaff != null) {
            orgStaff.setDeleted(false); // 逻辑删除 -> false
        } else {
            orgStaff = new OrgStaff();
        }

        orgStaff.setOrgId(orgId);
        orgStaff.setUserId(addReq.getUserId());
        orgStaff.setIdNo(addReq.getIdNo());
        orgStaff.setJobNo(addReq.getJobNo());
        orgStaff.setNickName(addReq.getNickName());
        orgStaff.setRealName(addReq.getRealName());
        orgStaff.setPhones(addReq.getPhones());
        orgStaff.setTags(addReq.getTags());
        orgStaff.setStatus(addReq.getStatus());

        orgStaffRepo.save(orgStaff);

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(orgStaff.getId());
        return uniResp;
    }

    @Override
    public UniResp<Void> del(
            String userId,
            String orgId,
            String staffId
    ) {
        User user = userService.requireUser(userId);
        Org org = orgService.checkExists(userId, orgId);
        OrgStaff orgStaff = orgStaffService.checkExists(orgId, staffId);
        orgStaff.setDeleted(true);

        orgStaffRepo.save(orgStaff);

        UniResp<Void> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        return uniResp;
    }

    private OrgStaffGetResp toOrgStaffGetReq(OrgStaff orgStaff) {
        OrgStaffGetResp resp = new OrgStaffGetResp();

        resp.setId(orgStaff.getId());
        resp.setDateCreated(orgStaff.getDateCreated());
        resp.setCreatedBy(orgStaff.getCreatedBy());
        resp.setLastModifiedDate(orgStaff.getLastModifiedDate());
        resp.setLastModifiedBy(orgStaff.getLastModifiedBy());

        resp.setOrgId(orgStaff.getOrgId());
        resp.setUserId(orgStaff.getUserId());
        resp.setIdNo(orgStaff.getIdNo());
        resp.setJobNo(orgStaff.getJobNo());
        resp.setNickName(orgStaff.getNickName());
        resp.setRealName(orgStaff.getRealName());
        resp.setPhones(orgStaff.getPhones());
        resp.setTags(orgStaff.getTags());
        resp.setStatus(orgStaff.getStatus());

        return resp;
    }

    @Override
    public UniResp<OrgStaffGetResp> get(
            String userId,
            String orgId,
            String staffId
    ) {
        User user = userService.requireUser(userId);
        Org org = orgService.checkExists(userId, orgId);
        OrgStaff orgStaff = orgStaffService.checkExists(orgId, staffId);

        OrgStaffGetResp resp = toOrgStaffGetReq(orgStaff);


        UniResp<OrgStaffGetResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(resp);
        return uniResp;
    }

    @Override
    public UniResp<String> check(String userId, String orgId) {
        OrgStaff orgStaff = orgStaffRepo.findOne(
                Expressions.allOf(
                        QOrgStaff.orgStaff.userId.eq(userId),
                        QOrgStaff.orgStaff.orgId.eq(orgId),
                        QOrgStaff.orgStaff.deleted.ne(true)
                )
        );

        UniResp<String> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        if (orgStaff != null) {
            uniResp.setData(orgStaff.getId());
        }
        return uniResp;
    }

    @Override
    public UniResp<Void> update(
            String userId,
            String orgId,
            String staffId,
            OrgStaffUpdateReq orgStaffUpdateReq
    ) {
        return null;
    }

    @Override
    public UniResp<UniPage<OrgStaffGetResp>> list(
            int size,
            int page,
            List<String> sort,
            String userId,
            String orgId,
            String staffIds
    ) {
        User user = userService.requireUser(userId);
        Org org = orgService.checkExists(userId, orgId);

        Sort s = ParamUtils.toSort(sort);
        Pageable pageable = new PageRequest(page, size, s);

        Page<OrgStaff> domainPage = orgStaffRepo.findAll(Expressions.allOf(
                QOrgStaff.orgStaff.orgId.eq(orgId),

                staffIds != null && !staffIds.isEmpty()
                        ? QOrgStaff.orgStaff.id.in(staffIds)
                        : null,

                Expressions.anyOf(
                        QOrgStaff.orgStaff.deleted.isNull(),
                        QOrgStaff.orgStaff.deleted.eq(false)
                )
        ), pageable);

        Page<OrgStaffGetResp> respPage = domainPage.map(i -> toOrgStaffGetReq(i));

        UniPage<OrgStaffGetResp> respUniPage = uniPageConverter.convert(respPage);
        UniResp<UniPage<OrgStaffGetResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(respUniPage);
        return uniResp;
    }

    @Override
    public UniResp<UniPage<OrgStaffGetResp>> search(
            int size,
            int page,
            List<String> sort,
            String userId
    ) {

        Sort s = ParamUtils.toSort(sort);
        Pageable pageable = new PageRequest(page, size, s);

        Page<OrgStaff> orgStaffList =
                orgStaffRepo.findAll(
                        Expressions.allOf(
                                QOrgStaff.orgStaff.userId.eq(userId),
                                QOrgStaff.orgStaff.deleted.in(false)
                        ), pageable);

        Page<OrgStaffGetResp> respPage = orgStaffList.map(i -> toOrgStaffGetReq(i));

        UniPage<OrgStaffGetResp> respUniPage = uniPageConverter.convert(respPage);
        UniResp<UniPage<OrgStaffGetResp>> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(respUniPage);
        return uniResp;
    }
}
