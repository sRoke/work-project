package net.kingsilk.qh.shop.core;

public enum ShopEnum {

    DIRECTSALES("DIRECTSALES", "直营"),

    COOPERATE("COOPERATE", "合作商"),

    OTHER("OTHER", "其他");

    ShopEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    ShopEnum(String code) {
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
