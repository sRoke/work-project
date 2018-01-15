package net.kingsilk.qh.agency.core;

public enum SkuStoreStatusEnum {
    STORE_SUPPLY("STORE_SUPPLY","货源充足"),
    STORE_SELLOUT("STORE_SELLOUT","已售罄"),
    STORE_LACK("STORE_LACK","紧缺的");

    SkuStoreStatusEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    SkuStoreStatusEnum(String code) {
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
