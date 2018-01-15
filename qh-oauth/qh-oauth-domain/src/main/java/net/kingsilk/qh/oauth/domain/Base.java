package net.kingsilk.qh.oauth.domain;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

/**
 * 通用的6个字段
 */
@Document
public abstract class Base {

    @Id
    protected String id;

    /**
     * 创建时间
     */
    @CreatedDate
    protected Date dateCreated;

    /**
     * 创建者的ID
     */
    @CreatedBy
    protected String createdBy;

    /**
     * 最后修改日期
     */
    @LastModifiedDate
    protected Date lastModifiedDate;

    /**
     * 最后更新者的ID
     */
    @LastModifiedBy
    protected String lastModifiedBy;

    /**
     * 是否已经逻辑删除
     */
    protected boolean deleted;

    // --------------------------------------- equals && hashCode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Base base = (Base) o;
        return Objects.equals(id, base.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

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
