package net.kingsilk.qh.agency.wap.api.common.dto;


import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by zcw on 3/13/17.
 * 通用的6个字段
 */
public abstract class Base {
    @ApiModelProperty(value = "属性列表")
    protected String id;

    /**
     * 创建时间
     */
    protected Date dateCreated;

    /**
     * 创建者的ID
     */
    protected String createdBy;

    /**
     * 最后修改日期
     */
    protected Date lastModifiedDate;

    /**
     * 最后更新者的ID
     */
    protected String lastModifiedBy;

    /**
     * 是否已经逻辑删除
     */
    protected boolean deleted;


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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }


}
