package net.kingsilk.qh.oauth.api.user.org.orgStaff;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.core.*;

import java.util.*;

/**
 *
 */
@ApiModel
public class OrgStaffAddReq {


    @ApiModelProperty("所属组织ID")
    private String orgId;

    @ApiModelProperty("要加为员工的用户ID")
    private String userId;

    @ApiModelProperty("身份证号码")
    private String idNo;

    @ApiModelProperty("工号")
    private String jobNo;

    @ApiModelProperty("花名")
    private String nickName;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("联系电话")
    private List<String> phones;

    @ApiModelProperty("员工标签")
    private Set<String> tags;

    @ApiModelProperty("员工状态")
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
