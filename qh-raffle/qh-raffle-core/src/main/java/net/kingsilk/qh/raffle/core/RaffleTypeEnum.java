package net.kingsilk.qh.raffle.core;

public enum RaffleTypeEnum {

    DIAL("DIAL","大转盘");

    RaffleTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    RaffleTypeEnum(String code) {
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
