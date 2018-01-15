package net.kingsilk.qh.raffle.core;

public enum AwardTypeEnum {

    VIRTUAL("VIRTUAL", "虚拟"),
    MATERIAL("MATERIAL", "实物");

    AwardTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    AwardTypeEnum(String code) {
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

