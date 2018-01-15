package net.kingsilk.qh.raffle.core;

/**
 * 活动进行的状态
 */
public enum DrawTypeEnum {
    SINCE("SINCE", "自提"),
    ONLINE("ONLINE", "线上");

    DrawTypeEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    DrawTypeEnum(String code) {
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
