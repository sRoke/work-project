package net.kingsilk.qh.agency.core;

/**
 * 分类的类别
 * <p>
 * 说明: "商品分类" 主要用作菜单管理
 */
public enum CategoryTypeEnum {

    PLATFORM_ITEM("PLATFORM_ITEM", "商品分类");

    CategoryTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    CategoryTypeEnum(String code) {
        this(code, null);
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
