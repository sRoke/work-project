package net.kingsilk.qh.raffle.core;
/**
 * 活动入口。
 */
public enum RaffleEntranceEnum {

    TAG("TAG","吊牌"),
    WEB("WEB","商城"),
    OUT("OUT","外站");

    RaffleEntranceEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    RaffleEntranceEnum(String code) {
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