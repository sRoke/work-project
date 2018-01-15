package net.kingsilk.qh.shop.core;

/**
 * 仓库操作类型
 */
public enum RepertoryOperateEnum {

    IN("IN", "入库"),
    OUT("OUT", "出库");

    RepertoryOperateEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    RepertoryOperateEnum(String code) {
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
