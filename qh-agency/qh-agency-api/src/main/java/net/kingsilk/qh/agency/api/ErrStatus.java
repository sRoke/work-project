package net.kingsilk.qh.agency.api;

/**
 *
 */
public interface ErrStatus {

    /**
     * 成功、没有异常。
     */
    int OK = 200;


    /**
     * 未知错误，未分类错误。
     */
    int UNKNOWN = 9999;

    /**
     * 未找到该渠道商员工
     */
    int USER_404_WITH_PARTNER_STAFF = 11001;

    /**
     * 未找到该渠道商
     */
    int PARTNER_404 = 11002;


    /**
     * 渠道商状态异常（禁用或过期）
     */
    int PARTNER_500 = 11003;

    /**
     * 渠道商申请中
     */
    int PARTNER_APPLYING = 11004;

    /**
     * 渠道商申请被拒绝
     */
    int PARTNER_REJECT = 11005;

    /**
     * 未找到该渠道商的openId
     */
    int PARTNER_401 = 11006;

}
