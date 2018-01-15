package net.kingsilk.qh.oauth.api.user.org.orgStaff;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.core.*;

import java.util.*;

/**
 *
 */
@ApiModel
public class OrgStaffUpdateReq {

    @ApiModelProperty("所属组织ID")
    private Optional<String> orgId = Optional.empty();

    @ApiModelProperty("要加为员工的用户ID")
    private Optional<String> userId = Optional.empty();

    @ApiModelProperty("身份证号码")
    private Optional<String> idNo = Optional.empty();

    @ApiModelProperty("工号")
    private Optional<String> jobNo = Optional.empty();

    @ApiModelProperty("花名")
    private Optional<String> nickName = Optional.empty();

    @ApiModelProperty("真实姓名")
    private Optional<String> realName = Optional.empty();

    @ApiModelProperty("联系电话")
    private Optional<List<String>> phones = Optional.empty();

    @ApiModelProperty("员工标签")
    private Optional<Set<String>> tags = Optional.empty();

    @ApiModelProperty("员工状态")
    private Optional<OrgStaffStatusEnum> status = Optional.empty();


    // ------------------------ 自动生成的 getter、 setter


    public Optional<String> getOrgId() {
        return orgId;
    }

    public void setOrgId(Optional<String> orgId) {
        this.orgId = orgId;
    }

    public Optional<String> getUserId() {
        return userId;
    }

    public void setUserId(Optional<String> userId) {
        this.userId = userId;
    }

    public Optional<String> getIdNo() {
        return idNo;
    }

    public void setIdNo(Optional<String> idNo) {
        this.idNo = idNo;
    }

    public Optional<String> getJobNo() {
        return jobNo;
    }

    public void setJobNo(Optional<String> jobNo) {
        this.jobNo = jobNo;
    }

    public Optional<String> getNickName() {
        return nickName;
    }

    public void setNickName(Optional<String> nickName) {
        this.nickName = nickName;
    }

    public Optional<String> getRealName() {
        return realName;
    }

    public void setRealName(Optional<String> realName) {
        this.realName = realName;
    }

    public Optional<List<String>> getPhones() {
        return phones;
    }

    public void setPhones(Optional<List<String>> phones) {
        this.phones = phones;
    }

    public Optional<Set<String>> getTags() {
        return tags;
    }

    public void setTags(Optional<Set<String>> tags) {
        this.tags = tags;
    }

    public Optional<OrgStaffStatusEnum> getStatus() {
        return status;
    }

    public void setStatus(Optional<OrgStaffStatusEnum> status) {
        this.status = status;
    }
}
