package net.kingsilk.qh.shop.core;

/**
 * 用户的登录方式
 */
public enum UserLoginTypeEnum {

    WEIXIN("WEIXIN","微信登录"),
    MOBILE("MOBILE","手机登录"),
    USERNAME_AND_PASSWORD("USERNAME_AND_PASSWORD","帐号密码登录"),
    WEIXIN_WITH_PUBLIC_ACCOUNT("WEIXIN_WITH_PUBLIC_ACCOUNT","微信带公众号登录");


    UserLoginTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    UserLoginTypeEnum(String code) {
        this(code, null);
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }

    private final String code;
    private final String desp;
}
