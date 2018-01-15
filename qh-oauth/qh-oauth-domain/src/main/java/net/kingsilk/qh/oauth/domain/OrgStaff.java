package net.kingsilk.qh.oauth.domain;

import net.kingsilk.qh.oauth.core.*;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.*;

/**
 * 组织下的员工表。
 *
 * 只管理是否是员工，不管理其员工在各个子系统中的权限。
 */
@Document
@CompoundIndexes({
        @CompoundIndex(unique = true, sparse = false, def = "{'orgId': 1, 'userId': 1}"),
//        @CompoundIndex(unique = true, sparse = true, def = "{'orgId': 1, 'idNo': 1}"),
//        @CompoundIndex(unique = true, sparse = true, def = "{'orgId': 1, 'jobNo': 1}"),
//        @CompoundIndex(unique = true, sparse = true, def = "{'orgId': 1, 'nickName': 1}"),
})
public class OrgStaff extends Base {

    /**
     * 所属组织
     */
    private String orgId;

    /**
     * 用户ID。
     */
    @Indexed
    private String userId;

    /**
     * 身份证号码。
     *
     * 同一个组织内唯一。
     */
//    @Indexed
    private String idNo;

    /**
     * 工号
     *
     * 同一个组织内唯一。
     */
    private String jobNo;

    /**
     * 花名
     *
     * 同一个组织内唯一。
     */
    private String nickName;

    /**
     * 真实姓名。
     */
    private String realName;

    /**
     * 联系电话
     */
    private List<String> phones;

    /**
     * 员工标签。
     */
    private Set<String> tags;

    /**
     * 员工状态
     */
    private OrgStaffStatusEnum status;

    // ------------------------ 自动生成的 getter、 setter

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public OrgStaffStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrgStaffStatusEnum status) {
        this.status = status;
    }
}
