package net.kingsilk.qh.shop.core;

/**
 * Created by lit on 17/12/4.
 */
public enum ShopStatusEnum {

    EXPIRED("EXPIRED", "店铺到期"),

    DISABLE("DISABLE", "禁用"),

    NORMAL("NORMAL", "正常");

    ShopStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    ShopStatusEnum(String code) {
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
