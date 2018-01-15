package net.kingsilk.qh.oauth.service;

import com.querydsl.core.types.dsl.*;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

@Service
public class OrgStaffService {


    @Autowired
    private OrgStaffRepo orgStaffRepo;

    public OrgStaff checkExists(String orgId, String staffId) {

        OrgStaff orgStaff = orgStaffRepo.findOne(Expressions.allOf(
                QOrgStaff.orgStaff.id.eq(staffId),
                QOrgStaff.orgStaff.orgId.eq(orgId),
                Expressions.anyOf(
                        QOrgStaff.orgStaff.deleted.isNull(),
                        QOrgStaff.orgStaff.deleted.eq(false)
                )
        ));
        Assert.isTrue(orgStaff != null, "员工不存在");
        return orgStaff;
    }

    /**
     * 检查指定的用户ID是否已经是员工。。
     *
     * @param orgId 组织ID
     * @param userId 用户ID。
     * @return 如果已经是员工，则返回该记录
     */
    public OrgStaff isStaff(
            String orgId,
            String userId
    ) {

        return orgStaffRepo.findOne(Expressions.allOf(
                QOrgStaff.orgStaff.userId.eq(userId),
                QOrgStaff.orgStaff.orgId.eq(orgId),
                Expressions.anyOf(
                        QOrgStaff.orgStaff.deleted.isNull(),
                        QOrgStaff.orgStaff.deleted.eq(false)
                )
        ));
    }

    /**
     * 检查指定的用户ID是否逻辑删除。
     *
     * @param orgId 组织ID
     * @param userId 用户ID。
     * @return 如果已经逻辑删除，则返回该记录
     */
    public OrgStaff isDeleted(
            String orgId,
            String userId
    ) {

        return orgStaffRepo.findOne(Expressions.allOf(
                QOrgStaff.orgStaff.userId.eq(userId),
                QOrgStaff.orgStaff.orgId.eq(orgId),
                QOrgStaff.orgStaff.deleted.eq(true)
        ));
    }


    /**
     * 检查身份证号是否已经被使用。
     *
     * @param orgId 组织ID
     * @param idNo 身份证号
     * @param excludeStaffId 不检查的员工的ID。可以为 null。
     * @return 已经使用该身份证号的员工。若未使用则返回 null。
     */
    public OrgStaff isIdNoInUse(
            String orgId,
            String idNo,
            String excludeStaffId
    ) {

        return orgStaffRepo.findOne(Expressions.allOf(

                !StringUtils.isEmpty(excludeStaffId)
                        ? QOrgStaff.orgStaff.id.ne(excludeStaffId)
                        : null,

                QOrgStaff.orgStaff.idNo.eq(idNo),
                QOrgStaff.orgStaff.orgId.eq(orgId)
        ));
    }


    /**
     * 检查工号是否已经被使用。
     *
     * @param orgId 组织ID
     * @param jobNo 工号
     * @param excludeStaffId 不检查的员工的ID。可以为 null。
     * @return 已经使用该工号的员工。若未使用则返回 null。
     */
    public OrgStaff isJobNoInUse(
            String orgId,
            String jobNo,
            String excludeStaffId
    ) {

        return orgStaffRepo.findOne(Expressions.allOf(

                !StringUtils.isEmpty(excludeStaffId)
                        ? QOrgStaff.orgStaff.id.ne(excludeStaffId)
                        : null,

                QOrgStaff.orgStaff.jobNo.eq(jobNo),

                QOrgStaff.orgStaff.orgId.eq(orgId)
        ));
    }


    /**
     * 检查花名是否已经被使用。
     *
     * @param orgId 组织ID
     * @param nickName 花名
     * @param excludeStaffId 不检查的员工的ID。可以为 null。
     * @return 已经使用该花名的员工。若未使用则返回 null。
     */
    public OrgStaff isNickNameInUse(
            String orgId,
            String nickName,
            String excludeStaffId
    ) {

        return orgStaffRepo.findOne(Expressions.allOf(

                !StringUtils.isEmpty(excludeStaffId)
                        ? QOrgStaff.orgStaff.id.ne(excludeStaffId)
                        : null,

                QOrgStaff.orgStaff.nickName.eq(nickName),

                QOrgStaff.orgStaff.orgId.eq(orgId)
        ));
    }

}
