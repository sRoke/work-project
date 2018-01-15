package net.kingsilk.qh.oauth.core;

/**
 * 统一登记 Scope 类型。
 *
 */
public enum OAuthScopeEnum {

    SERVER("SERVER", "服务器端应用"),
    LOGIN("LOGIN", "登录");

    private final String code;
    private final String desp;

    OAuthScopeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    OAuthScopeEnum(String code) {
        this(code, null);
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }
}
