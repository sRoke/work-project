package net.kingsilk.qh.shop.core;


/**
 * 优惠卷 是否可以叠加
 */
public enum ActivityStatusEnum {

    EDITING("EDITING","编辑中"),
    AUTO("AUTO","自动"),
    START("START","手动开始"),
    STOP("STOP","手动停止"),
    CLOSED("CLOSED","关闭");

    ActivityStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    ActivityStatusEnum(String code) {
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
