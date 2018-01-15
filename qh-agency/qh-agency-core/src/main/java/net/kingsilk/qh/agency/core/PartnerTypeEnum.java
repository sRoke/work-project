package net.kingsilk.qh.agency.core;

/**
 * 会员分类。
 */
public enum PartnerTypeEnum {

    BRAND_COM("BRAND_COM","品牌商"),
    GENERAL_AGENCY("GENERAL_AGENCY", "总代理"),
    REGIONAL_AGENCY("REGIONAL_AGENCY","代理"),
    LEAGUE("LEAGUE", "加盟商");

    PartnerTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    PartnerTypeEnum(String code) {
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
