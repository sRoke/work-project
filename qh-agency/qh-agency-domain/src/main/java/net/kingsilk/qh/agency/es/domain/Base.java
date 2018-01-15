package net.kingsilk.qh.agency.es.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Created by lit on 17/8/29.
 */
public class Base {
    @Id
    protected String id;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date)
    protected Date dateCreated;

    /**
     * 创建者的ID
     */
    protected String createdBy;

    /**
     * 最后修改日期
     */
    @Field(type = FieldType.Date)
    protected Date lastModifiedDate;

    /**
     * 最后更新者的ID
     */
    protected String lastModifiedBy;

    /**
     * 是否已经逻辑删除
     */
    protected boolean deleted;

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
