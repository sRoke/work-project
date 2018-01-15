package net.kingsilk.qh.oauth.domain;

import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * 公众号第三方平台。
 * <p>
 * 微信·开放平台 -> 管理中心 -> 公众号第三方平台 ： 列表中的单个 "公众号第三方平台" 信息。
 * <p>
 * https://open.weixin.qq.com
 * <p>
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1453779503&token=&lang=zh_CN
 */
@Document
@Deprecated
public class WxComApp {


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
    private boolean deleted;


    // ------------------------ 业务字段

    /**
     * APP ID
     */
    private String appId;

    /**
     * App Secret
     */
    private String appSecret;

    /**
     * 授权后代替公众号实现业务 - 公众号消息校验Token
     */
    private String msgVerifyToken;

    /**
     * 授权后代替公众号实现业务 - 公众号消息加解密Key
     */
    private String msgEncKey;


    // ------------------------ 自动生成的 getter、 setter

}
