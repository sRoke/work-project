package net.kingsilk.qh.shop.core;

/**
 * 商品属性类型
 */
public enum ItemPropTypeEnum {
    LIST("LIST", "单选列表"),
    TEXT("TEXT", "自定义文本"),
    IMG("IMG", "图片"),
    MAP("MAP", "集合字段"),
    INT("INT", "数字类型");

    ItemPropTypeEnum(String code) {
        this(code, null);
    }

    ItemPropTypeEnum(String code, String desp) {
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
