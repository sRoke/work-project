package net.kingsilk.qh.shop.core;

/**
 * 商品状态
 */
public enum ItemStatusEnum {
    EDITING("EDITING", "编辑中"),
    APPLYING("APPLYING", "申请上架中"),
    UNAPPROVED("UNAPPROVED", "审核未通过"),
    NORMAL("NORMAL", "正常"),
    OUT_OF_STOCK("OUT_OF_STOCK", "缺货"),
    SALE_OFF("SALE_OFF", "已下架");

    ItemStatusEnum(String code) {
        this(code, null);
    }

    ItemStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
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
