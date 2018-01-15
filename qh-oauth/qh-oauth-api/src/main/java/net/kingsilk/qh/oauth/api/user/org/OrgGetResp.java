package net.kingsilk.qh.oauth.api.user.org;

import io.swagger.annotations.*;
import net.kingsilk.qh.oauth.core.*;

import java.util.*;

/**
 *
 */
@ApiModel
public class OrgGetResp {

    // --------------------------------------- common fields

    @ApiModelProperty("组织ID")
    private String id;

    @ApiModelProperty("创建时间")
    private Date dateCreated;

    @ApiModelProperty("创建者的ID")
    private String createdBy;

    @ApiModelProperty("最后修改日期")
    private Date lastModifiedDate;

    @ApiModelProperty("最后更新者的ID")
    private String lastModifiedBy;

    // --------------------------------------- biz fields


    @ApiModelProperty("所属用户的用户ID")
    private String userId;

    @ApiModelProperty("组织名称")
    private String name;

    @ApiModelProperty("组织状态")
    private OrgStatusEnum status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrgStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrgStatusEnum status) {
        this.status = status;
    }
}
