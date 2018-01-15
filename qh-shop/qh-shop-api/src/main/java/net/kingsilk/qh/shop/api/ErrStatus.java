package net.kingsilk.qh.shop.api;

/**
 *
 */
public interface ErrStatus {

    /**
     * 成功、没有异常。
     */
    int OK = 200;


    /**
     * 参数为空
     */
    int PARAMNUll = 10021;

    /**
     * null
     */
    int FINDNULL = 10026;


    /**
     * not only
     */
    int NOTONLY = 10027;


    /**
     * 不能删除
     */
    int DELERROR = 10028;

    /**
     * 没有权限
     */
    int NOAUTH = 10029;

    /**
     * 会员创建异常
     */
    int MEMBERERROR = 10030;

    /**
     * null
     */
    int HTTPERR = 10031;
    /**
     * 未知错误，未分类错误。
     */
    int UNKNOWN = 9999;

    int TIEMERROR = 10024;

    /**
     * 未找到该渠道商的openId
     */
    int PARTNER_401 = 11006;

    int UNLOGIN = 401;
    /**
     * 退款金额错误（退款额大于实际支付额）
     */
    int REFUNDMONEYEREEOR = 10031;

    /**
     * 退款金额错误（退款额大于实际支付额）
     */
    int SHOP_EXPIRE = 10032;

    /**
     * 找不到该会员
     */
    int NO_MEMBER = 10033;

    int ORDER_STATUS_ERROR = 10034;
}
