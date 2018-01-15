package net.kingsilk.qh.oauth.api;

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
     * 未找到绑定该微信公众号的用户
     */
    int USER_404_WITH_WX = 10001;

}
