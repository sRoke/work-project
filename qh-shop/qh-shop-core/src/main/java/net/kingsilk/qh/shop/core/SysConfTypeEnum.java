package net.kingsilk.qh.shop.core;

/**
 * 系统配置表
 */
public enum SysConfTypeEnum {
    //进货、退货、卖出等

    shopPrice("shopPrice", "门店价格");

    SysConfTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    SysConfTypeEnum(String code) {
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