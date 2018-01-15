package net.kingsilk.qh.agency.domain;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 演示用 Domain
 * <p>
 * 字段允许的类型：java基础数据类型以及其封装类型。
 *
 * @deprecated 仅仅示例用。
 */
@Document
@Deprecated
public class Xxx {


    // ------------------------ 共通字段
    /**
     * 额外的说明文档
     */

    @Id
    private String id;

    /**
     * 创建时间
     */
    @CreatedDate
    private Date dateCreated;

    /**
     * 创建者的ID
     */
    @CreatedBy
    private String createdBy;

    /**
     * 最后修改日期
     */
    @LastModifiedDate
    private Date lastModifiedDate;

    /**
     * 最后更新者的ID
     */
    @LastModifiedBy
    private String lastModifiedBy;

    /**
     * 是否已经逻辑删除
     */
    private boolean delete;


    // ------------------------ 业务字段


    /**
     * 名称
     */
    private String name;


    // ------------------------ 自动生成的 getter、 setter


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

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
