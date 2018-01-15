package net.kingsilk.qh.shop.core;

public enum GuideTypeEnum {

    ITEMPROP("ITEMPROP", "商品规格引导页"),

    ITEM_DETAIL("ITEM_DETAIL", "商品图文详情引导页");

    GuideTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    GuideTypeEnum(String code) {
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
