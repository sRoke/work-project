package net.kingsilk.qh.agency.core;

/**
 * Mq,Ajax,手机APP使用的API等返回消息的代码。
 */
public enum MsgCodeEnum {
    SUCCESS("SUCCESS", "成功"),
    ERROR("ERROR", "失败"),
    NOT_ORG("NOT_ORG", "用户支付密码未设置"),
    NOT_PAY_PASS("NOT_PAY_PASS", "用户支付密码未设置"),
    BALANCE("BALANCE", "用户余额不足"),
    BALANCE_SUCCESS("BALANCE_SUCCESS", "用户使用余额支付成功"),
    AMOUNT_ZERO("AMOUNT_ZERO", "订单价格为0"),
    NOT_LOGINED("NOT_LOGINED", "未登录"),
    NOT_WEIXIN("NOT_WEIXIN", "微信未绑定"),
    WEIXIN_BIND("WEIXIN_BIND", "微信已绑定"),
    UsernameNotFoundException("UsernameNotFoundException", "用户不存在"),
    BadCredentialsdException("BadCredentialsdException", "密码不正确"),
    UsernameExisted("UsernameExisted", "用户名已存在"),
    EmailExisted("EmailExisted", "电子邮箱已存在"),
    InvalidEmail("InvalidEmail", "电子邮箱不合法"),
    PhoneExisted("PhoneExisted", "手机号已存在"),
    PasswordDifference("PasswordDifference", "两次密码不一致"),
    VerifyCodeOutOfDate("VerifyCodeOutOfDate", "验证码已过期"),
    VerifyCodeActivated("VerifyCodeActivated", "验证码已激活");

    MsgCodeEnum(String code) {
        this(code, null);
    }

    MsgCodeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }

    public final String getDescription() {
        return desp;
    }

    private final String code;
    private final String desp;
}
