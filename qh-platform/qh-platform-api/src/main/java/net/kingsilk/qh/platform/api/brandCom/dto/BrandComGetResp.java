package net.kingsilk.qh.platform.api.brandCom.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.kingsilk.qh.platform.core.BrandComStatusEnum;

import java.util.Date;
import java.util.Set;

/**
 * 品牌商信息。
 */
@ApiModel
public class BrandComGetResp {

    // --------------------------------------- common fields

    @ApiModelProperty("品牌商ID")
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

    @ApiModelProperty("品牌商所属组织的组织ID")
    private String ownerOrgId;

    @ApiModelProperty("品牌商信息")
    private BrandComStatusEnum status;

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("有效期")
    private String date;

    @ApiModelProperty("应用")
    private Set<String> apps;

    @ApiModelProperty("logo地址")
    private String logUrl;

    @ApiModelProperty("主营")
    private Set<String> category;

    @ApiModelProperty("联系人")
    private String phone;

    // --------------------------------------- getter && setter

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

    public String getOwnerOrgId() {
        return ownerOrgId;
    }

    public void setOwnerOrgId(String ownerOrgId) {
        this.ownerOrgId = ownerOrgId;
    }

    public BrandComStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BrandComStatusEnum status) {
        this.status = status;
    }

    public Set<String> getApps() {
        return apps;
    }

    public void setApps(Set<String> apps) {
        this.apps = apps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public Set<String> getCategory() {
        return category;
    }

    public void setCategory(Set<String> category) {
        this.category = category;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
