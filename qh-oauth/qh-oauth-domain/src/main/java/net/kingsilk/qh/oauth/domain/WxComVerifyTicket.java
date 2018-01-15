package net.kingsilk.qh.oauth.domain;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 演示用 Domain
 * <p>
 * 字段允许的类型：java基础数据类型以及其封装类型。
 *
 * @see org.springframework.security.oauth2.common.OAuth2AccessToken
 */
@Document
@Deprecated
public class WxComVerifyTicket {


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
    private Date createdDate;

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


    @Indexed()
    private String appId;

    private Date createtime;

    private String infoType;

    private String componentVerifyTicket;


    // ------------------------ 自动生成的 getter、 setter
}
