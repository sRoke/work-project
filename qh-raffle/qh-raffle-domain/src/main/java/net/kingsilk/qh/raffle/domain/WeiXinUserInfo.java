package net.kingsilk.qh.raffle.domain;

import java.util.Date;
import java.util.List;

public class WeiXinUserInfo extends Base{

//    /** 业务id */
//    private String id;
//
//    /** 业务创建时间,框架提供，无法修改 */
//    private Date dateCreated;
//
//    /** 业务最后修改时间,框架提供，无法修改 */
//    private Date lastUpdated;

    /** 应用的APPID */
    private String appId;

    /** 用户的openid，仅微信公众号中有用 */
    private String openId;

    /** 用户的全局id，在微信服务中一个用户只有一个 */
    private String unionId;

    /** 用户昵称 */
    private String nickname;

    /** 用户昵称 */
    private String sex;

    /** 用户所在省 */
    private String province;

    /** 用户所在城市 */
    private String city;

    /** 用户的国籍 */
    private String country;

    /** 头像地址 */
    private String headimgurl;

    /** (请注意去除重复) */
    private List<String> privilege;

    // -------- 以下字段只能通过 用户管理/获取用户基本信息（UnionID机制）获取，也即，只能获取已关注的用户。

    /**
     * 是否已经订阅（关注）。
     */
    private boolean subscribe;

    /**
     * 订阅（关注）时间。
     */
    private Date subscribeTime;

    /**
     * 公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
     */
    private String remark;

    /**
     * 用户所在的分组ID（兼容旧的用户分组接口）
     */
    private Integer groupId;

    /**
     * 用户被打上的标签ID列表
     */
    private List<Integer> tagIdList;
}
