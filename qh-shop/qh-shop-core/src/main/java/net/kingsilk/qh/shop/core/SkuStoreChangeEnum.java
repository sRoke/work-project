package net.kingsilk.qh.shop.core;

/**
 * Created by lit on 17/8/24.
 */
public enum SkuStoreChangeEnum {
    //进货、退货、卖出等

    PURCHASE("PURCHASE", "采购"),
    REFUND("REFUND", "退货"),
    SELL("SELL", "卖出");

    SkuStoreChangeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    SkuStoreChangeEnum(String code) {
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
