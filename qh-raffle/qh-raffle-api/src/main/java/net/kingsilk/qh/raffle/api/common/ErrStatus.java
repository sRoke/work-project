package net.kingsilk.qh.raffle.api.common;

/**
 *
 */
public interface ErrStatus {

    /**
     * 成功、没有异常。
     */
    int OK = 200;


    /**
     * 参数错误
     */
    int VARIABLEERROR = 10023;

    /**
     * 活动相关错误。
     */
    int ACTIVITYERROR = 10024;

    /**
     * 微信信息未获取到
     */
    int WXUSER_404 = 10025;


    /**
     * 授权微信公众号不符合
     */
    int WXAUTHERROE = 10027;

    /**
     * 未关注微信公众号
     */
    int FOLLOWWXMP = 10028;

    /**
     * 渠道商申请中
     */
    int PARTNER_APPLYING = 11004;

    /**
     * 渠道商申请被拒绝
     */
    int PARTNER_REJECT = 11005;

    /**
     * 查找不到数据
     */
    int FOUNDNULL = 11006;
}
