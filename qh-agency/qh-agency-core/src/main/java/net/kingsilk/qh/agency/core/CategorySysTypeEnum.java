package net.kingsilk.qh.agency.core;

/**
 * 预定义的系统分类
 */
public enum CategorySysTypeEnum {
    QUILT("QUILT","普通商品"),
    RENT_QUILT("RENT_QUILT","酒店租赁"),
    SERVICE("SERVICE","服务商品"),
    STUDENT_RENT("STUDENT_RENT","学生租赁");

    CategorySysTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    CategorySysTypeEnum(String code) {
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
